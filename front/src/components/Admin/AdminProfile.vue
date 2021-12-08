<template>
  <div class="container">
    <!--ADMIN INFO-->
    <table class="table table-striped" style="margin-top: 15px">
      <tbody>
      <tr>
        <td>FULL NAME</td>
        <td>{{user.firstname}} {{user.lastName}}</td>
      </tr>
      <tr>
        <td>FULL ADDRESS</td>
        <td>{{user.address.street}}, {{user.address.city}}, {{user.address.country}}</td>
      </tr>
      <tr>
        <td>EMAIL</td>
        <td>{{user.email}}</td>
      </tr>
      <tr>
        <td>PHONE</td>
        <td>{{user.phone}}</td>
      </tr>
      </tbody>
    </table>
    <hr style="margin-top: 10px"/>
    <!--BUTTONS-->
    <div>
      <table style="margin: 10px">
        <tr>
          <td>
            <button class="btn btn-primary" @click="showPasswordChange">CHANGE PASSWORD</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showInfoChange">CHANGE INFO</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showTaxChange">TAX</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showBoats">BOATS</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showCottages">COTTAGES</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showCustomers">CUSTOMERS</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showInstructors">INSTRUCTORS</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showBoatOwners">BOAT OWNERS</button>
          </td>
          <td>
            <button class="btn btn-primary" @click="showCottageOwners">COTTAGE OWNERS</button>
          </td>
        </tr>
      </table>
    </div>
    <!--CHANGE INFO-->
    <div v-if="mode === 'info'">
      <form @submit.prevent="changeUserInfo">
        <div class="input-group mb-3">
          <span class="input-group-text">NAME</span>
          <input type="text" class="form-control" v-model="newUserInfo.firstname" required>
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text">LASTNAME</span>
          <input type="text" class="form-control" v-model="newUserInfo.lastName" required>
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text">STREET</span>
          <input type="text" class="form-control" v-model="newUserInfo.address.street" required>
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text">CITY</span>
          <input type="text" class="form-control" v-model="newUserInfo.address.city" required>
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text">COUNTRY</span>
          <input type="text" class="form-control" v-model="newUserInfo.address.country" required>
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text">PHONE</span>
          <input type="text" class="form-control" v-model="newUserInfo.phone" required>
        </div>
        <div class="input-group mb-3">
          <div class="btn-group-sm">
            <button type="submit" class="btn-info">CONFIRM</button>
            <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
          </div>
        </div>
      </form>
    </div>
    <!--CHANGE PASSWORD-->
    <div v-if="mode === 'password'" class="container">
      <form @submit.prevent="changePassword">
        <div class="input-group mb-3">
          <span class="input-group-text">NEW PASSWORD</span>
          <input type="password" class="form-control" v-model="newPassword" required>
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text">CONFIRM PASSWORD</span>
          <input type="password" class="form-control" v-model="confirmPassword" required>
        </div>
        <div class="input-group mb-3">
          <div class="btn-group-sm">
            <button type="submit" class="btn-info">CONFIRM</button>
            <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
          </div>
        </div>
      </form>
    </div>
    <!--CHANGE TAX-->
    <div v-if="mode === 'tax'" class="container">
      <form @submit.prevent="changeTax">
        <div class="input-group mb-3">
          <span class="input-group-text">NEW TAX %</span>
          <input type="number" class="form-control" v-model="newTax" required>
        </div>
        <div class="btn-group-sm">
          <button type="submit" class="btn-info">CONFIRM</button>
          <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
        </div>
      </form>
    </div>
    <!--PERCENT OF ALL TRANSACTIONS-->

    <!--ALL ENTITIES-->

  </div>
</template>

<script>
import AdminService from "@/Services/AdminService";
import LogInService from "@/Services/LogInService";

export default {
  data(){
    return{
      user: {
        address:{
          street: ''
        }
      },
      mode:'neutral',
      newPassword: '',
      confirmPassword: '',
      newUserInfo: '',
      newTax: ''
    }
  },
  mounted() {
    AdminService.getAdminById(LogInService.userId).then(res => {this.user = res.data}).catch(() => {
      alert("THERE IS SOME PROBLEM WITH LOADING ADMIN");
    })
  },
  methods:{
    changeModeToNeutral(){
      this.mode = 'neutral';
    },
    showPasswordChange(){
      this.mode = 'password';
    },
    showInfoChange(){
      this.mode = 'info';
      this.newUserInfo = JSON.parse(JSON.stringify(this.user));
    },
    showTaxChange(){
      this.mode = 'tax';
    },
    showBoats(){
      this.mode = 'boats';
    },
    showCottages(){
      this.mode = 'cottage';
    },
    showInstructors(){
      this.mode = 'instructors';
    },
    showBoatOwners(){
      this.mode = 'boatOwners';
    },
    showCottageOwners(){
      this.mode = 'cottageOwners';
    },
    showCustomers(){
      this.mode = 'customers';
    },
    changePassword(){
      if( this.newPassword === this.confirmPassword){
        //CHANGE
      }
    },
    changeUserInfo(){

    },
    changeTax(){

    }
  }
}
</script>

<style scoped>

</style>