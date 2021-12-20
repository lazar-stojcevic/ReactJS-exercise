<template>
  <div class="container">
  <b-card
      img-width="80vw"
      img-height="250vh"
      overlay
      :img-src="slika1"
      img-alt="Card Image"
      text-variant="white"
      title="Renting cottages"
  >
    <b-card-text>
      Cottages in all environment.
    </b-card-text>
    <br>
    <br>
    <br>
    <br>
    <router-link class="btn btn-secondary" to="/allcottages" style="margin: 5px">See cottages</router-link>
  </b-card>
    <br>
    <b-card
        img-width="80vw"
        img-height="250vh"
        overlay
        :img-src="slika2"
        img-alt="Card Image"
        text-variant="white"
        title="Fishing instructors"
    >
      <b-card-text>
        Unforgettable avantures with experienced instructor!
      </b-card-text>
      <br>
      <br>
      <br>
      <br>
      <router-link class="btn btn-secondary" to="/allinstructors" style="margin: 5px">See advantures!</router-link>
      <router-link class="btn btn-secondary" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'" to="/reserveAdventure" style="margin: 5px">
        Reserve adventure
      </router-link>
    </b-card>
    <br>


    <b-card
        img-width="80vw"
        img-height="250vh"
        overlay
        :img-src="slika3"
        img-alt="Card Image"
        text-variant="white"
        title="Renting boats"
    >
      <b-card-text>
        Boats of all types!
      </b-card-text>
      <br>
      <br>
      <br>
      <br>
      <router-link class="btn btn-secondary" to="/allboats" style="margin: 5px">See boats</router-link>
    </b-card>
  </div>
</template>

<script>
import slika1 from "@/assets/vikendica.jpg";
import slika2 from "@/assets/fishing.jpg";
import slika3 from "@/assets/boat.jpg";
import LogInService from "@/Services/LogInService";
export default {
  data() {
    return {
      slika1 : slika1,
      slika2 : slika2,
      slika3 : slika3,
      LogInService
    }
  },
  mounted() {
    if (localStorage.getItem("userId") === null){
      this.$forceUpdate()
    }
  }
}
</script>

<style scoped>

</style>
