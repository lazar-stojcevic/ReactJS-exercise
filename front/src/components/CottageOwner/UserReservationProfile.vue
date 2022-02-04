<template>
  <div>
    <table class="table table-striped">
      <tbody>
      <tr>
        <td>FULL NAME</td>
        <td>{{user.firstname}} {{user.lastName}}</td>
      </tr>
      <tr>
        <td>FULL ADDRESS</td>
        <td v-if="user.address !== null">{{user.address.street}}, {{user.address.city}}, {{user.address.country}}</td>
        <td v-else>ADDRESS IS NOT SET</td>
      </tr>
      <tr>
        <td>EMAIL</td>
        <td>{{user.email}}</td>
      </tr>
      <tr>
        <td>PHONE</td>
        <td>{{user.phone}}</td>
      </tr>
      <tr>
        <td>REWARD POINTS</td>
        <td>{{user.points}}</td>
      </tr>
      <tr v-if="user.points > 100">
        <td>USER CATEGORY</td>
        <td>*PLATINUM* - 5% discaunt on all adventures</td>
      </tr>
      <tr v-else-if="user.points > 50">
        <td>USER CATEGORY</td>
        <td>*GOLD* - 3% discaunt on all adventures</td>
      </tr>
      <tr v-else>
        <td>USER CATEGORY</td>
        <td>Need more point from price reduction</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CustomerService from "../../Services/CustomerService";

export default {
  name: "UserReservationProfile",
  data(){
    return{
      id: '',
      user: ''
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    CustomerService.getCustomerId(this.id).then(res => {
      this.user = res.data;
    });
  }
}
</script>

<style scoped>

</style>