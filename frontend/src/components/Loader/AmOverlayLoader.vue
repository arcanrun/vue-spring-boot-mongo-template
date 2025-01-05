<template>
  <transition
    name="overlay-fade-transition"
    enter-active-class="transition ease-in-out duration-50 transform"
    enter-class="opacity-0"
    enter-to-class="opacity-0"
    leave-active-class="transition ease-in-out duration-50"
    leave-class="opacity-100"
    leave-to-class="opacity-0"
  >
    <!--    TODO: the image for the background is too heavy. It must be replicated via CSS !!! -->
    <div
      class="w-full h-full bg-blue-300 top-0 right-0 fixed z-50 bg-[url('@/assets/signup-bg.svg')] bg-cover">
      <div class="text-center h-full flex flex-col align-items-center justify-center items-center">
        <AmLoader/>
        <div class="my-5 text-white uppercase font-bold">Loading...</div>
      </div>
    </div>
  </transition>
</template>
<script setup lang="ts">
import AmLoader from '@/components/Loader/AmLoader.vue'

</script>
<script lang="ts">
export default {
  name: 'AmOverlayLoader'
}
</script>
