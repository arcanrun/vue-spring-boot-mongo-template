<template>
  <h1 class="text-green-600 text-2xl">Home page</h1>
</template>

<script setup lang="ts">
import {useLoaderStore} from "@/stores/loader.ts";
import {onMounted} from "vue";

const loader = useLoaderStore()

onMounted(() => {
  loader.hideOverlayLoader();
})

</script>

