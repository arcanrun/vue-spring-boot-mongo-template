<template>
  <!--  https://flowbite.com/docs/components/forms/-->
  <form
    @submit.prevent="onSubmit"
    class="flex flex-col w-full space-y-4">
    <slot></slot>
    <span class="text-sm text-red-500">{{ validationErrorMessage }}</span>
    <div class="flex items-end justify-end mt-4">
      <AmBtn :level="BtnLevel.INFO" :type="'submit'">{{ props.submitBtnText }}</AmBtn>
    </div>
  </form>
</template>
<script setup lang="ts">
import {AmBtn, BtnLevel} from '@/components/Button'
import {computed, ref} from "vue";

const props = withDefaults(defineProps<{
    submitBtnText?: string,
    validation?: Function | null,
    validationForLabels?: string[] | null,
  }>(),
  {
    submitBtnText: 'submit',
    validation: null,
    validationForLabels: null,
    validationMessage: null,
  })
const emit = defineEmits(['submit'])
const isValidationError = ref<boolean>(false)

const onSubmit = () => {
  isValidationError.value = false
  const formElement = document.querySelector('form')

  if (!formElement) {
    return
  }

  const formData = new FormData(formElement)
  const data: Record<string, string> = {}


  formData.forEach((value, key) => {
    data[key] = value as string
  })

  if (props.validationForLabels && props.validation) {
    const isValid = props.validation(data);
    isValidationError.value = !isValid;
    data['hasErrors'] = !isValid ? 'yes' : 'no'
  }

  emit('submit', data)
}

const validationErrorMessage = computed(() => {
  if (props.validationForLabels && isValidationError.value) {
    return `Error validation in: ${props.validationForLabels.join(', ')}`
  }

})
</script>
<script lang="ts">
export default {
  name: 'AmForm'
}
</script>
