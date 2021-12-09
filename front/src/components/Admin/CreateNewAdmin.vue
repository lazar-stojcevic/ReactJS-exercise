<template>
  <div class="container">
    <form style="padding: 10px" @submit.prevent="fieldsFill">
      <div class="input-group mb-lg-4">
        <span class="input-group-text">FIRSTNAME</span>
        <input type="text" class="form-control" v-model="firstname" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">LASTNAME</span>
        <input type="text" class="form-control" v-model="lastName" required>
      </div>
      <div class="row" style="margin-bottom: 20px">
        <div class="col">
          <span class="input-group-text">STREET</span>
          <input type="text" class="form-control" v-model="street" required>
        </div>
        <div class="col">
          <span class="input-group-text">CITY</span>
          <input type="text" class="form-control" v-model="city" required>
        </div>
        <div class="col">
          <span class="input-group-text">COUNTRY</span>
          <input type="text" class="form-control" v-model="country" required>
        </div>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">EMAIL</span>
        <input type="text" class="form-control" v-model="email" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">PHONE</span>
        <input type="text" class="form-control" v-model="phone" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">PASSWORD</span>
        <input type="password" class="form-control" v-model="password" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">CONFIRM PASSWORD</span>
        <input type="password" class="form-control" v-model="confirmPassword" required>
      </div>
      <div class="input-group mb-lg-4">
        <div class="btn-group-sm">
          <button type="submit" class="btn-info">CONFIRM</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import AdminService from "@/Services/AdminService";
export default {
  data(){
    return{
      firstname:'',
      lastName: '',
      password: '',
      confirmPassword: '',
      email: '',
      phone: '',
      street: '',
      city: '',
      country: ''
    }
  },
  methods:{
    fieldsFill(){
      if(this.password !== this.confirmPassword){
        alert("PASSWORD AND CONFIRM PASSWORD IS NOT EQUALS")
        return;
      }
      let admin = {
        firstname: this.firstname, lastname: this.lastName, password: this.password, email: this.email, phone: this.phone,
        street: this.street, city: this.city, country: this.country}
      AdminService.saveAdmin(admin).then(() => {alert('SUCCESSFUL')}).catch(() => {
        alert('THERE IS SOME PROBLEM WITH SAVING ADMIN')});
      this.$router.push('/adminProfile')
    }
  }
}
</script>

<style scoped>

</style>