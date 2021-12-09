<template>
  <div>
    <h1>Name: {{cottage.name}}</h1>
    <br>
    <h2>Rules: {{cottage.conductRules}} </h2>
    <br>
    <h3>Address: {{cottage.address.street}},{{cottage.address.city}},{{cottage.address.country}} </h3>
    <br>
    <p>Description: {{cottage.promo}} </p>
    <br>
    <h3>Rating: {{cottage.rating}} </h3>
  </div>
</template>

<script>
import CottageService from "@/Services/CottageService";
export default {
  name: "OneCottage",
  data(){
    return{
      id: '',
      cottage: {
        name: '',
        conductRules: '',
        address: {
          street: '',
          city: '',
          country: ''
        },
        promo: '',
        rating: 0
      },
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    CottageService.getCottageById(this.id).then(res => {
      this.cottage = res.data;
    });
  }
}
</script>

<style scoped>

</style>
