import {defineStore} from 'pinia'
import {API} from '@/service'
import {computed, reactive} from 'vue'
import {useLoaderStore} from '@/stores/loader'
import {useToastStore} from '@/stores/toast'
import {useRouter} from "vue-router";
import type {ILoginRequestDto} from "@/service/dto/auth.ts";

export const useUserStore = defineStore('users', () => {
  const router = useRouter()
  const toast = useToastStore()
  const loader = useLoaderStore()
  const state = reactive({
    isAuthenticated: !!localStorage.getItem('token'),
    token: localStorage.getItem('token'),
    tokenExpiry: localStorage.getItem('tokenExpiry'),
    id: null as string | null,
    avatarSrc: null as string | null,
    name: null as string | null,
    sureName: null as string | null,
    email: null as string | null,
    phoneNumber: null as string | null,
    address: null as string | null,
    roles: null as string[] | null | undefined,
    authorities: null as string[] | null | undefined,
  })

  const fullName = computed(() => {
    if (state.sureName) {
      return `${state.name} ${state.sureName}`
    }

    return `${state.name}`
  })

  async function login(loginDto: ILoginRequestDto) {
    loader.showOverlayLoader()
    API.authController.MANUAL_MODE.login(loginDto)
      .then((res) => {
        updateToken(res.data)
        API.userController.fetchCurrentUser()
          .then(res => {
            state.id = res.id
            state.name = res.name
            state.authorities = res.authorities
            state.isAuthenticated = !!localStorage.getItem('token')

            router.push('/').then((res) => {
              loader.hideOverlayLoader()
            })
          })


      }).catch(err => {
      loader.hideOverlayLoader()
      showToastError(err)
    })
  }

  async function getCurrentUser() {
    if (state.isAuthenticated
      && state.name
      && state.id) {

      return;
    }


    const user = await API.userController.fetchCurrentUser()

    state.id = user.id
    state.name = user.name
  }

  async function logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('tokenExpiry')
    state.id = null
    state.name = null
    state.authorities = null
    state.token = null
    state.isAuthenticated = false
    await router.push('/login')
  }

  async function refreshToken() {
    if (state.tokenExpiry &&
      (Number.parseInt(state.tokenExpiry) - Math.floor(Date.now() / 1000)) <= 10) {
      API.authController.MANUAL_MODE.refreshToken()
        .then((res) => {
          updateToken(res.data);
        })
        .catch((err) => {
          showToastError(err);
        })
    }
  }

  function updateToken(token: string) {
    const jsonToken = tokenToJson(token);

    localStorage.removeItem('token')
    localStorage.removeItem('tokenExpiry')
    localStorage.setItem('token', token)
    localStorage.setItem('tokenExpiry', jsonToken.exp)
    state.token = localStorage.getItem('token')
    state.tokenExpiry = jsonToken.exp
  }

  function tokenToJson(token: string) {
    return JSON
      .parse(
        atob(token
          .replace("Bearer ", "")
          .split(".")[1]));
  }

  function showToastError(err: any) {
    let message = err.resposne
      ? err.response.data.message
      : err.message

    if (err.response?.data?.detail) {
      message = err.response.data.detail
    }

    toast.showError(message)
  }

  return {state, login, fullName, getCurrentUser, logout, refreshToken}
})
