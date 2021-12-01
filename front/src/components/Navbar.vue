<template>
  <div></div>
</template>

<script>
import LogInService from "@/Services/LogInService";
export default {
  data() {
    return {
      userRole : 'neulogovan',
      isLogged: false
    }
  },
  methods: {
    mounted(){
      this.userRole = LogInService.userRole;
    },
    logout(){
      LogInService.logout();
    },
    setUserRole(userRole){
      this.userRole = userRole;
      if(this.userRole === '')
        this.isLogged = false;
      else
        this.isLogged = true;
      alert(this.userRole);
    }
  }
}
</script>

<style scoped>

</style>