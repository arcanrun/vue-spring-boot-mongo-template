<template>
  <div v-if="isVisible" :class="classes">
    <div @click="toggleSearchBar" class="flex p-4 hover:opacity-50 cursor-pointer">
      <div>
        <svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
        </svg>
      </div>
    </div>
    <div class="flex flex-col p-4 mt-10">
         <span class="text-md text-white font-bold">
            Title
          </span>
      <AmInput type="text"/>
      <span class="text-md text-white font-bold">
            Content
          </span>
      <AmInput type="text"/>
      <div class="mb-2">
          <span class="text-md text-white font-bold">
            Category
          </span>
        <AmSelect label="category"/>
      </div>
      <span class="text-md text-white font-bold">
            Tags
          </span>
      <AmInput type="text"/>
      <div class="flex w-full justify-end mt-4">
        <AmBtn :level="BtnLevel.ERROR">clear</AmBtn>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

import {AmBtn, BtnLevel} from "@/components/Button";
import {AmInput} from "@/components/Form";
import AmSelect from "@/components/Select/AmSelect.vue";
import {useUserStore} from "@/stores/user.ts";
import {computed} from "vue";
import {useLayoutStore} from "@/stores/layout.ts";

const user = useUserStore()
const layout = useLayoutStore()

const isVisible = computed(() => {
  return user.state.isAuthenticated
})
const classes = computed(() => {
  const classesCommon = [
    'overflow-hidden',
    'z-40',
    'h-screen',
    'overflow-y-auto',
    'transition-transform',
    'bg-gray-500/60',
    'backdrop-blur-sm',
    'fixed',
    'top-0',
    'right-0',
    'w-80',
    'z-1'
  ]

  if (layout.searchBar) {
    return [...classesCommon, 'translate-x-[1%]']
  }

  return [...classesCommon, 'translate-x-[100%]']
})
const toggleSearchBar = () => {
  layout.toggleSearchBar()
}

</script>
<script lang="ts">
export default {
  name: 'AmSearchBar'
}
</script>
