import { defineStore } from 'pinia'

export const useLayoutStore = defineStore('layout', {
  state: () => ({
    drawer: false,
    header: true
  }),
  actions: {
  },
  getters: {}
})
