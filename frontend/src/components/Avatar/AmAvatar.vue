<template>
  <div>
    <img
      v-if="props.src"
      :src="props.src"
      alt="avatar"
      :class="imageClasses"
    />
    <div
      v-else
      :class="initialClasses"
    >
      {{ initials }}
    </div>
  </div>
</template>
<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import { computed } from 'vue'

const user = useUserStore()
const props = withDefaults(defineProps<{ src?: string, size?: string }>(), {})
const initials = computed(() => {
  if(user.state.sureName) {
    return `${user.state.name?.substring(0, 1)}${user.state.sureName?.substring(0, 1)}`
  }

  return `${user.state.name?.substring(0, 1)}`
})
const initialClasses = computed(() => {
  const classes = [
    'flex',
    'items-center',
    'justify-center',
    'overflow-hidden',
    'rounded-full',
    'bg-blue-500',
    'text-white'
  ]

  if (props.size) {
    if (props.size === '3xl') {
      return [...classes, 'w-96', 'h-96', 'text-9xl']
    }

    if (props.size === 'xxl') {
      return [...classes, 'w-72', 'h-72', 'text-9xl']
    }

    if (props.size === 'lg') {
      return [...classes, 'w-24', 'h-24', 'text-lg']
    }

    if (props.size === 'md') {
      return [...classes, 'w-20', 'h-20', 'text-md']
    }

    if (props.size === 'sm') {
      return [...classes, 'w-16', 'h-16', 'text-sm']
    }
  }

  return [...classes, 'w-56', 'h-56', 'text-8xl']
})
const imageClasses = computed(() => {
  const classes = [
    'rounded-full',
    'object-cover',
    'object-center',
    'ring',
    'ring-white'

  ]

  if (props.size) {
    if (props.size === '3xl') {
      return [...classes, 'w-96', 'h-96']
    }

    if (props.size === 'xxl') {
      return [...classes, 'w-72', 'h-72']
    }

    if (props.size === 'lg') {
      return [...classes, 'w-24', 'h-24']
    }

    if (props.size === 'md') {
      return [...classes, 'w-20', 'h-20']
    }

    if (props.size === 'sm') {
      return [...classes, 'w-16', 'h-16']
    }
  }
  return [...classes, 'w-56', 'h-56']
})
</script>
<script lang="ts">
export default {
  name: 'AmAvatar'
}
</script>
