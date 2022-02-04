<template>
<div class="container">
  <!-- Modal -->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">ENTER REASON FOR DELETING</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submitAnswer">
            <div class="input-group">
              <span class="input-group-text">ANSWER</span>
              <input type="text" class="form-control" v-model="answer" required/>
            </div>
            <div class="input-group" style="margin-top: 10px; margin-bottom: 20px">
              <label for="del" STYLE="margin-right: 10px">FOR DELETING</label>
              <input style="margin-top: 6px" type="checkbox" id="del" v-model="forDelete"/>
            </div>
            <button type="reset" class="btn btn-info" data-bs-dismiss="modal">CLOSE</button>
            <button type="submit" class="btn btn-danger" style="margin-left: 10px">SUBMIT ANSWER</button>
          </form>
        </div>
      </div>
    </div>
  </div>

  <table class="table table-striped">
    <thead>
    <th>USERNAME</th>
    <th>FULL NAME</th>
    <th>REASON FOR DELETING</th>
    </thead>
    <tbody>
    <tr v-for="r in requests" :key="r.id">
      <td class="col-md-2">{{r.user.username}}</td>
      <td class="col-md-2">{{r.user.firstname}} {{r.user.lastName}}</td>
      <td class="col-md-7">{{r.requestText}}</td>
      <td class="col-md-1"><button class="btn-sm btn-warning" @click="openForm(r.id)"
                                   data-bs-toggle="modal" data-bs-target="#exampleModal">ANSWER</button></td>
    </tr>
    </tbody>
  </table>
</div>
</template>

<script>
import DeleteProfileRequestService from "@/Services/DeleteProfileRequestService";
export default {
  data(){
    return{
      requests: [],
      requestId: '',
      forDelete: '',
      answer: ''
    }
  },

  mounted() {
    this.loadAllRequests();
  },

  methods:{
    loadAllRequests(){
      DeleteProfileRequestService.getAllNotReviewedRequests().then(res => {this.requests = res.data}).catch(() => {
        alert("THERE IS SOME ERROR WITH LOADING DELETE PROFILE REQUESTS");
      })
    },

    openForm(id){
      this.requestId = id;
      this.answer = '';
    },

    submitAnswer(){
      let data = {};
      data.answer = this.answer;
      data.requestId = this.requestId;
      data.forDelete = this.forDelete;
      DeleteProfileRequestService.answerOnDeleteRequest(data).then(() => {this.loadAllRequests()}).catch(() => {
        alert("THERE IS SOME ERROR WITH DELETING PROFILE");
      })
    }
  }
}
</script>

<style scoped>

</style>