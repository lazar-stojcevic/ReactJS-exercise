<template>
  <div class="container">
    <div class="search">
      <b-input-group prepend="Instructor name" class="mb-2 mt-2">
        <b-form-input aria-label="Instructor name" v-model="filter.name"></b-form-input>
      </b-input-group>
      <b-input-group prepend="Location" class="mb-2 mt-2 m-lg-2">
        <b-form-input aria-label="Instructor location" v-model="filter.location"></b-form-input>
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
    <div v-for="instructor in filtered" :key="instructor.id">
      <b-card
          tag="cottage"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{instructor.firstname}} {{instructor.lastName}}
        </b-card-title>
        <b-card-text>
          Phone number: {{ instructor.phone }}
        </b-card-text>
        <br>
        <b-card-text>
          ADDRESS:  {{ instructor.address.country }} , {{ instructor.address.city }}, {{instructor.address.street}}
        </b-card-text>
        <br>
        <p>
          Average mark: {{ instructor.mark }}
        </p>

        <router-link class="btn btn-secondary" :to="'instructor/'+instructor.id.toString()" style="margin: 5px">See adventures</router-link>

      </b-card>
    </div>

  </div>
</template>

<script>
import FishingInstructorService from "@/Services/FishingInstructorService";
import GradeService from "@/Services/GradeService";

export default {
  name: "AllInstructors",
  data() {
    return {
      instructors : [],
      filtered : [],
      instructor : {},
      filter: {
        name:'',
        location:'',
        mark : 0
      },
      sort : 'nameASC'
    }
  },
  mounted() {
    FishingInstructorService.getAllFishingInstructors().then(res => {
      this.instructors = res.data;
      this.filtered = res.data;
    }).then(() => {
      for(let ad of this.instructors){
        GradeService.getAllGradeOfInstructor(ad.id).then(res =>{
          ad.mark = res.data.avgRating;
          console.log(ad.mark)
        })
      }
      for(let ad of this.filtered){
        GradeService.getAllGradeOfInstructor(ad.id).then(res =>{
          ad.mark = res.data.avgRating;
          console.log(ad.mark)
        })
      }
    })

    },
  methods:{
    search(){
      this.filtered = [];
      for (let instructor of this.instructors){
        if (instructor.name.includes(this.filter.name) && (instructor.city.includes(this.filter.location) ||
                instructor.country.includes(this.filter.location) || instructor.street.includes(this.filter.location)) &&
            instructor.mark >= this.filter.mark){
          this.filtered.push(instructor)
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
