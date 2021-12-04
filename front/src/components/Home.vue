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
    <b-button href="/cottages" variant="primary">See cottages</b-button>
  </b-card>
    <br>
    <b-card
        img-width="80vw"
        img-height="250vh"
        overlay
        :img-src="slika2"
        img-alt="Card Image"
        text-variant="white"
        title="Hiring instructors"
    >
      <b-card-text>
        Unforgettable avantures with experienced instructor!
      </b-card-text>
      <br>
      <br>
      <br>
      <br>
      <b-button href="/avanture" variant="primary">See adventures</b-button>
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
      <b-button href="/boats" variant="primary">See boats</b-button>
    </b-card>
  </div>
</template>

<script>
import slika1 from "@/assets/vikendica.jpg";
import slika2 from "@/assets/fishing.jpg";
import slika3 from "@/assets/boat.jpg";
export default {
  data() {
    return {
      slika1 : slika1,
      slika2 : slika2,
      slika3 : slika3
    }
  }
}
</script>

<style scoped>
  .picture{
    width: 100%;
    height: 100px;
  }
</style>
