<template>
  <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd; padding: 5px">
    <div class="container-fluid">
      <router-link class="btn btn-secondary" to="/">Home</router-link>
    </div>
    <button :hidden="LogInService.userRole === ''" class="btn btn-secondary" style="margin: 5px" @click="logout()">LogOut</button>
    <router-link :hidden="LogInService.userRole !== ''" class="btn btn-secondary" to="/login" style="margin: 5px">LogIn</router-link>
    <router-link :hidden="LogInService.userRole !== ''" class="btn btn-secondary" to="/registration" style="margin: 5px">Registration</router-link>
  </nav>
</template>

<script>
import LogInService from "@/Services/LogInService";
export default {
  data() {
    return {
      LogInService
    }
  },
  methods: {
    logout(){
      LogInService.logout();
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>

</style>