<template>
  <div class="loginImage">
    <main class="form-signin">
      <div class="card">
        <div class="card-body">
          <form @submit.prevent="login">
            <h1 class="h3 mb-3 fw-normal">Please log in</h1>
            <div class="form-floating">
              <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" required
                     v-model="user.email"/>
              <label for="floatingInput">Email address</label>
            </div>
            <div class="form-floating">
              <input type="password" class="form-control" id="floatingPassword" placeholder="Password" required
                     v-model="user.password"/>
              <label for="floatingPassword">Password</label>
            </div>
            <button class="w-100 btn btn-lg btn-outline-success" type="submit">Log in</button>
            <p class="mt-5 mb-3 text-muted">© 2021–2022</p>
          </form>
        </div>
      </div>
    </main>
  </div>
  <!--<b-container fluid="sm">
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
    </b-container>-->
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
        if (LogInService.userRole === '')
          this.$router.push("/registration");
        else
          this.$router.push("/");
      }).catch(() => {alert("Wrong email or password")});
    }
  }
}
</script>

<style lang="css">
.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
  text-align: center;
  background-color: transparent;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
  background-color: transparent;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
  background-color: transparent;
}

.card{
  background-color: transparent;
}
</style>
