<template>
  <div>
  <h2 style="color: darkred"> Past reservations:</h2>
  <br>
  <div v-for="reservation in adventures" :key="reservation.id">
    <b-card
        tag="adventure"
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
      <button @click="writeComplaint(reservation.id, reservation.text)" type="reset" class="btn-danger">CLOSE</button>
    </b-card>
  </div>
  </div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
import ComplaintService from "@/Services/ComplaintService";
import LogInService from "@/Services/LogInService";

export default {
  name: "Complaint",
  data() {
    return {
      adventures : [],
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
  },
  methods:{
    writeComplaint(id, text){
      ComplaintService.writeComplaint(id, text).then(()=>{
        alert("Complaint successfully added");
        this.$router.push('/');
      }).catch(() => alert("Some error occurred"));
    }
  }
}
</script>

<style scoped>

</style>
