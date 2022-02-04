<template>
  <div>
    <h3><strong>COTTAGES</strong></h3>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>NAME</td>
        <td>FULL ADDRESS</td>
        <td>RATING</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="cottage in cottages" :key="cottage.id">
        <td>{{cottage.name}}</td>
        <td>{{cottage.address.street}} {{cottage.address.city}} {{cottage.address.country}}</td>
        <td>{{cottage.rating}}</td>
        <td><button class="btn btn-danger" @click="deleteCottage(cottage.id)">DELETE</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import CottageService from "@/Services/CottageService";

export default {
  data(){
    return{
      cottages: []
    }
  },
  mounted() {
    this.loadAllCottage();
  },
  methods:{
    loadAllCottage(){
      CottageService.getAllCottages().then(res => {this.cottages = res.data;}).catch(() => {
        alert("THERE IS SOME PROBLEM WITH LOADING ALL COTAGES");
      });
    },

    deleteCottage(id) {
      CottageService.delete(id).then(() => {this.loadAllCottage()}).catch(() => {
        alert("THERE IS SOME PROBLEM WITH DELETING COTTAGE");
      })
    }
  }
}
</script>

<style scoped>

</style>