<template>
  <div>
    <h1>Name: {{customer.name}}</h1>
    <br>
    <h2>Email: {{customer.email}} </h2>
    <br>
    <h3>Address: {{customer.address}} </h3>
    <br>
    <h3>Phone: {{customer.phone}} </h3>
  </div>
</template>

<script>
export default {
  name: "UserReservationProfile",
  data(){
    return{
      customer: {
        name:"Marko Markovic",
        email: "nekimejl@gmail.com",
        address: "Masarikova, Sabac, Srbija",
        phone: "0605124783"
      }
    }
  }
}
</script>

<style scoped>

</style>