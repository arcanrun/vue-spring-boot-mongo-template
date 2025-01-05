import { defineStore } from 'pinia'

export const useLoaderStore = defineStore('loader', {
  state: () => ({
    isLoading: false,
    isOverlayLoader: false
  }),
  getters: {},
  actions: {
    showOverlayLoader () {
      this.isOverlayLoader = true
      this.showLoader()
    },
    hideOverlayLoader () {
      this.isOverlayLoader = false
      this.hideLoader()
    },
    showLoader () {
      this.isLoading = true
    },
    hideLoader () {
      this.isLoading = false
    }
  }
})
