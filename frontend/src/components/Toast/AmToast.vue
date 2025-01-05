<template>
  <div :class="classes">
    <div class="flex flex-row justify-between justify-items-center content-around p-4">
      <div>
        <svg class="w-6 h-6 mr-2 mt-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor"
             viewBox="0 0 20 20">
          <path
            d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
        </svg>
      </div>

      <div>
        <div class="text-xl font-bold"> {{ title }}</div>
        <div>
          {{ props.toast?.message }}
        </div>
      </div>

      <div class="ms-auto">
        <button
          @click="() => toast.remove(props.toast.id)"
          type="button"
          class="inline-flex flex-shrink-0 justify-center items-center size-5 rounded-lg text-white hover:text-white opacity-50 hover:opacity-100 focus:outline-none focus:opacity-100 hover:bg-gray-50/30 p-4"
        >
          <span class="sr-only">Close</span>
          <svg
            class="flex-shrink-0 size-5"
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M18 6 6 18"/>
            <path d="m6 6 12 12"/>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { IToast, ToastType, useToastStore } from '@/stores/toast'
import { computed } from 'vue'

const toast = useToastStore()
const props = defineProps<{ toast: IToast }>()

const classes = computed(() => {
  const classes = ['text-md', 'rounded-md', 'shadow-xl', 'my-2']

  switch (props.toast.type) {
    case ToastType.SUCCESS:
      return [...classes, 'bg-green-500', 'text-white']
    case ToastType.INFO:
      return [...classes, 'bg-blue-600', 'text-white']
    case ToastType.WARNING:
      return [...classes, 'bg-orange-400', 'text-white']
    case ToastType.ERROR:
      return [...classes, 'bg-red-500', 'text-white']
    default:
      return [...classes, 'bg-slate-100', 'text-black']
  }
})

const title = computed(() => {
  switch (props.toast.type) {
    case ToastType.SUCCESS:
      return 'Success!'
    case ToastType.INFO:
      return 'Please, note'
    case ToastType.WARNING:
      return 'Warning!'
    case ToastType.ERROR:
      return 'Something went wrong'
    default:
      return 'Please, note'
  }
})
</script>
<script lang="ts">
export default {
  name: 'AmToast'
}
</script>
