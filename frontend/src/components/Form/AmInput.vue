<template>
  <!--  https://flowbite.com/docs/components/forms/-->
  <div class="w-full">
    <label
      v-if="props.label"
      class="block mb-2 text-sm font-medium text-blue-500"
    >
      {{ label }}
    </label>
    <input
      autocomplete="on"
      :name="props.label"
      :type="props.type"
      :placeholder="props.placeholder"
      :value="props.value"
      @input="e => emit('input', e?.target)"
      required
      class="border-solid border border-grey-300 bg-gray-50 outline-none text-gray-900 text-sm
             rounded-md block w-full p-2.5 focus:border-blue-500"
    />
  </div>
</template>
<script setup lang="ts">
import {computed} from 'vue'

const emit = defineEmits(['input'])
const props = withDefaults(
  defineProps<{
    type: string;
    label?: string | undefined;
    placeholder?: string | undefined;
    value?: string | null;
  }>(),
  {
    label: undefined,
    placeholder: undefined
  }
)

const label = computed(() => {
  if (props.label) {
    return props.label.charAt(0).toUpperCase() + props.label.slice(1)
  }
  return null
})
</script>

<script lang="ts">
export default {
  name: 'AmInput'
}
</script>
