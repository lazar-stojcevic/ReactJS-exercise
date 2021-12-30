<template>
  <div>
    <h3><strong>BOATS</strong></h3>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>NAME</td>
        <td>FULL ADDRESS</td>
        <td>RATING</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="boat in boats" :key="boat.id">
        <td>{{boat.name}}</td>
        <td>{{boat.address.street}} {{boat.address.city}} {{boat.address.country}}</td>
        <td>{{boat.rating}}</td>
        <td><button class="btn btn-danger">DELETE</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data(){
    return{
      boats: []
    }
  },

  mounted() {
  },

  methods:{
    loadAllBoats(){

    }
  }
}
</script>

<style scoped>

</style>