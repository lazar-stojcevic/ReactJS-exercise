<template>
  <div>
    <b-form inline style="display: flex" @submit.prevent="searchAvailableCottages">
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
    <div style="display: flex">
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
    <br>
    <div style="margin-right: 20px; margin-left: 20px" v-for="cottage in cottages" :key="cottage.id">
      <b-card bg-variant="dark" text-variant="white">
        <b-card-title>
          {{cottage.name}}
        </b-card-title>
        <b-card-sub-title style="margin: 5px">
          {{cottage.promo}}
        </b-card-sub-title>
        <b-card-sub-title style="margin: 5px">
          Conduct rules: {{cottage.conductRules}}
        </b-card-sub-title>
        <b-card-text style="margin: 5px">
          Address: {{cottage.address.country}}, {{cottage.address.city}}, {{cottage.address.street}}
        </b-card-text>
        <b-card-text style="margin: 5px">
          Basic price: {{cottage.cottagePriceList.price}}
        </b-card-text>
        <b-card-text style="margin: 5px">
          Rating: {{cottage.rating}}
        </b-card-text>
        <br>
        <b-form-group label="Additional services:" v-slot="{ ariaDescribedby }">
          <b-form-checkbox-group
              id="checkbox-group-2"
              v-model="cottage.selected"
              :aria-describedby="ariaDescribedby"
              name="services"
          >
            <b-form-checkbox v-for="service in cottage.services" :key="service.id" :value="service.id">{{ service.name }} - {{service.addPrice}}</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group>
        <b-button @click="reserve(cottage)" variant="primary">Reserve</b-button>
      </b-card>
      <br>
    </div>

  </div>
</template>

<script>
import CottageReservationService from "../../Services/CottageReservationService";
import CottageService from "../../Services/CottageService";

export default {
  name: "CreateReservation",
  data() {
    return {
      id:'',
      inputData: {
        firstDay: '',
        lastDay: '',
        persons: 1
      },
      cottages: [],
      sort : 'markDESC'

    }
  },
  mounted() {
    this.id = this.$route.params.id;
  },
  methods:{
    searchAvailableCottages(){
      CottageReservationService.getAllAvailableCottagesTerms(this.inputData.firstDay, this.inputData.lastDay, this.inputData.persons)
          .then((res) => {
            this.cottages = res.data;
          }).then(() => {
        for (let cottage of this.cottages){
          CottageService.getAdditionalServicesOfCottage(cottage.id).then((res) => {
            cottage.services = res.data;
            console.log(res.data)
          });
          cottage.selected = [];
        }
      });
    },
    sortReservations(){
      if (this.sort === 'markDESC')
        this.cottages.sort((a,b) => (a.rating > b.rating) ? 1 : ((b.rating > a.rating) ? -1 : 0))
      else if (this.sort === 'markASC')
        this.cottages.sort((a,b) => (a.rating < b.rating) ? 1 : ((b.rating < a.rating) ? -1 : 0))
      if (this.sort === 'priceDESC')
        this.cottages.sort((a,b) => (a.cottagePriceList.price < b.cottagePriceList.price) ? 1 : ((b.cottagePriceList.price < a.cottagePriceList.price) ? -1 : 0))
      else if (this.sort === 'priceASC')
        this.cottages.sort((a,b) => (a.cottagePriceList.price > b.cottagePriceList.price) ? 1 : ((b.cottagePriceList.price > a.cottagePriceList.price) ? -1 : 0))
    },
    reserve(cottageToReserve){
      alert("Please wait for a while...")
      CottageReservationService.reserveCottageForCustomer(cottageToReserve.id, cottageToReserve.selected, this.inputData.firstDay, this.inputData.lastDay, this.id).then(() =>{
        alert("Reservation creted. Details will be sent to your email.")
        this.$router.push('/');
      }).catch(() => {alert("Some of your term overlaps!")})
    }
  }

}

</script>

<style scoped>

</style>