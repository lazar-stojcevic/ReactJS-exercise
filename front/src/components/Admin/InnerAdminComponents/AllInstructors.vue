<template>
  <div>
    <h3><strong>FISHING INSTRUCTORS</strong></h3>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>EMAIL</td>
        <td>FULL NAME</td>
        <td>ROLE</td>
        <td>FULL ADDRESS</td>
        <td>PHONE</td>
        <td>ENABLED</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{user.email}}</td>
        <td>{{user.firstname}} {{user.lastName}}</td>
        <td>{{user.role.name}}</td>
        <td>{{user.address.street}} {{user.address.city}} {{user.address.country}}</td>
        <td>{{user.phone}}</td>
        <td>{{user.enabled}}</td>
        <td><button class="btn btn-danger">DELETE</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import FishingInstructorService from "@/Services/FishingInstructorService";

export default {
  data(){
    return{
      users: []
    }
  },
  mounted() {
    FishingInstructorService.getAllFishingInstructors().then(res => { this.users = res.data}).catch(() => {
      alert("THERE IS SOME PROBLEM WITH LOADING INSTRUCTORS")
    });
  },
  methods:{

  }
}
</script>

<style scoped>

</style>