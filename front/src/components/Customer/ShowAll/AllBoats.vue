<template>
  <div class="container">
    <div class="search">
      <b-input-group prepend="Boat name" class="mb-2 mt-2">
        <b-form-input aria-label="Boat name" v-model="filter.name"></b-form-input>
      </b-input-group>
      <b-input-group prepend="Location" class="mb-2 mt-2 m-lg-2">
        <b-form-input aria-label="Boat location" v-model="filter.location"></b-form-input>
      </b-input-group>
      <b-input-group prepend="Minimum mark" class="mt-2">
        <b-form-input type="range" min="0" max="5" v-model="filter.mark"></b-form-input>
      </b-input-group>
      <b-form-select v-model="sort" class="m-3">
        <b-form-select-option value="nameASC">name descending</b-form-select-option>
        <b-form-select-option value="nameDESC">name ascending</b-form-select-option>
        <b-form-select-option value="markASC">mark descending</b-form-select-option>
        <b-form-select-option value="markDESC">mark ascending</b-form-select-option>
        <b-form-select-option value="cityASC">city descending</b-form-select-option>
        <b-form-select-option value="cityDESC">city ascending</b-form-select-option>
      </b-form-select>
      <b-input-group class="justify-content-center">
        <b-button variant="info" @click="search">Search</b-button>
      </b-input-group>
    </div>
    <br>
    <p>Boats</p>
    <br>
    <div v-for="boat in filtered" :key="boat.id">
      <b-card
          name="boat"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{boat.name}}
        </b-card-title>
        <b-card-text>
          Type: {{ boat.type }}
        </b-card-text>
        <b-card-text>
          {{ boat.promo }}
        </b-card-text>
        <b-card-text>
          Capacity: {{ boat.capacity }}
        </b-card-text>
        <br>
        <b-card-text>
          ADDRESS: {{boat.address.country}}, {{ boat.address.city }}, {{ boat.address.street }}
        </b-card-text>
        <br>
        <b-card-text>
          Average mark: {{boat.rating}}
        </b-card-text>
        <GmapMap
            :center = '{ lat: boat.address.latitude, lng: boat.address.longitude }'
            :zoom='12'
            style='width:100%;  height: 400px;'
        >
          <GmapMarker
              :position='{ lat: boat.address.latitude, lng: boat.address.longitude }'
          />
        </GmapMap>
        <router-link class="btn btn-secondary" :to="'boat/'+boat.id.toString()" style="margin: 5px">See more</router-link>

      </b-card>
    </div>

  </div>
</template>

<script>
import BoatService from "@/Services/BoatService";
export default {
  name: "AllBoats",
  data() {
    return {
      boats : [],
      filtered : [],
      filter: {
        name:'',
        location:'',
        mark : 0
      },
      sort : 'nameASC'
    }
  },
  mounted() {
    BoatService.getAllBoats().then((res) => {
      this.boats = res.data;
      this.filtered = res.data;
    })
    this.filtered.sort((a,b) => (a.address.city > b.address.city) ? 1 : ((b.address.city > a.address.city) ? -1 : 0))
  },
  methods:{
    search(){
      this.filtered = [];
      for (let boat of this.boats){
        if (boat.name.includes(this.filter.name) && (boat.address.city.includes(this.filter.location) ||
                boat.address.city.includes(this.filter.location) || boat.address.street.includes(this.filter.location)) &&
            boat.rating >= this.filter.mark){
          this.filtered.push(boat)
        }
      }
      if (this.sort === 'nameASC')
        this.filtered.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))
      else if (this.sort === 'nameDESC')
        this.filtered.sort((a,b) => (a.name < b.name) ? 1 : ((b.name < a.name) ? -1 : 0))
      else if (this.sort === 'markDESC')
        this.filtered.sort((a,b) => (a.rating > b.rating) ? 1 : ((b.rating > a.rating) ? -1 : 0))
      else if (this.sort === 'markASC')
        this.filtered.sort((a,b) => (a.rating < b.rating) ? 1 : ((b.rating < a.rating) ? -1 : 0))
      else if (this.sort === 'cityDESC')
        this.filtered.sort((a,b) => (a.address.city > b.address.city) ? 1 : ((b.address.city > a.address.city) ? -1 : 0))
      else if (this.sort === 'cityASC')
        this.filtered.sort((a,b) => (a.address.city < b.address.city) ? 1 : ((b.address.city < a.address.city) ? -1 : 0))
      //this.filtered.concat(pom);
    }
  }
}
</script>

<style scoped>
.search{
  display: flex;
}

</style>
