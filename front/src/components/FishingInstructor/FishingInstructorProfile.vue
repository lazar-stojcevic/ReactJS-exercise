<template>
  <div class="container">
    <div class="grid">
      <div class="row" style="margin-top: 20px">
        <div class="col">
          <!--INFO-->
          <table class="table table-striped">
            <tbody>
            <tr>
              <td>FULL NAME</td>
              <td>{{user.firstname}} {{user.lastName}}</td>
            </tr>
            <tr>
              <td>FULL ADDRESS</td>
              <td v-if="user.address !== null">{{user.address.street}}, {{user.address.city}}, {{user.address.country}}</td>
              <td v-else>ADDRESS IS NOT SET</td>
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
          <!--BUTTONS-->
          <div class="btn-group-sm" style="margin: 10px">
            <button @click="changeModeToInfo" v-if="mode === 'neutral'" class="btn-info">CHANGE YOUR INFO</button>
            <button @click="changeModeToPassword" v-if="mode === 'neutral'" class="btn-info">CHANGE PASSWORD</button>
            <button @click="changeModeToHoliday" v-if="mode === 'neutral'" class="btn-info">ADD AVAILABLE TIMESPAN</button>
            <button @click="myAdventures" v-if="mode === 'neutral'" class="btn-info">MY ADVENTURES</button>
            <button @click="requestForDeleting" v-if="mode === 'neutral'" class="btn-danger">SEND REQUEST FOR DELETING</button>
          </div>
          <!--CALENDAR-->
          <div v-if="user.available !== null" style="text-align: center">
            <strong style="margin: 10px">YOUR AVAILABLE TIMESPAN</strong>
          <table class="table table-striped">
            <thead>
            <tr>
              <td>FROM DATE</td>
              <td>TO DATE</td>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>{{user.available.fromDate}}</td>
              <td>{{user.available.toDate}}</td>
            </tr>
            </tbody>
          </table>
          </div>
          <!--PASSWORD CHANGING-->
          <div v-if="mode === 'changePassword'" class="container">
            <form @submit.prevent="changePassword">
              <div class="input-group mb-3">
                <span class="input-group-text">NEW PASSWORD</span>
                <input type="password" class="form-control" v-model="newPassword">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">CONFIRM PASSWORD</span>
                <input type="password" class="form-control" v-model="confirmPassword">
              </div>
              <div class="input-group mb-3">
                <div class="btn-group-sm">
                  <button type="submit" class="btn-info">CONFIRM</button>
                  <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
                </div>
              </div>
            </form>
          </div>
          <!--INFO CHANGING-->
          <div v-if="mode === 'changeInfo'" class="container">
            <form @submit.prevent="changeUserInfo">
              <div class="input-group mb-3">
                <span class="input-group-text">NAME</span>
                <input type="text" class="form-control" v-model="newUserInfo.firstname">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">LASTNAME</span>
                <input type="text" class="form-control" v-model="newUserInfo.lastName">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">STREET</span>
                <input type="text" class="form-control" v-model="newUserInfo.address.street">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">CITY</span>
                <input type="text" class="form-control" v-model="newUserInfo.address.city">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">COUNTRY</span>
                <input type="text" class="form-control" v-model="newUserInfo.address.country">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">PHONE</span>
                <input type="text" class="form-control" v-model="newUserInfo.phone">
              </div>
              <div class="input-group mb-3">
                <div class="btn-group-sm">
                  <button type="submit" class="btn-info">CONFIRM</button>
                  <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
                </div>
              </div>
            </form>
          </div>
          <!--ADDING AVAILABLE TIMESPAN-->
          <div v-if="mode === 'addHoliday'" class="container">
            <form @submit.prevent="addAvailableTimespan">
              <div class="input-group mb-3">
                <span class="input-group-text">FROM DATE</span>
                <input type="date" class="form-control" v-model="fromDate">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">TO DATE</span>
                <input type="date" class="form-control" v-model="toDate">
              </div>
              <div class="input-group mb-3">
                <div class="btn-group-sm">
                  <button type="submit" class="btn-info">CONFIRM</button>
                  <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      <!--OVDE IDE KALENDAR ZAUZETOSTI-->
    </div>
  </div>
</template>

<script>
import FishingInstructorService from "@/Services/FishingInstructorService";
import LogInService from "@/Services/LogInService";

export default {
  data(){
    return{
      user: {
        address: {
          street: ''
        },
        available: {
          fromDate: ''
        }
      },
      mode: 'neutral', //changePassword, changeInfo
      newPassword: '',
      confirmPassword: '',
      newUserInfo: '',
      fromDate: ' ',
      toDate: ''
      }
    },
  mounted() {
    if(LogInService.userRole === '')
      this.$router.push('/login');
    FishingInstructorService.getFishingInstructorById(LogInService.userId).then(res => {
      this.user = res.data;
      this.newUserInfo = res.data;
    });
  },
  methods: {
    changeModeToHoliday(){
      this.mode = 'addHoliday';
    },
    changeModeToNeutral(){
      if(this.mode === 'changePassword'){
        this.newPassword = '';
        this.confirmPassword = '';
      }
      if(this.mode === 'addHoliday'){
        this.fromDate = '';
        this.toDate = '';
      }
      this.mode = 'neutral';
    },
    changeModeToInfo(){
      this.mode = 'changeInfo';
      this.newUserInfo = JSON.parse(JSON.stringify(this.user));
    },
    changeModeToPassword(){
      this.mode = 'changePassword'
    },
    changePassword(){
      if(this.newPassword !== this.confirmPassword && this.newPassword.trim() !== ''){
        alert("NEW PASSWORD IS NOT MATCHING WITH CONFIRM PASSWORD");
        return;
      }
      FishingInstructorService.changePassword(this.confirmPassword, this.user.id)
          .then(() => {
            LogInService.logout();
            this.$router.push('/login');
          })
          .catch(err => { alert("SERVER ERROR: " + err)});
    },
    //TODO: VALIDACIJA
    changeUserInfo(){
      FishingInstructorService.changeFishingInstructor(this.newUserInfo).then(res => {this.user = res.data}).catch(() => {
        alert("SERVER ERROR");
      });
      this.mode = 'neutral';
    },
    addAvailableTimespan(){
      if(this.toDate === '' && this.fromDate === ''){
        alert("BOTH OF FIELDS MUST BE FILLED")
        return;
      }
      if(this.fromDate > this.toDate){
        alert("FROM DATE MUST BE EARLIER FROM THE TO DATE")
        return;
      }
      FishingInstructorService.addHoliday(this.fromDate, this.toDate, this.user.id).then(res => {this.user = res.data})
          .catch(() => {
            alert("SERVER ERROR");
      });
      this.mode = 'neutral';
    },
    myAdventures(){
      this.$router.push('/myAdventures');
    },
    requestForDeleting(){
      FishingInstructorService.requestForDeleting(this.user.id).then(() => {LogInService.logout()}).catch(
          () => {alert("PROBABLY REQUEST IS ALREADY SENT")}
      )
    }
  }

}
</script>

<style scoped>

</style>