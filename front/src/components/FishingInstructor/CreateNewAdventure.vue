<template>
<div class="container">
  <form style="padding: 10px" @submit.prevent="fieldsFill">
    <div class="input-group mb-lg-4">
      <span class="input-group-text">ADVENTURE NAME</span>
      <input type="text" class="form-control" v-model="name" required>
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
        <span class="input-group-text">DESCRIPTION</span>
        <input type="text" class="form-control" v-model="description" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">INSTRUCTOR BIOGRAPHY</span>
        <input type="text" class="form-control" v-model="instructorBiography" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">EQUIPMENTS(ADDITIONAL EQUIPMENTS IF CUSTOMER DOESNT HAVE ANY)</span>
        <input type="text" class="form-control" v-model="equipment" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">CANCELING TERMS</span>
        <input type="text" class="form-control" v-model="cancelingTerms" required>
      </div>
      <div class="input-group mb-lg-4">
        <span class="input-group-text">CONDUCT RULES</span>
        <input type="text" class="form-control" v-model="conductRules" required>
      </div>
      <div class="input-group mb-1">
        <span class="input-group-text">MAXIMUM PERSONS</span>
        <input type="number" class="form-control" v-model="maxPersons" required>
      </div>
    <!--LOGIKA ZA POPUNJAVANJE CENOVNIKA-->
    <div class="input-group mb-lg-4">
      <div class="btn-group-sm">
        <button type="submit" class="btn-info">CONFIRM</button>
      </div>
    </div>
  </form>
</div>

</template>

<script>
import AdventureService from "@/Services/AdventureService";
import LogInService from "@/Services/LogInService";

export default {
  data(){
    return{
      newAdventure: '',
      name: '',
      street: '',
      city: '',
      country:'',
      maxPersons:'',
      description: '',
      instructorBiography:'',
      equipment:'',
      cancelingTerms:'',
      conductRules: ''
    }
  },
  methods:{
    createAdventure(){
        AdventureService.saveAdventure(this.newAdventure, LogInService.userId).then(() => {
          alert('ADVENTURE CREATED')
          this.$router.push('/myAdventures');
        }).catch( () => {
          alert('THERE SOME ERROR WITHS SERVER');
        });
    },
    fieldsFill(){
      this.newAdventure = {
       name: this.name,
        address: {
          street: this.street,
          city: this.city,
          country: this.country
        },
        maxPersons: this.maxPersons,
        description: this.description,
        instructorBiography: this.instructorBiography,
        equipment: this.equipment,
        cancelingTerms: this.cancelingTerms,
        conductRules: this.conductRules
      }
      this.createAdventure();
    }
  }
}
</script>

<style scoped>

</style>