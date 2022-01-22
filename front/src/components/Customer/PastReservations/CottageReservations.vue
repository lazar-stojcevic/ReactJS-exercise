<template>
  <div class="container w-100">
    <div class="search">
      <b-form-select v-model="sort" class="m-3">
        <b-form-select-option value="dateASC">date descending</b-form-select-option>
        <b-form-select-option value="dateDESC">date ascending</b-form-select-option>
        <b-form-select-option value="priceDESC">price descending</b-form-select-option>
        <b-form-select-option value="priceASC">price ascending</b-form-select-option>
        <b-form-select-option value="lengthDESC">length descending</b-form-select-option>
        <b-form-select-option value="lengthASC">length ascending</b-form-select-option>
      </b-form-select>
      <b-input-group >
        <b-button variant="info" @click="search">Sort</b-button>
      </b-input-group>
    </div>
    <br>
    <p>Cottage reservation:</p>
    <div v-for="reservation in reservations" :key="reservation.id">
      <b-card
          name="cottage"
          style="max-width: 70rem; margin-left: 10rem; margin-right: 10rem"
          class="mb-2"
      >
        <b-card-title>
          {{reservation.cottage.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Cottage owner: {{reservation.cottage.cottageOwner.firstname}} {{reservation.cottage.cottageOwner.lastName}}
        </b-card-sub-title>
        <br>
        <b-card-sub-title>
          Description: {{reservation.cottage.promo}}
        </b-card-sub-title>

        <b-card-text>
          {{ reservation.reservationStart | formatDate}} - {{ reservation.reservationEnd | formatDate}}
        </b-card-text>
        <b-card-text>
          PRICE:  {{ reservation.price }}
        </b-card-text>
        <div v-if="!reservation.rated">
          <b-form-rating v-model="reservation.mark" variant="warning" class="mb-2"></b-form-rating>
          <b-form-textarea
              id="textarea"
              v-model="reservation.text"
              placeholder="Enter you comment here"
              rows="3"
              max-rows="6"
          ></b-form-textarea>
          <br>
          <button @click="sendGrade(reservation.id, reservation.mark, reservation.text)" type="reset" class="btn-danger">RATE</button>
        </div>
        <div v-else>
          <b-card-text>
            Already rated.
          </b-card-text>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import LogInService from "@/Services/LogInService";
import GradeService from "@/Services/GradeService";
import CottageReservationService from "@/Services/CottageReservationService";

export default {
  name: "CottageReservations",
  data() {
    return {
      reservations : [],
      filtered : [],
      filter: {
        dateFrom: new Date(),
        dateTo: new Date(),
        price : 0,
        name : '',
      },
      sort : 'nameASC'
    }
  },
  mounted() {
    CottageReservationService.getAllPastTermsByCustomerId(LogInService.userId).then(res =>  {
      console.log(res.data)
      this.reservations = res.data;
      for(let reservation of this.reservations){
        reservation.text = '';
        reservation.mark = 1;
      }
    });
  },
  methods:{
    search(){
      if (this.sort === 'dateASC')
        this.reservations.sort((a,b) =>
            (moment(a.reservationStart).format("dd-mm-yyyy") > moment(b.reservationStart).format("dd-mm-yyyy"))
            ? 1 : ((moment(b.reservationStart).format("dd-mm-yyyy") > moment(a.reservationStart).format("dd-mm-yyyy")) ? -1 : 0))
      else if (this.sort === 'dateDESC')
        this.reservations.sort((a,b) =>
            (moment(a.reservationStart).format("dd-mm-yyyy") < moment(b.reservationStart).format("dd-mm-yyyy"))
                ? 1 : ((moment(b.reservationStart).format("dd-mm-yyyy") < moment(a.reservationStart).format("dd-mm-yyyy")) ? -1 : 0))
      else if (this.sort === 'priceASC')
        this.reservations.sort((a,b) => (a.price > b.price) ? 1 : ((b.price > a.price) ? -1 : 0))
      else if (this.sort === 'priceDESC')
        this.reservations.sort((a,b) => (a.price < b.price) ? 1 : ((b.price < a.price) ? -1 : 0))
      else if (this.sort === 'lengthASC')
        this.reservations.sort((a,b) => (a.length > b.length) ? 1 : ((b.length > a.length) ? -1 : 0))
      else if (this.sort === 'lengthDESC')
        this.reservations.sort((a,b) => (a.length < b.length) ? 1 : ((b.length < a.length) ? -1 : 0))
    },
    sendGrade(id, mark, text){
      let data = {
        rating : mark,
        revision: text,
        entityId: id,
        entityType: 'C'
      };
      GradeService.saveGrade(data).then(()=>{
        alert("Grade successfully added");
        this.$router.push('/');
      }).catch(() => alert("Some error occurred"));
    }
  }
}
</script>

<style scoped>
.search{
  display: flex;
  align-items: center;
}
.datepicker{
  align-items: normal;
  flex-wrap: nowrap;
}
</style>
