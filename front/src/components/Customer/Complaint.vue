<template>
  <div>
  <h2 style="color: darkred"> Past reservations:</h2>
  <br>
    <h3 style="margin-left: 5px; color: darkred">Adventures:</h3>
  <div v-for="reservation in adventures" :key="reservation.id">
    <b-card
        name="adventure"
        style="max-width: 50rem; margin-left: 20px; margin-right: 20px"
        class="mb-2"
    >
      <b-card-title>
        {{reservation.adventure.name}}
      </b-card-title>
      <br>
      <b-card-sub-title>
        Instructor: {{reservation.adventure.instructor.firstname}} {{reservation.adventure.instructor.lastName}}
      </b-card-sub-title>
      <br>
      <b-card-sub-title>
        Description: {{reservation.adventure.description}}
      </b-card-sub-title>

      <b-card-text>
        {{ reservation.reservationStart | formatDate}} - length in hours: {{ reservation.length}}
      </b-card-text>
      <br>
      <b-card-text>
        PRICE:  {{ reservation.price }}
      </b-card-text>
      <b-form-textarea
          id="textarea"
          v-model="reservation.text"
          placeholder="Enter you complaint here"
          rows="3"
          max-rows="6"
      ></b-form-textarea>
      <br>
      <button @click="writeComplaintForAdventure(reservation.id, reservation.text)" type="reset" class="btn-danger">SEND COMPLAINT</button>
    </b-card>

    </div>
    <br>
    <h3 style="margin-left: 5px; color: darkred">Cottages:</h3>
    <div v-for="reservation in cottages" :key="reservation.id">
      <b-card
          name="cottage"
          style="max-width: 50rem; margin-left: 20px; margin-right: 20px"
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
        <br>
        <b-card-text>
          PRICE:  {{ reservation.price }}
        </b-card-text>
        <b-form-textarea
            id="textarea"
            v-model="reservation.text"
            placeholder="Enter you complaint here"
            rows="3"
            max-rows="6"
        ></b-form-textarea>
        <br>
        <button @click="writeComplaintForCottage(reservation.id, reservation.text)" type="reset" class="btn-danger">SEND COMPLAINT</button>
      </b-card>
    </div>
    <br>
  </div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
import ComplaintService from "@/Services/ComplaintService";
import LogInService from "@/Services/LogInService";
import CottageReservationService from "@/Services/CottageReservationService";

export default {
  name: "Complaint",
  data() {
    return {
      adventures : [],
      cottages: []
    }
  },
  mounted() {
    AdventureReservationService.getAllPastTermsByCustomerIdWithPutComplaint(LogInService.userId).then(res =>  {
      console.log(res.data)
      this.adventures = res.data;
      for(let ad of this.adventures){
        ad.text = '';
      }
    });
    CottageReservationService.getAllPastTermsByCustomerIdWithPutComplaint(LogInService.userId).then(res => {
      console.log(res.data);
      this.cottages = res.data;
      for (let cr of this.cottages)
        cr.text = '';
    });

  },
  methods:{
    writeComplaintForAdventure(id, text){
      ComplaintService.writeComplaint(id, text).then(()=>{
        alert("Complaint successfully added");
        this.$router.push('/');
      }).catch(() => alert("Some error occurred"));
    },
    writeComplaintForCottage(id, text){
      ComplaintService.writeComplaintForCottage(id, text).then(()=>{
        alert("Complaint successfully added");
        this.$router.push('/');
      }).catch(() => alert("Some error occurred"));
    }
  }
}
</script>

<style scoped>

</style>
