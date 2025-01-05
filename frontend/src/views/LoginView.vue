<template>
  <div class="flex flex-col items-center justify-center min-h-screen w-full">
    <AmCard class="w-[25%]">
      <AmForm @submit="onSubmit" :submit-btn-text="'login'">
        <AmInput label="login" type="text" placeholder="Login"/>
        <AmInput label="password" type="password" placeholder="Password"/>
      </AmForm>
    </AmCard>
    <div>
      <span class="hover:underline hover:text-blue-400 cursor-pointer text-gray-400">
        <RouterLink to="/signup">Go to the sign up page</RouterLink>
      </span>
    </div>
  </div>
</template>

<script setup lang="ts">
import {AmForm, AmInput} from "@/components/Form";
import AmCard from "@/components/Card/AmCard.vue";
import {useUserStore} from "@/stores/user.ts";
import {useRouter} from "vue-router";
import {onMounted} from "vue";

const user = useUserStore()
const router = useRouter();

const onSubmit = async (data: any) => {
  await user.login({username: data.login, password: data.password})
}

onMounted(() => {
  if (user.state.isAuthenticated) {
    router.push("/");
  }
})
</script>
