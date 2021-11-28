<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import LogIn from "./components/LogIn";
import VueRouter from 'vue-router'
import Vue from "vue";
import axios from "axios";

Vue.use(VueRouter)
const router = new VueRouter({
  mode: 'hash',
  base: '/home',
  routes: [
    { path: '/login', component: LogIn },
  ]
});

export default {
  router: router,
  name: 'App',
  components: {
  },
  data: function(){
    return{ tip: 'neulogovani'}
  },
  mounted() {
    axios.get('/ulogovaniKorisnik')
        .then(response => {
          if (response.data == null) {
            this.tip = "neulogovani"
          }
          else {
            this.tip = response.data
          }
          window.location.href = "#/home";
        })
  }
}
</script>

<style>

</style>
