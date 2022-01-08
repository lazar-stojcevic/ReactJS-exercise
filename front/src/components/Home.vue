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
      :hidden="LogInService.userRole.trim() === 'ROLE_COTTAGE_OWNER'"
  >
    <b-card-text>
      Cottages in all environment.
    </b-card-text>
    <br>
    <br>
    <br>
    <br>
    <router-link class="btn btn-secondary" to="/allcottages" style="margin: 5px">See cottages</router-link>
    <router-link class="btn btn-secondary" :hidden="LogInService.userRole.trim() !== 'ROLE_CUSTOMER'" to="/reserveCottage" style="margin: 5px">
      Reserve cottage
    </router-link>

  </b-card>
    <br>
    <b-card
        img-width="80vw"
        img-height="250vh"
        overlay
        :img-src="slika1"
        img-alt="Card Image"
        text-variant="white"
        title="Cottages"
        :hidden="LogInService.userRole.trim() !== 'ROLE_COTTAGE_OWNER'"
    >
      <b-card-text>
        Cottages in all environment.
      </b-card-text>
      <br>
      <br>
      <br>
      <br>
      <router-link class="btn btn-secondary" to="/usersCottage" style="margin: 5px">See cottages</router-link>
      <router-link class="btn btn-secondary" to="/createCottage" style="margin: 5px">Create cottage</router-link>

    </b-card>
    <br>
    <b-card
        img-width="80vw"
        img-height="250vh"
        overlay
        :img-src="slika4"
        img-alt="Card Image"
        text-variant="white"
        title="User profile"
        :hidden="LogInService.userRole.trim() !== 'ROLE_COTTAGE_OWNER'"
    >
      <b-card-text>

      </b-card-text>
      <br>
      <br>
      <br>
      <br>
      <router-link class="btn btn-secondary" to="/cottageOwnerProfile" style="margin: 5px">See profile</router-link>

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
        :hidden="LogInService.userRole.trim() === 'ROLE_COTTAGE_OWNER'"
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
        :hidden="LogInService.userRole.trim() === 'ROLE_COTTAGE_OWNER'"
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
import slika4 from "@/assets/user-profile.jpg";
import LogInService from "@/Services/LogInService";
export default {
  data() {
    return {
      slika1 : slika1,
      slika2 : slika2,
      slika3 : slika3,
      slika4: slika4,
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
