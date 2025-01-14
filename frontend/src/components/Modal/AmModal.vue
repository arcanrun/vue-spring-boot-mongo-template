<template>
  <AmOverlay
    :value="props.modelValue"
    :close-overlay-on-click="true"
    @click="() => emit('update:modelValue')"
  >
    <transition
      name="modal-scale-transition"
      enter-active-class="transition ease-in-out duration-500 transform"
      enter-class="scale-0 opacity-0"
      enter-to-class="scale-100"
      leave-active-class="transition ease-in-out duration-300 transform"
      leave-class="scale-100"
      leave-to-class="scale-0 opacity-0"
    >
      <div v-show="props.modelValue" :class="classes">
        <div class="relative p-5">
          <div>
            <AmModalSvg v-if="props.level !== null" :level="props.level" />

            <div>
              <h3 class="text-center text-lg font-medium text-secondary-900">
                {{ props.cardTitle }}
              </h3>
              <div class="text-center mt-2 text-sm text-secondary-500">
                {{ props.cardText }}
              </div>
            </div>

            <slot></slot>
          </div>

          <div v-if="!hideControls" class="mt-5 flex justify-start gap-3">
            <AmBtn class="w-full" :level="BtnLevel.INFO" @click="() => emit('update:modelValue')">
              {{ props.cancelBtnTitle }}
            </AmBtn>
            <AmBtn class="w-full" :level="BtnLevel.ERROR" @click="performOk">
              {{ props.okBtnTitle }}
            </AmBtn>
          </div>
        </div>
      </div>
    </transition>
  </AmOverlay>
</template>
<script setup lang="ts">
import { AmBtn, BtnLevel } from '@/components/Button'
import AmModalSvg from '@/components/Modal/AmModalSvg.vue'
import { ModalLevel } from '@/components/Modal/enums/level'
import AmOverlay from '@/components/AmOverlay.vue'
import { computed } from 'vue'

const emit = defineEmits(['onOk', 'update:modelValue'])
const props = withDefaults(
  defineProps<{
    modelValue?: boolean
    cardTitle?: string
    cardText?: string | null
    okBtnTitle?: string
    cancelBtnTitle?: string
    level?: ModalLevel | null
    hideControls?: boolean
    widthClass?: string | null
  }>(),
  {
    value: false,
    cardTitle: 'Perform this action',
    cardText: 'Are you sure you want to perform this action?',
    okBtnTitle: 'ok',
    cancelBtnTitle: 'cancel',
    level: null,
    hideControls: false,
    widthClass: null,
  },
)

const classes = computed(() => {
  const classes = ['z-40', 'mx-auto', 'overflow-hidden,', 'rounded-lg', 'bg-white', 'shadow-xl']

  if (props.widthClass) {
    return [...classes, props.widthClass]
  }

  return [...classes, 'w-fit']
})

const performOk = () => {
  emit('onOk')
}
</script>
<script lang="ts">
export default {
  name: 'AmModal',
}
</script>
