<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd; padding: 5px">
      <div class="container-fluid">
        <router-link class="btn btn-secondary" to="/">Home</router-link>
      </div>
      <span v-bind:hidden="userRole === 'neulogovan'"><button class="btn btn-secondary" style="margin: 5px" @click="logout()">LogOut</button></span>
      <span v-bind:hidden="userRole !== 'neulogovan'"><router-link class="btn btn-secondary" to="/login" style="margin: 5px">LogIn</router-link></span>
      <router-link class="btn btn-secondary" to="/registration" style="margin: 5px">Registration</router-link>
    </nav>

    <!--RUTER-->
    <router-view/>
  </div>
</template>

<script>

import LogInService from "@/Services/LogInService";

export default {
  data() {
    return {
      userRole : 'neulogovan',
      isLogged: false
    }
  },
  methods: {
    mounted(){
      this.userRole = LogInService.userRole;
    },
    logout(){
      LogInService.logout();
    },
    setUserRole(userRole){
      this.userRole = userRole;
      if(this.userRole === '')
        this.isLogged = false;
      else
        this.isLogged = true;
      alert(this.userRole);
    }
  }
}
</script>

<style>

</style>
