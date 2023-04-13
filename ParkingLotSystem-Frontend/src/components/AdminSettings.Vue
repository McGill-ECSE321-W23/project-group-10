<template>
  <div class="AdminSettings">
    <NavBar activeNav="settings"/>
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Parking Lot System Settings</h2>
      <p id="msg">
        You can edit the settings below.
      </p>
      <br>

      <h3>Opening Hours</h3>
      <label>From:</label>
      <b-form-timepicker 
        v-model="openTime" 
        class="mb-2 mx-auto custom-width">
      </b-form-timepicker>
      <label>To:</label>
      <b-form-timepicker 
        v-model="closeTime" 
        class="mb-3 mx-auto custom-width">
      </b-form-timepicker>
      <b-button 
        type="submit" 
        class="mb-3 mx-auto custom-width" 
        variant="primary" 
        @click="setHours()">
        Save
      </b-button>
      <br><br>

      <h3>Parking Fees</h3>
      <label>Parking Spot Type:</label><br>
      <b-form-select 
        class="mb-2 text-center mx-auto custom-width"
        :options="[{ text: '-', value: null }, ...spotTypes]" 
        @change="getSpotTypeInfo()"
        v-model="selectedSpotType">
      </b-form-select>
      <br>
      <div v-if="selectedSpotType">
        <label>Fee:</label>
        <b-input 
          type="number" 
          step=".01" 
          v-model="spotTypeFee" 
          class="mb-2 mx-auto custom-width">
        </b-input>
        <label>in CAD$ per {{ selectedSpotType=="subscription" ? "month" : "15 minutes" }}</label>
        <br>
        <b-button 
          type="submit" 
          class="mb-3" 
          variant="primary" 
          @click="setFee()">
          Save
        </b-button>
      </div>
      <br>

      <h3>Available Services</h3>
      <label>Edit a service:</label><br>
      <b-form-select 
        class="mb-2 text-center mx-auto custom-width"
        :options="[{ text: '- New Service -', value: null }, ...services]" 
        @change="getServiceInfo()"
        v-model="selectedService">
      </b-form-select>
      <br>
      <label>Description:</label>
      <b-input 
        type="text" 
        v-model="serviceDesc" 
        class="mb-2 mx-auto custom-width" 
        :readonly="selectedService!=null">
      </b-input>
      <label>Price:</label>
      <b-input 
        type="number" 
        step=".01" 
        v-model="serviceFee" 
        class="mx-auto custom-width">
      </b-input>
      <label>in CAD$</label>
      <br>
      <b-button 
        type="submit" 
        class="mb-3" 
        variant="primary" 
        @click="saveService()">
        Save
      </b-button>
      <b-button 
        class="mb-3" 
        variant="danger" 
        @click="deleteService()">
        Delete
      </b-button>
    </div>
  </div>
</template>

<script src="@/scripts/AdminSetting.js"></script>
<style scoped>
#msg {
  color: dodgerblue;
}
</style>