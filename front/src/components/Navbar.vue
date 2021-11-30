<template>
  <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd; padding: 5px">
    <div class="container-fluid">
      <router-link class="btn btn-secondary" to="/">Home</router-link>
    </div>
    <router-link class="btn btn-secondary" to="/login" style="margin: 5px">LogIn</router-link>
    <router-link class="btn btn-secondary" to="/registration" style="margin: 5px">Registration</router-link>
  </nav>
</template>

<script>

export default {
  name: "Navbar",
  data: function () {
    return {

    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>