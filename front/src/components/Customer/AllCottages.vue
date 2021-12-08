<template>
<div class="container">
  <div class="search">
    <b-input-group prepend="Cottage name" class="mb-2 mt-2">
      <b-form-input aria-label="Cottage name" v-model="filter.name"></b-form-input>
    </b-input-group>
    <b-input-group prepend="Location" class="mb-2 mt-2 m-lg-2">
      <b-form-input aria-label="Cottage location" v-model="filter.location"></b-form-input>
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
  <p>Cottages</p>
  <br>
  <div v-for="cottage in filtered" :key="cottage.id">
    <b-card
        tag="cottage"
        style="max-width: 20rem;"
        class="mb-2"
    >
      <b-card-title>
        {{cottage.name}}
      </b-card-title>
      <b-card-text>
        {{ cottage.description }}
      </b-card-text>
      <br>
      <b-card-text>
        ADDRESS:  {{ cottage.street }} , {{ cottage.city }}, {{cottage.country}}
      </b-card-text>
      <br>
      <b-card-text>
        Average mark: {{cottage.mark}}
      </b-card-text>
      <router-link class="btn btn-secondary" :to="'cottage/'+cottage.id.toString()" style="margin: 5px">See cottage</router-link>

    </b-card>
  </div>

</div>
</template>

<script>
export default {
  name: "AllCottages",
  data() {
    return {
      cottages : [],
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
    this.cottages = [
      {id: 1, name : "Neko ime 1", city : "Beograd" , street : "Ustanicka", country : "Serbia" , description: "Very nice", mark: 3.5},
      {id: 2, name : "Neko ime 2", city : "Novi Sad" , street : "Ustanicka", country : "Serbia", description: "Very nice", mark: 1.5},
      {id: 3, name : "Neko ime 2", city : "Karavukovo" , street : "Ustanicka", country : "Serbia", description: "Very nice", mark: 4.9},
    ];
    this.filtered = [
      {id: 1, name : "Neko ime 1", city : "Beograd" , street : "Ustanicka", country : "Serbia" , description: "Very nice", mark: 3.5},
      {id: 2, name : "Neko ime 2", city : "Novi Sad" , street : "Ustanicka", country : "Serbia", description: "Very nice", mark: 1.5},
      {id: 3, name : "Neko ime 2", city : "Karavukovo" , street : "Ustanicka", country : "Serbia", description: "Very nice", mark: 4.9},
    ];
    this.filtered.sort((a,b) => (a.city > b.city) ? 1 : ((b.city > a.city) ? -1 : 0))
  },
  methods:{
    search(){
      this.filtered = [];
      for (let cottage of this.cottages){
        if (cottage.name.includes(this.filter.name) && (cottage.city.includes(this.filter.location) ||
            cottage.country.includes(this.filter.location) || cottage.street.includes(this.filter.location)) &&
        cottage.mark >= this.filter.mark){
          this.filtered.push(cottage)
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
