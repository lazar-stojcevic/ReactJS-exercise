<template>
  <div class="container w-100">
    <br>
    <h2 style="color: darkred">Subscriptions:</h2>
    <h3 style="color: darkred; margin-left: 20px; margin-top: 20px;">Fishing instructors: </h3>
    <div style="margin-left: 20px" v-for="instructor in instructors" :key="instructor.id">
      <b-card
          name="instructor"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{instructor.firstname}} {{instructor.lastName}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Email: {{instructor.email}}
        </b-card-sub-title>
        <br>
        <b-card-sub-title>
          Phone number: {{instructor.phone}}
        </b-card-sub-title>
        <br>
        <b-button  variant="danger" v-on:click="unsubscribe(instructor.id)">Unsubscribe</b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
import CustomerService from "@/Services/CustomerService";
import LogInService from "@/Services/LogInService";
import FishingInstructorService from "@/Services/FishingInstructorService";
export default {
  name: "Subscriptions",
  data() {
    return {
      instructors : [],
    }
  },
  mounted() {
    CustomerService.getAllCustomerInstructorsSubscriptions(LogInService.userId).then((res) =>{
      console.log(res.data);
      this.instructors = res.data;
    })
  },
  methods:{
    unsubscribe(instructorId){
      FishingInstructorService.unsubscribeInstructor(LogInService.userId, instructorId);
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>

</style>
