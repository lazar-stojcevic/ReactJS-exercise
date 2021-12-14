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
          <td><button class="btn btn-success" @click="enableUser(user.id)">ENABLE</button></td>
          <td><button class="btn btn-danger" @click="deleteUser(user.id)">DELETE</button></td>
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
    this.loadUserRequests();
  },
  methods:{
    enableUser(userId){
      AdminService.acceptUserRequest(userId).then(() => {
        alert('USER IS ENABLED');
        this.loadUserRequests();
      }).catch(() => {alert('THERE IS SOME ERROR WITH ENABLING USER')});
    },
    deleteUser(userId){
      AdminService.notAcceptUserRequest(userId).then(() => {
        alert('USER IS DELETED');
        this.loadUserRequests();
      }).catch(() => {alert('THERE IS SOME ERROR WITH DELETING USER')});
    },
    loadUserRequests(){
      AdminService.getAllNotEnabledUsers().then(res => {this.users = res.data}).catch(()=>{
        alert("THERE IS SOME PROBLEM WITH LOADING USERS")
      })
    }
  }
}
</script>

<style scoped>

</style>