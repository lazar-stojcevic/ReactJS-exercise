<template>
<div>
  <b-form inline style="display: flex" @submit.prevent="searchAvailableAdventures">
    <span class="input-group mb-lg-2">
      <span class="input-group-text">DATE FROM*</span>
      <input type="datetime-local" class="form-control" v-model="inputData.firstDay" :max="inputData.lastDay" required/>
    </span>
    <span class="input-group mb-lg-2">
      <span class="input-group-text">DATE TO*</span>
      <input type="datetime-local" class="form-control" v-model="inputData.lastDay"  :min="inputData.firstDay" required/>
    </span>
    <span class="input-group mb-lg-2">
      <span class="input-group-text">PERSONS*</span>
      <input type="number" class="form-control" v-model="inputData.persons" required/>
    </span>
    <span class="input-group mb-lg-2">
      <span class="input-group-text">COUNTRY</span>
      <input type="text" class="form-control" v-model="inputData.country"/>
    </span>
    <span class="input-group mb-lg-2">
      <span class="input-group-text">CITY</span>
      <input type="text" class="form-control" v-model="inputData.city"/>
    </span>
    <button type="submit" class="btn-info">Search</button>
  </b-form>
  <br>
  <div style="display: flex; align-items: center; margin-left: 1rem">
    Sort:
    <b-form-select v-model="sort" class="m-3">
      <b-form-select-option value="markASC">mark ascending</b-form-select-option>
      <b-form-select-option value="markDESC">mark descending</b-form-select-option>
      <b-form-select-option value="priceASC">price ascending</b-form-select-option>
      <b-form-select-option value="priceDESC">price descending</b-form-select-option>
    </b-form-select>
    <b-input-group>
      <b-button variant="info" @click="sortReservations">Sort</b-button>
    </b-input-group>
  </div>

  <div style="margin-right: 20px; margin-left: 20px" v-for="adventure in adventures" :key="adventure.id">
    <b-card bg-variant="dark" text-variant="white">
      <b-card-title>
        {{adventure.adventure.name}}
      </b-card-title>
      <b-card-sub-title style="margin: 5px">
        Time of start: {{adventure.reservationStart}}
      </b-card-sub-title>
      <b-card-sub-title style="margin: 5px">
        Maximum persons: {{adventure.adventure.maxPersons}}
      </b-card-sub-title>
      <b-card-text style="margin: 5px">
        Description: {{adventure.adventure.description}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Equipment: {{adventure.adventure.equipment}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        RULES: {{adventure.adventure.conductRules}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Address: {{adventure.adventure.address.country}}, {{adventure.adventure.address.city}}, {{adventure.adventure.address.street}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Base price: {{adventure.price}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Instructor mark: {{adventure.mark}}
      </b-card-text>
      <b-button :to="'newAdventureReservation/'+adventure.id.toString()" variant="primary">Reserve</b-button>
    </b-card>
    <br>
  </div>

</div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
import GradeService from "@/Services/GradeService";
export default {
  name: "ReserveAvanture",
  data() {
    return {
      AdventureReservationService,
      inputData: {
        firstDay: '',
        lastDay: '',
        city: '',
        country: '',
        persons: 1
      },
      adventures: [],
      sort : 'markDESC',
    }
  },
  methods:{
    searchAvailableAdventures(){
      AdventureReservationService.getAllAvailableAdventureTerms(this.inputData.firstDay, this.inputData.lastDay, this.inputData.persons, this.inputData.city, this.inputData.country)
          .then((res) => {
            this.adventures = res.data;
            console.log(res.data)
          }).then(() => {
        for(let ad in this.adventures){
          GradeService.getAllGradeOfInstructor(this.adventures[ad].adventure.instructor.id).then(res =>{
            this.adventures[ad].mark = 0
            this.adventures[ad].mark = res.data.avgRating;
            if (isNaN(this.adventures[ad].mark))
              this.adventures[ad].mark = 0
          });
        }
      });
    },
    sortReservations(){
    if (this.sort === 'markDESC')
        this.adventures.sort((a,b) => (a.rating > b.rating) ? 1 : ((b.rating > a.rating) ? -1 : 0))
    else if (this.sort === 'markASC')
        this.adventures.sort((a,b) => (a.rating < b.rating) ? 1 : ((b.rating < a.rating) ? -1 : 0))
      if (this.sort === 'priceDESC')
        this.adventures.sort((a,b) => (a.price < b.price) ? 1 : ((b.price < a.price) ? -1 : 0))
      else if (this.sort === 'priceASC')
        this.adventures.sort((a,b) => (a.price > b.price) ? 1 : ((b.price > a.price) ? -1 : 0))
    }
  }
}
</script>

<style scoped>

</style>
