<template>
  <div v-if="isVisible()" class="flex flex-row items-center justify-between
                                 h-24 px-4 fixed left-0 top-0 w-full bg-white
                                 z-[39]">
    <div class="flex">
      <span @click="showDrawer"
            class="cursor-pointer hover:opacity-20 transition-all duration-5 ease-in-out visible xl:invisible">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd" clip-rule="evenodd"
              d="M2 9.25C2 8.91848 2.1317 8.60054 2.36612 8.36612C2.60054 8.1317 2.91848 8 3.25 8H16.135C16.4665 8 16.7845 8.1317 17.0189 8.36612C17.2533 8.60054 17.385 8.91848 17.385 9.25C17.385 9.58152 17.2533 9.89946 17.0189 10.1339C16.7845 10.3683 16.4665 10.5 16.135 10.5H3.25C2.91848 10.5 2.60054 10.3683 2.36612 10.1339C2.1317 9.89946 2 9.58152 2 9.25ZM2 4.25C2 3.91848 2.1317 3.60054 2.36612 3.36612C2.60054 3.1317 2.91848 3 3.25 3H20.75C21.0815 3 21.3995 3.1317 21.6339 3.36612C21.8683 3.60054 22 3.91848 22 4.25C22 4.58152 21.8683 4.89946 21.6339 5.13388C21.3995 5.3683 21.0815 5.5 20.75 5.5H3.25C2.91848 5.5 2.60054 5.3683 2.36612 5.13388C2.1317 4.89946 2 4.58152 2 4.25ZM2 14.25C2 13.9185 2.1317 13.6005 2.36612 13.3661C2.60054 13.1317 2.91848 13 3.25 13H20.75C21.0815 13 21.3995 13.1317 21.6339 13.3661C21.8683 13.6005 22 13.9185 22 14.25C22 14.5815 21.8683 14.8995 21.6339 15.1339C21.3995 15.3683 21.0815 15.5 20.75 15.5H3.25C2.91848 15.5 2.60054 15.3683 2.36612 15.1339C2.1317 14.8995 2 14.5815 2 14.25ZM2 19.25C2 18.9185 2.1317 18.6005 2.36612 18.3661C2.60054 18.1317 2.91848 18 3.25 18H16.135C16.4665 18 16.7845 18.1317 17.0189 18.3661C17.2533 18.6005 17.385 18.9185 17.385 19.25C17.385 19.5815 17.2533 19.8995 17.0189 20.1339C16.7845 20.3683 16.4665 20.5 16.135 20.5H3.25C2.91848 20.5 2.60054 20.3683 2.36612 20.1339C2.1317 19.8995 2 19.5815 2 19.25Z"
              fill="#1E293B"/>
      </svg>
      </span>
    </div>
    <slot></slot>
    <div >
      <RouterLink to="/" class="flex justify-center items-center">
        <div class="uppercase font-bold pr-2">mongo notes</div>
        <img src="@/assets/logo.svg" alt="logo" class="h-8">
      </RouterLink>
    </div>
  </div>
</template>
<script setup lang="ts">
import { useLayoutStore } from '@/stores/layout'
import { useUserStore } from '@/stores/user'

const layout = useLayoutStore()
const user = useUserStore()
const showDrawer = () => {
  layout.drawer = true
}
const isVisible = () => {
  return user.state.isAuthenticated
}
</script>
<script lang="ts">
export default {
  name: 'AmHeader'
}
</script>
