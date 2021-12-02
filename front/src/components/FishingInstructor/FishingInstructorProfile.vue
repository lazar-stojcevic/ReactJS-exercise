<template>
  <div class="container">
    <div class="grid">
      <!--PRVI RED-->
      <div class="row" style="margin-top: 20px">
        <div class="col">
          <table class="table table-striped">
            <tbody>
            <tr>
              <td>FULL NAME</td>
              <td></td>
            </tr>
            <tr>
              <td>FULL ADDRESS</td>
              <td></td>
            </tr>
            <tr>
              <td>EMAIL</td>
              <td></td>
            </tr>
            <tr>
              <td>PHONE</td>
              <td></td>
            </tr>
            </tbody>
          </table>
          <div class="btn-group-sm">
            <button class="btn-info">CHANGE YOUR INFO</button>
            <button class="btn-info">CHANGE PASSWORD</button>
          </div>
        </div>
        <div class="col">ODMOR</div>
      </div>
    </div>
  </div>
</template>

<script>
//import LogInService from "@/Services/LogInService";
//import axios from "axios";

import FishingInstructorService from "@/Services/FishingInstructorService";
import LogInService from "@/Services/LogInService";

export default {
  data(){
    return{
      user: '',
      //changePassword, changeInfo
      mode: 'neutral'
    }
  },
  mounted() {
    FishingInstructorService.getFishingInstructorById(LogInService.userId).then(res => {
      this.user = res.data;
    });
  },
  methods: {

  }
}
</script>

<style scoped>

</style>