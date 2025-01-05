<template>
  <div class="mt-24">
    <h1 class="text-green-600 text-2xl">{{ user.state.id }}</h1>
    <h1 class="text-green-600 text-2xl">{{ user.state.name }}</h1>
  </div>
</template>

<script setup lang="ts">
import {onMounted} from "vue";
import {useUserStore} from "@/stores/user.ts";

const user = useUserStore()

onMounted(() => {
  user.getCurrentUser();
})

</script>

