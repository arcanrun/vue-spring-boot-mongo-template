<template>
  <AmOverlayLoader v-if="loader.isOverlayLoader"/>
  <div v-else class="flex">
    <AmDrawer/>
    <AmHeader/>
    <AmToastContainer/>
    <div :class="classes">
      <router-view v-slot="{ Component, route }">
        <transition name="fade" mode="out-in">
          <component :is="Component" :key="route.name"/>
        </transition>
      </router-view>
    </div>
  </div>
</template>
<script setup lang="ts">
import {RouterView} from 'vue-router'
import {AmToastContainer} from "@/components/Toast";
import {useLoaderStore} from "@/stores/loader.ts";
import AmOverlayLoader from "@/components/Loader/AmOverlayLoader.vue";
import AmHeader from "@/components/Header/AmHeader.vue";
import AmDrawer from "@/components/Drawer/AmDrawer.vue";
import {computed} from "vue";
import {useUserStore} from "@/stores/user.ts";

const user = useUserStore();
const loader = useLoaderStore();

const classes = computed(() => {
    if (user.state.isAuthenticated) {
      return 'flex-1 mt-24'
    }
    return 'flex-1'
  }
)

</script>
