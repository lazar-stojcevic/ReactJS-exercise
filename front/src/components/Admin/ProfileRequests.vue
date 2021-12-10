<template>
  <!--USER SERVICE SAMO ZA ENEJBLOVANJE KORISNIKA-->
  <div class="container">
    <table class="table table-striped">
      <thead>
        <tr>
          <td>EMAIL</td>
          <td>FULL NAME</td>
          <td>ROLE</td>
          <td>FULL ADDRESS</td>
          <td>PHONE</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{user.email}}</td>
          <td>{{user.firstname}} {{user.lastName}}</td>
          <td>{{user.role.name}}</td>
          <td>{{user.address.street}} {{user.address.city}} {{user.address.country}}</td>
          <td>{{user.phone}}</td>
          <td><button class="btn btn-success">ENABLE</button></td>
          <td><button class="btn btn-danger">DISABLE</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import AdminService from "@/Services/AdminService";
export default {
  data(){
    return{
      users: []
    }
  },
  mounted() {
    AdminService.getAllNotEnabledUsers().then(res => {this.users = res.data}).catch(() => {
      alert("THERE IS SOME PROBLEM WITH LOADING USERS")
    });
  },
  methods:{
    enable(){

    },
    disable(){

    }
  }
}
</script>

<style scoped>

</style>