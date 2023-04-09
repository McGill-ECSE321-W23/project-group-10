<template>
  <div class="services-admin">
    <NavBar 
      :navItems="['dashboard', 'settings', 'services-admin', 'reservations-admin']" 
      activeNav="services-admin" 
      :username="username" 
    />
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Service Requests</h2>
      <b-container class="mt-3" fluid>
        <!-- Main table element -->
        <b-table
          :busy="isBusy"
          :items="serviceRequests"
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

          <template #cell(licenseNumber)="row">
            <span 
              v-if="row.item.hasOwnProperty('monthlyCustomerDto')" 
              v-b-tooltip.hover 
              :title="createTooltip(row.item)"
            >
              {{ row.item.licenseNumber }}
            </span>
            <span v-else>{{ row.item.licenseNumber }}</span>
          </template>

          <template #cell(paymentDate)="row">
            <span v-if="row.item.hasOwnProperty('paymentDate')">
              {{ row.item.paymentDate }}
            </span>
            <span v-else>-</span>
          </template>

          <template #cell(assignment)="row">
            <b-button v-if="!row.item.isAssigned" size="sm" @click="assign(row.item)" class="mr-1">
              Assign
            </b-button>
            <span v-else>Assigned</span>
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
        <b-button class="mb-3" variant="light" @click="refresh()">Refresh</b-button>
      </b-container>
    </div>
  </div>
</template>

<script src="@/scripts/ServicesAdmin.js"></script>

<style>
  h1,h3 {
    margin-bottom: 30px;
  }
</style>