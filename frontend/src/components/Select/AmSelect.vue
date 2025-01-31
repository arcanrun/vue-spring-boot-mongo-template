<template>
  <div>
    <label
      v-if="props.title"
      for="am-select"
      class="block mb-2 text-xl font-medium text-sky-600"
    >
      {{ props.title }}
    </label>

    <select
      id="am-select"
      @change="onChange"
      class="block w-full px-4 py-3 text-base text-gray-900
             border-solid border border-grey-300 rounded-lg
             bg-white focus:ring-blue-500 focus:border-blue-500">
    >
      <option v-if="!props.selectedValue && !chosenValue" value="" class="text-gray-500">
        Please select one
      </option>
      <option class="bg-white text-sky-600 hover:text-white"
              v-for="option in selectedOnTop"
              :key="option"
              :value="option"
      >
        {{ option }}
      </option>
    </select>
  </div>
</template>
<script setup lang="ts">
import { computed, ref } from 'vue'

const emit = defineEmits<{(
    e: 'change',
    value: string
  ): void
}>()
const chosenValue = ref()
const props = withDefaults(defineProps<{
  selectedValue?: string | undefined | null
  options?: string[] | undefined | null,
  title?: string | null
}>(), {
  selectedValue: null,
  options: () => [],
  title: null
})

const selectedOnTop = computed(() => {
  if (props.selectedValue && props.options && props.options.length > 0) {
    const indexOfSelected = props.options?.indexOf(props.selectedValue)
    const copyOfOptions = props.options

    copyOfOptions.splice(indexOfSelected, 1)
    copyOfOptions.unshift(props.selectedValue)

    return copyOfOptions
  }

  return props.options
})

const onChange = (e: any) => {
  const value = e.target.value

  chosenValue.value = value

  if (value !== '') {
    emit('change', value)
  }
}
</script>
<script lang="ts">
export default {
  name: 'AmSelect'
}
</script>
