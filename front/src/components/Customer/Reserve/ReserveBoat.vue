<template>
  <div>
    <b-form inline style="display: flex" @submit.prevent="searchAvailableBoats">
      <span class="input-group mb-lg-2">
        <span class="input-group-text">DATE FROM</span>
        <input type="datetime-local" class="form-control" v-model="inputData.firstDay" required/>
      </span>
      <span class="input-group mb-lg-2">
        <span class="input-group-text">DATE TO</span>
        <input type="datetime-local" class="form-control" v-model="inputData.lastDay" required/>
      </span>
      <span class="input-group mb-lg-2">
        <span class="input-group-text">PERSONS</span>
        <input type="number" class="form-control" v-model="inputData.persons" required/>
      </span>
      <button type="submit" class="btn-info">Search</button>
    </b-form>
    <br>
    <div style="margin-right: 20px; margin-left: 20px" v-for="boat in boats" :key="boat.id">
      <b-card bg-variant="dark" text-variant="white">
        <b-card-title>
          {{boat.name}}
        </b-card-title>
        <b-card-sub-title style="margin: 5px">
          {{boat.promo}}
        </b-card-sub-title>
        <b-card-sub-title style="margin: 5px">
          Conduct rules: {{boat.conductRules}}
        </b-card-sub-title>
        <b-card-sub-title style="margin: 5px">
          Max capacity: {{boat.capacity}}
        </b-card-sub-title>
        <b-card-text style="margin: 5px">
          Address: {{boat.address.country}}, {{boat.address.city}}, {{boat.address.street}}
        </b-card-text>
        <b-card-text style="margin: 5px">
          Basic price: {{boat.priceList.price}}
        </b-card-text>
        <b-card-text style="margin: 5px">
          Rating: {{boat.rating}}
        </b-card-text>
        <br>
        <b-form-group label="Additional services:" v-slot="{ ariaDescribedby }">
          <b-form-checkbox-group
              id="checkbox-group-2"
              v-model="boat.selected"
              :aria-describedby="ariaDescribedby"
              name="services"
          >
            <b-form-checkbox v-for="service in boat.services" :key="service.id" :value="service.id">{{ service.name }} - {{service.addPrice}}</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group>
        <b-button @click="reserve(boat)" variant="primary">Reserve</b-button>
      </b-card>
      <br>
    </div>
  </div>
</template>

<script>
import BoatReservationService from "@/Services/BoatReservationService";
import BoatService from "@/Services/BoatService";
export default {
  name: "ReserveBoat",
  data() {
    return {
      inputData: {
        firstDay: '',
        lastDay: '',
        persons: 1
      },
      boats: [],
      sort : 'markDESC'

    }
  },
  methods:{
    searchAvailableBoats(){
      BoatReservationService.getAllAvailableBoatTerms(this.inputData.firstDay, this.inputData.lastDay, this.inputData.persons)
          .then((res) => {
            this.boats = res.data;
          }).then(() => {
            for (let boat of this.boats) {
              BoatService.getAdditionalServicesOfBoat(boat.id).then((res) => {
                boat.services = res.data;
              });
              boat.selected = [];
            }
      })
    },
    sortReservations(){
      if (this.sort === 'markDESC')
        this.boats.sort((a,b) => (a.rating > b.rating) ? 1 : ((b.rating > a.rating) ? -1 : 0))
      else if (this.sort === 'markASC')
        this.boats.sort((a,b) => (a.rating < b.rating) ? 1 : ((b.rating < a.rating) ? -1 : 0))
      if (this.sort === 'priceDESC')
        this.boats.sort((a,b) => (a.priceList.price < b.priceList.price) ? 1 : ((b.priceList.price < a.priceList.price) ? -1 : 0))
      else if (this.sort === 'priceASC')
        this.boats.sort((a,b) => (a.priceList.price > b.priceList.price) ? 1 : ((b.priceList.price > a.priceList.price) ? -1 : 0))
    },
    reserve(boatToReserve){
      alert("Please wait for a while...");
      BoatReservationService.reserveBoat(boatToReserve.id, boatToReserve.selected, this.inputData.firstDay, this.inputData.lastDay).then(() => {
        alert("Reservation creted. Details will be sent to your email.")
        this.$router.push('/');
      }).catch(() => {alert("Some of your term overlaps!")})
    }
  }
}
</script>

<style scoped>

</style>
