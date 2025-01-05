import axios from 'axios'
import {useToastStore} from '@/stores/toast'
import {useLoaderStore} from '@/stores/loader'
import {useUserStore} from "@/stores/user.ts";
import type {ApiError} from "@/service/dto/apiError.ts";

const axiosAuto = axios.create({
  baseURL: import.meta.env.VITE_BACK_END_URL,
  timeout: import.meta.env.VITE_AXIOS_TIME_OUT
})

const axiosManual = axios.create({
  baseURL: import.meta.env.VITE_BACK_END_URL,
  timeout: import.meta.env.VITE_AXIOS_TIME_OUT
})

axiosAuto.interceptors.request.use(
  async (config) => {
    const loader = useLoaderStore()
    const user = useUserStore();

    loader.showLoader()

    if (user.state.token) {
      await user.refreshToken()
      // eslint-disable-next-line
      config.headers['Authorization'] = `Bearer ${user.state.token}`
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

axiosAuto.interceptors.response.use(
  (response) => {
    const loader = useLoaderStore()

    loader.hideLoader()

    return response
  },
  (error) => {
    return handleError(error)
  }
)

async function handleError(error: unknown): Promise<ApiError> {
  const loader = useLoaderStore()
  const toast = useToastStore()
  const user = useUserStore();

  loader.hideLoader()

  if (axios.isAxiosError(error)) {
    const message = `${error.message}. ${error.response?.data.message ? error.response?.data.message : ''}`
    const status = error.status

    if (status === 401) {
      await user.logout()
    }

    toast.showError(message)

    return {
      status: status,
      message: message,
      data: null
    }
  }

  const message = 'Something went wrong'

  toast.showError(message)

  return {
    status: 500,
    message: message,
    data: null
  }
}

export {axiosAuto, axiosManual}
