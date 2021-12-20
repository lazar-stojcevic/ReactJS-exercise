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
      <br>
      <b-button @click="reserve" variant="primary">Reserve</b-button>
    </b-card>
  </div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
export default {
  name: "NewAdventureReservation",
  data() {
    return {
      AdventureReservationService,
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
    });
  },
  methods:{
    reserve(){
      AdventureReservationService.reserveAdventure(this.id);
    }
  }
}
</script>

<style scoped>

</style>
