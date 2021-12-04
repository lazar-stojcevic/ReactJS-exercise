<template>
  <div class="container">
    <div class="myClass" v-for="adventure in myAdventures" :key="adventure.id">
      <strong>{{adventure.name}}</strong>
      <table>
        <tr>
          <td>MAXIMUM PERSONS</td>
          <td>{{adventure.maxPersons}}</td>
        </tr>
        <tr>
          <td>ADDRES</td>
          <td>{{adventure.address.street}} {{adventure.address.city}} {{adventure.address.country}}</td>
        </tr>
        <tr>
          <td>INSTRUCTOR</td>
          <td>{{user.firstname}} {{user.lastName}}</td>
        </tr>
        <tr>
          <td>DESCRIPTION</td>
          <td>{{adventure.description}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import LogInService from "@/Services/LogInService";
export default {
  data(){
    return{
      myAdventures: '',
      user:'',
      LogInService
    }
  },
  //avanture i user
  mounted() {
  },
  methods: {

  }

}
</script>

<style scoped>
.myClass{
  margin: 10px;
  background: gainsboro;
  text-align: center;
  border: dotted 1px silver;
}
.myClass:hover{
  border-color: darkred;
  cursor: pointer;
}
</style>