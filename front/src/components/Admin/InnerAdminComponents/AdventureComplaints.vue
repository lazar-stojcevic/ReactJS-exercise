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
        <th>SUBMITTER MAIL</th>
        <th>OWNER MAIL</th>
        <th>COMPLAINT TEXT</th>
      </thead>
      <tbody>
        <tr v-for="c in complaints" :key="c.id">
          <td class="col-md-3">
            {{c.customerMail}}
          </td>
          <td class="col-md-2">
            {{c.ownerMail}}
          </td>
          <td class="col-md-6">{{c.comment}}</td>
          <td class="col-md-1"><button class="btn-sm btn-success" @click="prepareAnswer(c.id, c.customerMail, c.ownerMail)"
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
      answer: {complaintId: '', mailForOwner: '', mailForCustomer: '', customerMail: '', ownerMail: ''}
    }
  },

  mounted() {
    this.loadAdventureComplaints();
  },

  methods:{
    loadAdventureComplaints(){
      ComplaintService.loadAllNotReviewedComplaints().then(res => {this.complaints = res.data}).catch(() => {
        alert("THERE IS SOME ERROR WITH LOADING ADVENTURE COMPLAINTS");
      })
    },

    prepareAnswer(id, customerMail, ownerMail){
      this.answer = {complaintId: id, mailForOwner: '', mailForCustomer: '', ownerMail: ownerMail, customerMail: customerMail};
    },

    submitAnswer() {
      alert("Please wait...")
      ComplaintService.reviewComplaint(this.answer).then(() => {
        this.loadAdventureComplaints()
        alert("COMPLAINT IS REPORTED")
      }).catch(() => {
        alert("THERE IS SOME PROBLEM WITH SENDING COMPLAINT");
      })
      //alert(JSON.stringify(this.answer));
    }
  }
}
</script>

<style scoped>

</style>