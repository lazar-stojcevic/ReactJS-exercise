import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import 'bootstrap/dist/css/bootstrap.min.css'
import BootstrapVue from 'bootstrap-vue';
import routes from "@/router/routes";
import Navbar from "@/components/Navbar";

Vue.component('my-navbar', Navbar)

Vue.use(VueRouter);
const router = new VueRouter({routes})

Vue.config.productionTip = false
Vue.use(BootstrapVue)

new Vue({
  router,
  el: '#app',
  data() {
    return {

    }
  },
  render: h => h(App)
}).$mount("#app");
