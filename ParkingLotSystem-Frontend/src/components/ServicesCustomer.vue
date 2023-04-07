<template>
  <div class="services-customer">
    <NavBar 
      :navItems="['subscription', 'services-customer']" 
      activeNav="services-customer" 
      :username="username" 
    />
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h3>{{ currServiceReq==null?"Request a Service":"Current Service Request" }}</h3>
      <div class="d-flex justify-content-center">
        <b-card v-if="currServiceReq!=null" :title="currServiceReq.servicesDto.description" class="bg-transparent card">
          <b-card-text>Status: {{ currServiceReq.isAssigned?"Assigned":"Not Assigned" }}</b-card-text>
          <b-button href="#" variant="primary" @click="payServiceReq()" :disabled="!currServiceReq.isAssigned">Pay</b-button>
        </b-card>
        <b-form v-else inline class="form">
          <label class="mr-sm-2" for="inline-form-custom-select-pref">Select service: </label>
          <b-form-select
            id="inline-form-custom-select-pref"
            class="mb-2 mr-sm-2 mb-sm-0"
            :options="[{ text: '-', value: null }, ...services]"
            v-model="selectedService"
          ></b-form-select>
          <b-button variant="primary" @click="createServiceReq()" :disabled="selectedService==null">Submit</b-button>
        </b-form>
      </div>
      <h3>Service Request History</h3>
      <b-container class="mt-3" fluid>
        <!-- Main table element -->
        <b-table
          :busy="isBusy"
          :items="paidServiceReqs"
          :fields="fields"
          sort-by="id"
          sort-desc
          :current-page="currentPage"
          :per-page="perPage"
          stacked="md"
          show-empty
          small
        >

          <template #cell(service)="row">
            {{ row.item.servicesDto.description }}
          </template>

          <template #cell(paymentDate)="row">
            {{ row.item.paymentDate }}
          </template>

          <template #table-busy>
            <div class="text-center text-dark my-2">
              <b-spinner class="align-middle"></b-spinner>
              <strong>Loading...</strong>
            </div>
          </template>
        </b-table>

        <!-- User Interface controls -->
        <b-row class="justify-content-center">
          <b-col sm="2" md="3" class="my-1">
            <b-form-group
              label="Per page"
              label-for="per-page-select"
              label-cols-sm="7"
              label-cols-md="6"
              label-cols-lg="5"
              label-align-sm="right"
              label-size="sm"
              class="mb-0"
            >
              <b-form-select
                id="per-page-select"
                v-model="perPage"
                :options="pageOptions"
                size="sm"
              ></b-form-select>
            </b-form-group>
          </b-col>

          <b-col sm="4" md="4" class="my-1">
            <b-pagination
              v-model="currentPage"
              :total-rows="totalRows"
              :per-page="perPage"
              align="fill"
              size="sm"
              class="my-0"
            ></b-pagination>
          </b-col>
        </b-row>
        <b-button variant="light" @click="refresh()">Refresh</b-button>
      </b-container>
    </div>
  </div>
</template>

<script src="../scripts/ServicesCustomer.js"></script>

<style scoped>
  h3 {
    margin-bottom: 30px;
  }
  .card {
    min-width: 30%;
    max-width: 100%;
    margin-bottom: 30px;
  }
  .form {
    justify-content: center;
    margin-bottom: 30px;
  }
</style>