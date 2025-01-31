import { defineStore } from 'pinia'

export const useLayoutStore = defineStore('layout', {
  state: () => ({
    drawer: false,
    header: true,
    searchBar: false
  }),
  actions: {
    toggleSearchBar() {
      this.searchBar = !this.searchBar
    }
  },
  getters: {}
})
