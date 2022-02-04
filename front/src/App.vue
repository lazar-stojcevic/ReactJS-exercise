<template>
  <div id="app">
    <my-navbar></my-navbar>
    <!--RUTER-->
    <router-view/>
  </div>
</template>

<script>

export default {
  data() {
    return {
    }
  },
  mounted() {
    localStorage.setItem("userId", '')
    localStorage.setItem("userRole", '')
    localStorage.setItem("accessToken", '')
    localStorage.setItem("expiresIn", '')
  },

}
</script>

<style>
.loginImage{
  background-image: url("assets/bridge1.jpg");
  height: 2160px;
  background-position: top;
  background-repeat: repeat-x;
  background-size: cover;
}
</style>
