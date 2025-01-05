<template>
  <transition name="fade">
    <div class='flex space-x-2 justify-center items-center'>
      <span class='sr-only'>Loading...</span>
      <div
        class='p-8 m-8 h-8 w-8 bg-blue-600 animate-ping rounded-full [animation-delay:-0.3s]'></div>
      <div
        class='p-8 m-8 h-8 w-8 bg-blue-600 animate-ping rounded-full [animation-delay:-0.15s]'></div>
      <div class='p-8 m-8 h-8 w-8 bg-blue-600 animate-ping rounded-full'></div>
    </div>
  </transition>
</template>
<script setup lang="ts">
</script>
<script lang="ts">
export default {
  name: 'AmLoader2'
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
