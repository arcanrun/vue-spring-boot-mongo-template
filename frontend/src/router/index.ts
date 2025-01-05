import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from "@/stores/user.ts";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/signup',
      name: 'signup',
      component: () => import('../views/SignupView.vue'),
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue'),
    },
  ]
})

router.beforeEach((to, from, next) => {
  const user = useUserStore();

  if (to.name !== 'signup' && to.name !== 'login' && !user.state.isAuthenticated) {
    next({name: 'login'})

    return
  }

  next()
})

export default router
