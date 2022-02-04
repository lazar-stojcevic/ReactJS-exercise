<template>
  <div class="container">

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">ENTER REASON FOR DELETING</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <input type="text" class="form-control-plaintext" placeholder="Enter reason" v-model="reason"/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">CLOSE</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal" @click="deleteUser">DELETE USER</button>
          </div>
        </div>
      </div>
    </div>

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
        <td><button class="btn-sm btn-success" @click="enableUser(user.id)">ENABLE</button></td>
        <td>
          <button @click="prepareUserForDeleting(user.id)" type="button" class="btn-sm btn-warning"
                  data-bs-toggle="modal" data-bs-target="#exampleModal">DELETE</button>
        </td>
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
      users: [],
      userId: '',
      reason: ''
    }
  },
  mounted() {
    this.loadUserRequests();
  },
  methods:{
    enableUser(userId){
      AdminService.acceptUserRequest(userId).then(() => {
        this.loadUserRequests();
        alert('USER IS ENABLED');
      }).catch(() => {alert('THERE IS SOME ERROR WITH ENABLING USER')});

    },

    deleteUser(){
      if(this.reason === ''){
        alert("REASON IS REQUIRED")
        return;
      }
      AdminService.notAcceptUserRequest(this.userId, this.reason).then(() => {
        this.loadUserRequests();
        alert('USER IS DELETED');
      }).catch(() => {alert('THERE IS SOME ERROR WITH DELETING USER')});
    },

    loadUserRequests(){
      AdminService.getAllNotEnabledUsers().then(res => {this.users = res.data}).catch(()=>{
        alert("THERE IS SOME PROBLEM WITH LOADING USERS")
      })
    },

    prepareUserForDeleting(userId){
      this.userId = userId;
      this.reason = '';
    }
  }

}
</script>

<style scoped>

</style>