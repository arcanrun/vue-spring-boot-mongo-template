<template>
  <AmAvatar :src="avatarSrc" :size="props.size"/>
</template>
<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import { onMounted, onUnmounted, ref } from 'vue'
import { API } from '@/service'
import AmAvatar from '@/components/Avatar/AmAvatar.vue'

const user = useUserStore()
const avatarSrc = ref()
const props = withDefaults(defineProps<{ size?: string }>(), {})

onMounted(() => {
  user.getCurrentUser()
})
onUnmounted(() => {
  URL.revokeObjectURL(avatarSrc.value) // free memory
  avatarSrc.value = null
})
</script>
<script lang="ts">
export default {
  name: 'AmProfileAvatar'
}
</script>
