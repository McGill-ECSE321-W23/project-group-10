<template>
  <div class="AdminSettings">
    <NavBar activeNav="settings"/>
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Parking Lot System Settings</h2>
      <p id="msg">
        You can edit the settings below.
      </p>
      <div class="Opening Hours" id="OH">
        <h3>Opening Hours</h3>
        <label for="hour">From : </label>
        <b-form-timepicker v-model="openTime" class="mb-2"></b-form-timepicker>

        <label for="hour">To :&nbsp; &nbsp; &nbsp; </label>
        <b-form-timepicker v-model="closeTime" class="mb-2"></b-form-timepicker>

        <b-button type="submit" class="mb-3" variant="primary" @click="setHours()"> Set</b-button><br>


        <h3>Parking Fees</h3>
        <label for="type">Parking Spot Type : </label>
        <b-form-select 
          class="mb-2 mr-sm-2 mb-sm-0"
          :options="[{ text: '-', value: null }, ...spotTypes]" 
          v-model="selectedSpotType">
        </b-form-select>
        <br>
        <label for="fee">Fee : </label>
        <b-input type="number" step=".01" v-model="spotTypeFee"></b-input> <label>in CAD$ per</label>
        <select name="unit" id="unit">
          <option value="Hour">Hour</option>
          <option value="Month">Month</option>
        </select>
        <br>
        <b-button type="submit" class="mb-3" variant="primary" @click="setFee()"> Set</b-button>

        <h3>Available Services</h3>
        <label for="add a service">Add a service : </label><br>
        <label for="name of the new service">Description : </label>
        <input type="text" v-model="serviceDesc" name="serviceDesc"><br>
        <label for="price of the new service">Price : &nbsp; &nbsp; &nbsp;</label>
        <input type="number" step=".01" v-model="serviceFee" name="serviceFee"><label> &nbsp;in CAD$</label><br>
        <b-button type="submit" class="mb-3" variant="primary" @click="addService()"> Add</b-button><br><br>

        <label for="delete a service"> Delete a service : </label>
        <b-form-select 
          class="mb-2 mr-sm-2 mb-sm-0"
          :options="[{ text: '-', value: null }, ...services]" 
          v-model="selectedService">
        </b-form-select>
        <br>
        <b-button variant="danger" @click="deleteService()">Delete</b-button><br><br>

      </div>
    </div>
  </div>
</template>
<script src="@/scripts/AdminSetting.js"></script>
<style scoped>

#msg {
  color: dodgerblue;
}

#OH {
  text-align: left;
  margin-left: 500px;

}

h3 {
  position: relative;
  right: 200px
}

#setFee {
  border-color: dodgerblue;
  padding: 3px 10px;
  border-radius: 8px;
  margin-left: 23%;
}

#setFee:Hover {
  background-color: dodgerblue;
  padding: 3px 10px;
  border-radius: 8px;
  margin-left: 23%;
}

#setTime {
  border-color: dodgerblue;
  padding: 3px 10px;
  border-radius: 8px;
}

#setTime:Hover {
  background-color: dodgerblue;
  padding: 3px 10px;
  border-radius: 8px;
}

#addService {
  border-color: chartreuse;
  padding: 3px 10px;
  border-radius: 8px;
  margin-left: 23%;

}

#addService:Hover {
  background-color: chartreuse;
  padding: 3px 10px;
  border-radius: 8px;
  margin-left: 23%;

}

#deleteService {
  border-color: red;
  padding: 3px 10px;
  border-radius: 8px;
  margin-left: 22%;

}

#deleteService:Hover {
  background-color: red;
  padding: 3px 10px;
  border-radius: 8px;
  margin-left: 22%;

}
</style>