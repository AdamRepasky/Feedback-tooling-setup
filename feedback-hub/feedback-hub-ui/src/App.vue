<template>
  <div class="container">
    <div class="row mb-2">
      <h1 class="mt-5 fw-bolder text-success">Feedback Hub</h1>
    </div>
    <div class="row mb-2">
      <p>This page serves to link the publication developed with the help of specimens provided by biobanks in the BBMRI-ERIC network. Keeping this information provides the certain form of feedback for biobankers, on the basis of which they can judge the suitability of keeping specific types of samples in biobanks.</p>
    </div>
    <div class="row mb-2">
      <p>To create "feedback", it is necessary to insert a link to your publication (DOI) and the ID of the approved request on the basis of which you were granted the samples used in your study.</p>
    </div>
    <div class="row mb-2">

      <div class="form-group">
          <label for="requestId">Request ID</label>
          <input type="text" class="form-control" id="requestId" aria-describedby="requestHelp" placeholder="request12345" v-model="requestID">
          <small id="requestHelp" class="form-text text-muted">The ID of the granted request</small>
        </div>
        <div class="form-group">
          <label for="requestId">Digital Object Identifier (DOI)</label>
          <input type="text" class="form-control" id="doi" aria-describedby="doiHelp" placeholder="http://dx.doi.org/10.0000/0000" v-model="publication">
          <small id="doiHelp" class="form-text text-muted">Unique refference to publication</small>
        </div>
        <button type="submit" class="btn btn-primary" :disabled="!enableSubmit" @click=postData()>Submit</button>

    </div>
    <div class="row mb-2">
        <div class="alert alert-success" role="alert" v-if="enableSuccessAlert">
          Feedback successfully processed for addition.
        </div>
        <div class="alert alert-danger" role="alert" v-if="enableDangerAlert">
          Failed to upload feedback.
        </div>
      </div>
  </div>
</template>

<script>
// Importing http client
import axios from 'axios'

export default {
  name: 'App',
  data() {
    return {
        publication: "",
        requestID : "",
        enableSuccessAlert: false,
        enableDangerAlert: false,
        postPath: "/doi-data"
    }
  },
  methods: {
    postData() {
      this.enableSuccessAlert = false;
      this.enableDangerAlert = false;
      console.log("sending data");
      axios.post(import.meta.env.VITE_BACKEND_URI + this.postPath, { "requestID" : this.requestID, "publicationReference" : this.publication } )
      .then(response => {
        console.log(response);
        if (response.status == 200) {
          console.log("ok")
          this.enableSuccessAlert = true;
          this.enableDangerAlert = false;
        } else {
          console.log("nok")
          this.enableSuccessAlert = false;
          this.enableDangerAlert = true;
        }
      })
    }
  },
  computed: {
    // a computed getter
    enableSubmit() {
      // `this` points to the component instance
      return this.requestID != "" && this.publication != ""
    }
  }
}
</script>


<style scoped>
</style>
