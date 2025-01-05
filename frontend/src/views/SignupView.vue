<template>
  <div class="flex flex-col items-center justify-center min-h-screen w-full">
    <AmCard class="w-[25%]">
      <AmForm
        @submit="onSubmit"
        :submit-btn-text="'register'"
        :validation="validateField"
        :validationForLabels="validationFields"
        :validationMessage="'Error: password mismatch'">
        <AmInput label="login" type="text" placeholder="Login"/>
        <AmInput label="password" type="password" placeholder="Password"/>
        <AmInput label="retry password" type="password" placeholder="Password"/>
      </AmForm>
    </AmCard>
    <div>
      <span class="hover:underline hover:text-blue-400 cursor-pointer text-gray-400">
        <RouterLink to="/login">Back to the Login page</RouterLink>
      </span>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted} from "vue";
import {useUserStore} from "@/stores/user.ts";
import {useRouter} from "vue-router";
import {useLoaderStore} from "@/stores/loader.ts";
import AmCard from "@/components/Card/AmCard.vue";
import {AmForm, AmInput} from "@/components/Form";
import {API} from "@/service";

const user = useUserStore()
const router = useRouter();
const loader = useLoaderStore()
const validationFields = ["password", "retry password"];

const onSubmit = async (data: any) => {
  if (data['hasErrors'] === 'yes') {
    return;
  }

  loader.showOverlayLoader()
  await API.authController.signup({username: data.login, password: data.password})
  await router.push("/login");
  loader.hideOverlayLoader()
}

const validateField = (data: Record<string, string>) => {
  return data[validationFields[0]] === data[validationFields[1]]
};

onMounted(() => {
  if (user.state.isAuthenticated) {
    router.push("/");
  }
})
</script>
