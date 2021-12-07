<template>
  <div class="container w-100">
    <br>
    <p>Subscriptions</p>
    <div v-for="subscription in subscriptions" :key="subscription.id">
      <b-card
          tag="subscription"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{subscription.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Owner name: {{subscription.owner}}
        </b-card-sub-title>
        <br>
        <b-button  variant="danger">Cancel</b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Subscriptions",
  data() {
    return {
      subscriptions : [],
    }
  },
  mounted() {
    this.subscriptions = [
        {id: 24 ,name: "Shark boat", owner:"Djordje Djordjevic",type: "BOAT" },
        {id: 33 ,name: "Wooden cottage", owner:"Djordje Djordjevic",type: "COTTAGE" }
    ]
  }
}
</script>

<style scoped>

</style>
