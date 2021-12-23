<template>
  <div>
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
      <b-form-group label="Additional services:" v-slot="{ ariaDescribedby }">
        <b-form-checkbox-group
            id="checkbox-group-2"
            v-model="selected"
            :aria-describedby="ariaDescribedby"
            name="services"
        >
          <b-form-checkbox v-for="service in addServices" :key="service.id" :value="service.id">{{ service.name }} - {{service.addPrice}}</b-form-checkbox>

        </b-form-checkbox-group>
      </b-form-group>
      <br>
      <b-button @click="reserve" variant="primary">Reserve</b-button>
    </b-card>
  </div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
import AdventureService from "@/Services/AdventureService";
export default {
  name: "NewAdventureReservation",
  data() {
    return {
      AdventureReservationService,
      AdventureService,
      addServices:[],
      selected: [],
      adventure: {
        adventure:{
          name:'',
          maxPersons: 0,
          description: '',
          equipment: '',
          conductRules: ''
        },
        reservationStart: ''
      },
      id: '',
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    AdventureReservationService.getAdventureTermById(this.id).then((res) =>
    {
      this.adventure = res.data
    }).then(AdventureService.getAdditionalServicesOfReservation(this.id).then((res) =>{
      this.addServices = res.data
    }));
  },
  methods:{
    reserve(){
      AdventureReservationService.reserveAdventure(this.id, this.selected);
    }
  }
}
</script>

<style scoped>

</style>
