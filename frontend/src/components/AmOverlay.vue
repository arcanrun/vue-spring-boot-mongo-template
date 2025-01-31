<template>
  <transition
    name="overlay-fade-transition"
    enter-active-class="transition ease-in-out duration-50 transform"
    enter-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="transition ease-in-out duration-50"
    leave-class="opacity-100"
    leave-to-class="opacity-0"
  >
    <div v-if="props.value" :class="classes">
      <slot></slot>
      <div
        v-if="props.closeOverlayOnClick"
        @click="emit('click')"
        class="bg-gray-950/60 fixed inset-0 z-30 flex items-center justify-center p-4 sm:p-0 overflow-hidden"
      ></div>
    </div>
  </transition>
</template>
<script setup lang="ts">
import { computed } from 'vue'

const emit = defineEmits(['click'])
const props = withDefaults(
  defineProps<{
    value?: boolean
    closeOverlayOnClick?: boolean
  }>(),
  {
    value: false,
    closeOverlayOnClick: false,
  },
)

const classes = computed(() => {
  const classes = [
    'fixed',
    'inset-0',
    'z-50',
    'flex',
    'items-center',
    'justify-center',
    'p-4',
    'sm:p-0',
    'overflow-hidden',
    'backdrop-blur-sm',
  ]

  if (!props.closeOverlayOnClick) {
    return [...classes, 'bg-gray-950/60']
  }

  return classes
})
</script>
<script lang="ts">
export default {
  name: 'AmOverlay',
}
</script>
