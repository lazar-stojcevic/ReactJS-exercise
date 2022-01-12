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
      <tr v-if="currentTax !== ''">
        <td>TAX</td>
        <td>{{currentTax.taxRate}}</td>
      </tr>
      </tbody>
    </table>
    <hr style="margin-top: 10px"/>
    <!--BUTTONS-->
    <div>
      <table style="margin: 10px">
        <tr>
          <td>
            <button class="btn-sm btn-primary" @click="showPasswordChange">CHANGE PASSWORD</button>
          </td>
          <td>
            <button class="btn-sm btn-primary" v-if="!user.firstTimeCreated" @click="showInfoChange">CHANGE INFO</button>
          </td>
          <td>
            <button class="btn-sm btn-primary" v-if="!user.firstTimeCreated" @click="showTaxChange">TAX</button>
          </td>
          <td>
            <button class="btn-sm small btn-primary" v-if="!user.firstTimeCreated" @click="showBoats">BOATS</button>
          </td>
          <td>
            <button class="btn-sm small btn-primary" v-if="!user.firstTimeCreated" @click="showCottages">COTTAGES</button>
          </td>
          <td>
            <button class="btn-sm small btn-primary" v-if="!user.firstTimeCreated" @click="showCustomers">CUSTOMERS</button>
          </td>
          <td>
            <button class="btn-sm small btn-primary" v-if="!user.firstTimeCreated" @click="showIncome">SEE INCOME</button>
          </td>
        </tr>
      </table>
    </div>
    <div v-if="mode === 'systemIncome'">
      <table class="table table-primary" style="margin-top: 20px">
        <thead>
        <th>ADVENTURE RESERVATIONS</th>
        <th>COTTAGE RESERVATIONS</th>
        <th>BOAT RESERVATIONS</th>
        <th>INCOME SUM</th>
        </thead>
        <tbody>
        <tr>
          <td>{{income.numberOfAdventureReservations}}</td>
          <td>{{income.numberOfCottageReservations}}</td>
          <td>{{income.numberOfBoatReservations}}</td>
          <td>{{income.income}}</td>
          <td><button class="btn btn-warning" @click="changeModeToNeutral">CLOSE</button></td>
        </tr>
        </tbody>
      </table>
    </div>
    <!--INCOME REQUEST-->
    <div v-if="mode === 'income'">
      <form @submit.prevent="calculateIncome">
        <div class="input-group mb-lg-2">
          <span class="input-group-text">FROM DATE</span>
          <input type="datetime-local" class="form-control" v-model="incomeRequest.startTime" required/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">TO DATE</span>
          <input type="datetime-local" class="form-control" v-model="incomeRequest.endTime" required/>
        </div>
        <div class="input-group mb-3">
          <div class="btn-group-sm">
            <button type="submit" class="btn-info">CONFIRM</button>
            <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
          </div>
        </div>
      </form>
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
          <span class="input-group-text">NEW TAX (%)</span>
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
    <div v-if="mode === 'cottage'">
      <cottages></cottages>
      <button @click="changeModeToNeutral" class="btn btn-warning">CLOSE</button>
    </div>
    <div v-if="mode === 'customers'">
      <customers></customers>
      <button @click="changeModeToNeutral" class="btn btn-warning">CLOSE</button>
    </div>
  </div>
</template>

<script>
import AdminService from "@/Services/AdminService";
import LogInService from "@/Services/LogInService";

import AllCottages from "@/components/Admin/InnerAdminComponents/AllCottages";
import AllCustomers from "@/components/Admin/InnerAdminComponents/AllCustomers";
import TaxService from "@/Services/TaxService";
export default {
  components:{
    'cottages': AllCottages,
    'customers': AllCustomers
  },
  data(){
    return{
      user: {
        address:{
          street: ''
        },
        firstTimeCreated:''
      },
      mode:'neutral',
      newPassword: '',
      confirmPassword: '',
      newUserInfo: '',
      newTax: '',
      currentTax: '',
      incomeRequest: {startTime: '', endTime: ''},
      income: ''
    }
  },
  mounted() {
    if(LogInService.userId === '.' || LogInService.userId === ''){
      this.$router.push('/')
      return;
    }
    AdminService.getAdminById(LogInService.userId).then(res => {this.user = res.data}).catch(() => {
      alert("THERE IS SOME PROBLEM WITH LOADING ADMIN");
    });

    this.loadCurrentTax();
  },
  methods:{
    calculateIncome(){
      if(this.incomeRequest.startTime > this.incomeRequest.endTime){
        alert("START DATE MUST BE IN PAST OF END DATE");
        return;
      }
      this.incomeRequest.startTime = this.incomeRequest.startTime.replaceAll('T', ' ');
      this.incomeRequest.endTime = this.incomeRequest.endTime.replaceAll('T', ' ');

      TaxService.getIncome(this.incomeRequest).then(res => {
        this.income = res.data;
        this.mode = 'systemIncome';
      }).catch(() => {alert("THERE IS SOME ERROR WITH CALCULATING INCOME")});
    },

    showIncome(){
      this.mode = 'income';
      this.incomeRequest = {startTime: '', endTime: ''}
    },

    loadCurrentTax(){
      TaxService.getCurrentTax().then(res => {this.currentTax = res.data;}).catch(() => {
        alert("THERE IS SOME ERROR WITH LOADING CURRENT TAX");
        this.currentTax = '';
      });
    },

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
      this.newTax = '';
    },

    showBoats(){
      this.mode = 'boats';
    },

    showCottages(){
      this.mode = 'cottage';
    },

    showBoatOwners(){
      this.mode = 'boatOwners';
    },

    showCustomers(){
      this.mode = 'customers';
    },

    changePassword(){
      if( this.newPassword === this.confirmPassword){
        AdminService.changePassword(this.newPassword).then(() => {LogInService.logout(); this.$router.push('/')})
            .catch(() => {
          alert("THERE IS SOME PROBLEM WITH SERVER")
        });
      }else {
        alert("NEW PASSWORD AND CONFIRM PASSWORD MUST BE SAME")
      }
    },

    changeUserInfo(){
      AdminService.changeAdmin(this.newUserInfo).then(res => {this.user = res.data}).catch(() => {
        alert('THERE IS SOME PROBLEM WITH SERVER')
      });
      this.mode = "neutral";
    },

    changeTax(){
      if(this.newTax < 0 || this.newTax > 100){
        alert("TAX IS NOT IN RANGE LIMIT (0 - 100)");
        return;
      }
      TaxService.changeTax(this.newTax).then(() => this.loadCurrentTax()).catch(() => {
        alert("THERE IS SOME ERROR WITH CHANGING TAX");
      });
      this.mode = 'neutral';
    }
  }
}
</script>

<style scoped>

</style>