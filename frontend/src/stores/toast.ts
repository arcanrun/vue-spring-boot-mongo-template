import { defineStore } from 'pinia'
import { v4 as uuidv4 } from 'uuid'

const DEFAULT_TOAST_TIME_OUT = 6000

export const enum ToastType {
  INFO,
  ERROR,
  SUCCESS,
  WARNING,
}

export interface IToast {
  id: string;
  timeOut: number;
  type?: ToastType | null;
  message?: string | null;
  visible: boolean;
}

export const useToastStore = defineStore('toast', {
  state: () => ({ list: [] as IToast[] }),
  actions: {
    showInfo (message?: string) {
      this.addToast(ToastType.INFO, message)
    },
    showError (message?: string) {
      this.addToast(ToastType.ERROR, message)
    },
    showSuccess (message?: string) {
      this.addToast(ToastType.SUCCESS, message)
    },
    showWarning (message?: string) {
      this.addToast(ToastType.WARNING, message)
    },
    remove (id: string) {
      const idToRemove = this.$state.list.findIndex((e) => e.id === id)

      this.$state.list.splice(idToRemove, 1)
    },
    addToast (type?: ToastType, message?: string) {
      const id = uuidv4()

      this.$state.list.push({
        id,
        timeOut: DEFAULT_TOAST_TIME_OUT,
        type: type,
        message: message,
        visible: true
      })

      this.setTimeOutTimer(id, DEFAULT_TOAST_TIME_OUT)
    },
    setTimeOutTimer (id: string, timer: number) {
      setTimeout(() => {
        this.remove(id)
      }, timer)
    }
  },
  getters: {}
})
