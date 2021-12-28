<template>
  <div class="container">
    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">ENTER MAILS</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="submitAnswer">
              <div class="input-group" style="margin-top: 5px">
                <span class="input-group-text">TO CUSTOMER</span>
                <input type="text" class="form-control" v-model="answer.mailForCustomer" required/>
              </div>
              <div class="input-group" style="margin-top: 10px">
                <span class="input-group-text">TO OWNER</span>
                <input type="text" class="form-control" v-model="answer.mailForOwner" required/>
              </div>
              <div style="margin-top: 10px">
                <button type="reset" class="btn-sm btn-warning" data-bs-dismiss="modal">CLOSE</button>
                <button type="submit" class="btn-sm btn-success" style="margin-left: 10px">SUBMIT ANSWER</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <table class="table table-striped">
      <thead>
        <th>SUBMITTER FULL NAME</th>
        <th>OWNER FULL NAME</th>
        <th>COMPLAINT TEXT</th>
      </thead>
      <tbody>
        <tr v-for="c in complaints" :key="c.id">
          <td class="col-md-3">
            {{c.reservation.customer.firstname}} {{c.reservation.customer.lastName}}
          </td>
          <td class="col-md-2">
            {{c.reservation.adventure.instructor.firstname}} {{c.reservation.adventure.instructor.lastName}}
          </td>
          <td class="col-md-6">{{c.text}}</td>
          <td class="col-md-1"><button class="btn-sm btn-success" @click="prepareAnswer(c.id)"
                    data-bs-toggle="modal" data-bs-target="#exampleModal">ANSWER</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import ComplaintService from "@/Services/ComplaintService";

export default {
  data(){
    return{
      complaints: [],
      answer: {complaintId: '', mailForOwner: '', mailForCustomer: '', complaintType: 'AC'}
    }
  },

  mounted() {
    this.loadAdventureComplaints();
  },

  methods:{
    loadAdventureComplaints(){
      ComplaintService.loadAllAdventureComplaints().then(res => {this.complaints = res.data}).catch(() => {
        alert("THERE IS SOME ERROR WITH LOADING ADVENTURE COMPLAINTS");
      })
    },

    prepareAnswer(id){
      this.answer = {complaintId: id, mailForOwner: '', mailForCustomer: '', complaintType: 'AC'};
    },

    submitAnswer() {
      ComplaintService.reviewComplaint(this.answer).then(() => {this.loadAdventureComplaints()}).catch(() => {
        alert("THERE IS SOME PROBLEM WITH SENDING COMPLAINT");
      })
    }
  }
}
</script>

<style scoped>

</style>