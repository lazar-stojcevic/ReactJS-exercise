<template>
  <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd; padding: 5px">
    <div class="container-fluid" style="align-items: self-start; justify-content: flex-start">
      <router-link class="btn btn-secondary m-2" to="/">Home</router-link>
      <!--CUSTOMER_ROLE-->
      <b-nav-item-dropdown text="Past reservation" class="align-content-lg-end list-unstyled m-2" right :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'">
        <b-dropdown-item href="#/cottageReservations">Cottages</b-dropdown-item>
        <b-dropdown-item href="#/adventureReservations">Adventures</b-dropdown-item>
        <b-dropdown-item href="#/boatReservations">Boats</b-dropdown-item>
      </b-nav-item-dropdown>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'"
                   to="/bookedReservations">Booked adventures</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'"
                   to="/customerProfile">Profile</router-link>

      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'"
                   to="/penaltyPoints">Penalty points</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'"
                   to="/subscriptions">Subscriptions</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'"
                   to="/complaint">Complaint</router-link>

      <!--COTTAGE_OWNER_ROLE-->
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole !== 'ROLE_COTTAGE_OWNER'" to="/myCottageReservation">COTTAGE RESERVATIONS</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole !== 'ROLE_COTTAGE_OWNER'" to="/availability">COTTAGE AVAILABILITY</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole !== 'ROLE_COTTAGE_OWNER'" to="/availablePeriodsWithBenefits">CREATE BENEFITS</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole !== 'ROLE_COTTAGE_OWNER'" to="/reservationReport">CREATE REPORT</router-link>
      <router-link class="btn btn-secondary m-2" :hidden="LogInService.userRole !== 'ROLE_COTTAGE_OWNER'" to="/createReservationForCustomer">CREATE RESERVATION</router-link>
    </div>

    <!--FISHING_INSTRUCTOR_ROLE-->
    <div class="btn-group" v-if="LogInService.userRole === 'ROLE_INSTRUCTOR'">
      <router-link class=" btn btn-secondary" to="/createAdventure">CREATE ADVENTURE</router-link>
      <router-link class=" btn btn-outline-secondary" to="/reservationHistory">HISTORY OF RESERVATIONS</router-link>
      <router-link class=" btn btn-secondary" to="/fishingInstructorProfile">PROFILE</router-link>
    </div>

    <!--ADMIN-->
    <div class="btn-group" v-if="LogInService.userRole === 'ROLE_ADMIN'">
      <div class="btn-group dropstart">
        <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
          MENU
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
          <li><router-link class="dropdown-item" to="/createNewAdmin">CREATE ADMIN</router-link></li>
          <li><hr class="dropdown-divider"></li>
          <li><router-link class="dropdown-item" to="/profileRequests">REQUESTS</router-link></li>
          <li><hr class="dropdown-divider"></li>
          <li><router-link class="dropdown-item" to="/adminProfile">PROFILE</router-link></li>
        </ul>
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
.dropdown-menu {
  color: darkred;
  background-color: silver;
  font-weight: bolder;
}
</style>
