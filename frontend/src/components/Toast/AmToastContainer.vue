<template>
  <div class="p-4 fixed z-50 top-0 right-0 min-w-full lg:min-w-96">
    <transition-group
      name="toast-slide-transition"
      enter-active-class="transition ease-in-out duration-500 transform"
      enter-from-class="translate-x-full opacity-0"
      enter-to-class="translate-x-0 opacity-100"
      leave-active-class="transition ease-in-out duration-500 transform"
      leave-from-class="translate-x-0 opacity-100"
      leave-to-class="translate-x-full opacity-0"
    >
      <AmToast v-for="toastItem in toasts.list" :key="toastItem.id"
               :toast="toastItem"/>
    </transition-group>
  </div>
</template>

<script setup lang="ts">
import {useToastStore} from '@/stores/toast'
import AmToast from '@/components/Toast/AmToast.vue'

const toasts = useToastStore()
</script>
<script lang="ts">
export default {
  name: 'AmToastContainer'
}
</script>
