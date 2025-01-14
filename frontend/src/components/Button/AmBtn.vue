<template>
  <button :class="classes" :type="props.type">
    <slot></slot>
  </button>
</template>
<script setup lang="ts">
import { computed } from 'vue'
import { BtnLevel } from '@/components/Button/enums/level'

const props = withDefaults(
  defineProps<{
    level?: BtnLevel | null
    type?: 'button' | 'submit' | 'reset' | undefined
  }>(),
  {
    level: null,
    type: 'button',
  },
)

const classes = computed(() => {
  const classes = [
    'px-5',
    'py-2',
    'text-center',
    'text-sm',
    'inline-block',
    'cursor-pointer',
    'transition',
    'duration-5',
    'ease-in-out',
    'rounded',
    'focus-visible:outline-none',
    'focus-visible:ring-2',
    'focus-visible:ring-offset-2',
    'active:scale-95',
    'hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)]',
    'active:shadow-[0_0px_0px_-4px_rgba(59,113,202,0.3),0_0px_0px_0_rgba(59,113,202,0.2)]',
    'focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)]',
    'active:bg-primary-700',
    'focus:bg-primary-600',
    'focus:outline-none',
    'focus:ring-0',
  ]

  switch (props.level) {
    case BtnLevel.SUCCESS:
      return [
        ...classes,
        'text-white',
        'font-semibold',
        'uppercase',
        'bg-green-500',
        'hover:bg-green-400',
        'focus-visible:bg-green-400',
      ]
    case BtnLevel.INFO:
      return [
        ...classes,
        'text-white',
        'font-semibold',
        'uppercase',
        'bg-blue-600',
        'hover:bg-blue-500',
        'focus-visible:bg-blue-500',
      ]
    case BtnLevel.WARNING:
      return [
        ...classes,
        'text-white',
        'font-semibold',
        'uppercase',
        'bg-orange-400',
        'hover:bg-orange-300',
        'focus-visible:bg-orange-300',
      ]
    case BtnLevel.ERROR:
      return [
        ...classes,
        'text-white',
        'font-semibold',
        'uppercase',
        'bg-red-500',
        'hover:bg-red-400',
        'focus-visible:bg-red-400',
      ]
    default:
      return [
        ...classes,
        'text-black',
        // 'bg-slate-100',
        // 'hover:bg-slate-50',
        'focus-visible:bg-slate-50',
      ]
  }
})
</script>
<script lang="ts">
export default {
  name: 'AmBtn',
}
</script>
