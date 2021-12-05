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
          tag="boat"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{boat.name}}
        </b-card-title>
        <b-card-text>
          {{ boat.description }}
        </b-card-text>
        <br>
        <b-card-text>
          ADDRESS:  {{ boat.street }} , {{ boat.city }}, {{boat.country}}
        </b-card-text>
        <br>
        <b-card-text>
          Average mark: {{boat.mark}}
        </b-card-text>
        <router-link class="btn btn-secondary" :to="'boat/'+boat.id.toString()" style="margin: 5px">See more</router-link>

      </b-card>
    </div>

  </div>
</template>

<script>
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
    //DUMMY
    this.boats = [
      {id: 1, name : "Neko ime broda 1", city : "Beograd" , street : "Ustanicka", country : "Serbia" , description: "Very nice", mark: 3.5},
      {id: 2, name : "Neko ime broda 2", city : "Novi Sad" , street : "Dositejeva", country : "Serbia", description: "Very nice", mark: 1.5},
      {id: 3, name : "Neko ime broda 3", city : "Karavukovo" , street : "Karadjodja", country : "Serbia", description: "Very nice", mark: 4.9},
    ];
    this.filtered = [
      {id: 1, name : "Neko ime 1", city : "Beograd" , street : "Ustanicka", country : "Serbia" , description: "Very nice", mark: 3.5},
      {id: 2, name : "Neko ime 2", city : "Novi Sad" , street : "Dositejeva", country : "Serbia", description: "Very nice", mark: 1.5},
      {id: 3, name : "Neko ime 2", city : "Karavukovo" , street : "Karadjodja", country : "Serbia", description: "Very nice", mark: 4.9},
    ];
    this.filtered.sort((a,b) => (a.city > b.city) ? 1 : ((b.city > a.city) ? -1 : 0))
  },
  methods:{
    search(){
      this.filtered = [];
      for (let boat of this.boats){
        if (boat.name.includes(this.filter.name) && (boat.city.includes(this.filter.location) ||
                boat.country.includes(this.filter.location) || boat.street.includes(this.filter.location)) &&
            boat.mark >= this.filter.mark){
          this.filtered.push(boat)
        }
      }
      if (this.sort === 'nameDESC')
        this.filtered.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))
      else if (this.sort === 'nameASC')
        this.filtered.sort((a,b) => (a.name < b.name) ? 1 : ((b.name < a.name) ? -1 : 0))
      else if (this.sort === 'markDESC')
        this.filtered.sort((a,b) => (a.mark > b.mark) ? 1 : ((b.mark > a.mark) ? -1 : 0))
      else if (this.sort === 'markASC')
        this.filtered.sort((a,b) => (a.mark < b.mark) ? 1 : ((b.mark < a.mark) ? -1 : 0))
      else if (this.sort === 'cityDESC')
        this.filtered.sort((a,b) => (a.city > b.city) ? 1 : ((b.city > a.city) ? -1 : 0))
      else if (this.sort === 'cityASC')
        this.filtered.sort((a,b) => (a.city < b.city) ? 1 : ((b.city < a.city) ? -1 : 0))
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
