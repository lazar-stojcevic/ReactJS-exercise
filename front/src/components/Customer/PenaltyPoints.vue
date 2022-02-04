<template>
  <div style="display: flex">
    <h1 style="margin-right: 5px">You penalty points: </h1><h1 style="color: red"> {{this.penalty}}</h1>
  </div>
</template>

<script>
import LogInService from "@/Services/LogInService";
import CustomerService from "@/Services/CustomerService";
export default {
  name: "PenaltyPoints",
  data(){
    return{
      penalty : 0
    }
  },
  mounted() {
    if(LogInService.userRole === '')
      this.$router.push('/login');
    CustomerService.getCustomerPenaltyPoints(LogInService.userId).then(res => {
      this.penalty = res.data;
    });
  },
}
</script>

<style scoped>

</style>
