<template>
  <div>
    <form @submit.prevent="filterUsers">
      <table>
        <tr>
          <td>FILTER BY ENABLED:</td>
          <td>
            <select v-model="filterEnable">
              <option value="">ALL</option>
              <option value= 'true' >ENABLED</option>
              <option value= 'false' >NOT ENABLED</option>
            </select>
          </td>
          <td></td>
          <td>FILTER BY ROLE:</td>
          <td>
            <select v-model="filterRole">
              <option value="">ALL</option>
              <option value= 'ROLE_CUSTOMER' >CUSTOMERS</option>
              <option value= 'ROLE_INSTRUCTOR' >INSTRUCTORS</option>
              <option value= 'ROLE_COTTAGE_OWNER' >COTTAGE OWNERS</option>
              <option value= 'ROLE_BOAT_OWNER' >BOAT OWNERS</option>
            </select>
          </td>
          <td><button type="submit">SUBMIT</button></td>
        </tr>
      </table>
    </form>
    <h3><strong>USERS</strong></h3>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>EMAIL</td>
        <td>FULL NAME</td>
        <td>ROLE</td>
        <td>PHONE</td>
        <td>ENABLED</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in usersToShow" :key="user.id">
        <td>{{user.email}}</td>
        <td>{{user.firstname}} {{user.lastName}}</td>
        <td>{{user.role.name}}</td>
        <td>{{user.phone}}</td>
        <td>{{user.enabled}}</td>
        <td v-if="user.enabled.toString() === 'true'"><button @click="disableUser(user.id)" class="btn btn-danger">DISABLE</button></td>
        <td v-else><button @click="enableUser(user.id)" class="btn btn-success">ENABLE</button></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import UserService from "@/Services/UserService";

export default {
  data(){
    return{
      users: [],
      usersToShow: [],
      filterEnable: '',
      filterRole: ''
    }
  },
  mounted() {
    this.loadAllUsers();
  },
  methods:{
    loadAllUsers(){
      UserService.getAllUsers().then(res => { this.users = res.data; this.usersToShow = res.data}).catch(() => {
        alert("THERE IS SOME PROBLEM WITH LOADING USERS")
      });
    },

    filterUsers(){
      let filterUsers = []
      for(let user of this.users){
        if(user.enabled.toString().toLowerCase().includes(this.filterEnable) &&
          user.role.name.toString().includes(this.filterRole))
          filterUsers.push(user)
      }
      this.usersToShow = filterUsers;
    },

    enableUser(id){
      UserService.enableUser(id).then(() => {this.loadAllUsers()}).catch(() =>
      {alert("THERE IS SOME PROBLEM WITH ENABLING USER")})
    },

    disableUser(id){
      UserService.disableUser(id).then(() => {this.loadAllUsers()}).catch(() =>
      {alert("THERE IS SOME PROBLEM WITH DISABLING USER")});
    }
  }
}
</script>

<style scoped>

</style>