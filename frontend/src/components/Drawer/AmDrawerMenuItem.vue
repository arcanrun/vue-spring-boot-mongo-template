<template>
  <RouterLink :to="props.to" v-slot="{ href, navigate, isExactActive }">
    <a
      v-bind="$attrs"
      :href="href"
      @click="navigate"
    >
      <div @click="() => layout.drawer = false"
           :class="isExactActive ? classesActive : classesDefault">

        <div class="ms-6">
          <slot name="icon"></slot>
        </div>

        <span class="ms-8">
          <slot name="name"></slot>
        </span>

      </div>
    </a>
  </RouterLink>
</template>
<script setup lang="ts">
import { useLayoutStore } from '@/stores/layout'
import { computed } from 'vue'

const props = defineProps<{ to: string }>()
const layout = useLayoutStore()

const classesActive = computed(() => {
  return [
    'flex',
    'items-center',
    'p-4',
    'text-blue-600',
    'transition-all',
    'bg-blue-100',
    'hover:bg-blue-200',
    'hover:text-blue-800'
  ]
})
const classesDefault = computed(() => {
  return [
    'flex',
    'items-center',
    'p-4',
    'text-gray-900',
    'transition-all',
    'hover:bg-blue-200',
    'hover:text-blue-800'
  ]
})
</script>
<script lang="ts">
export default {
  name: 'AmDrawerMenuItem'
}
</script>
