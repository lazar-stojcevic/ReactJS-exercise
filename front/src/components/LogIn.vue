<template>
  <b-container fluid="sm">
    <b-row class="my-1">
      <b-col sm="2">
        <label>Email:</label>
      </b-col>
      <b-col sm="5">
        <b-form-input id="input-default" placeholder="Enter your name"  v-model="user.email"></b-form-input>
      </b-col>
    </b-row>
    <b-row class="my-1">
      <b-col sm="2">
        <label>Password:</label>
      </b-col>
      <b-col sm="5">
        <b-form-input id="input-default" placeholder="Enter your password"  v-model="user.password"></b-form-input>
      </b-col>
    </b-row>
    <b-button type="Log in" variant="primary" v-on:click="login">Submit</b-button>
    </b-container>
</template>

<script>
import LogInService from "../Services/LogInService";

export default {
  name: "LogIn",
  data: function () {
    return {
      user: {
        email:'',
        password:''
      }
    }
  },
  methods: {
    login : function() {
      LogInService.login(this.user).then(() => {
        if (LogInService.getToken() !== '')
          this.$router.push("/registration");
      });
    }
  }
}
</script>
