<template>
  <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd; padding: 5px">
    <div class="container-fluid" style="align-items: self-start">
      <router-link class="btn btn-secondary" to="/">Home</router-link>
      <!--FISHING_INSTRUCTOR_ROLE-->
      <div class="btn-group" :hidden="LogInService.userRole.trim() !== 'ROLE_INSTRUCTOR'">
        <router-link class=" btn btn-secondary" to="/createAdventure">CREATE ADVENTURE</router-link>
        <router-link class=" btn btn-outline-secondary" to="/">HISTORY OF RESERVATIONS</router-link>
        <router-link class=" btn btn-secondary" to="/fishingInstructorProfile">PROFILE</router-link>
      </div>
    </div>
    <button :hidden="LogInService.userRole === ''" class="btn btn-outline-secondary" style="margin: 5px" @click="logout()">LogOut</button>
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