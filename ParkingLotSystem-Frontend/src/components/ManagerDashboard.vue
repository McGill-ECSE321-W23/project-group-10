<template>
  <div class="manager">
    <!--Page appear after login as manager, include statistics data of parking lot. -->
    <NavBar activeNav="dashboard"/>
    <b-alert v-model="showError" variant="danger" dismissible>Error: {{ errorMessage }}</b-alert>
    <div class="content">
      <h2>Dashboard</h2>

      <b-row class="justify-content-center">
        <b-col md="5" class="my-1">
          <h5>Floor parking spot capacity</h5>
          <VueApexCharts
            class="chart"
            height="300"
            type="bar"
            :options="floorCapacityChartOptions"
            :series="floorCapacityChartSeries">
          </VueApexCharts>
        </b-col>
        <b-col md="5" class="my-1">
          <h5>Overall parking spot capacity</h5>
          <VueApexCharts
            class="chart"
            height="300"
            type="radialBar"
            :options="capacityChartOptions"
            :series="capacityChartSeries">
          </VueApexCharts>
        </b-col>
      </b-row>

      <b-row class="justify-content-center">
        <b-col  md="5" class="my-1">
          <h5>Unassigned service requests</h5>
          <b-table
          :busy="isBusy"
          :items="serviceRequests"
          :fields="serviceReqsFields"
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

            <template #table-busy>
              <div class="text-center text-dark my-2">
                <b-spinner class="align-middle"></b-spinner>
                <strong>Loading...</strong>
              </div>
            </template>
          </b-table>
        </b-col>
        <b-col  md="5" class="my-1">
          <h5>Revenue</h5>
          <b-table
            :busy="isBusy"
            :items="payments"
            :fields="paymentsFields"
            sort-by="dateTime"
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

            <template #table-busy>
              <div class="text-center text-dark my-2">
                <b-spinner class="align-middle"></b-spinner>
                <strong>Loading...</strong>
              </div>
            </template>
          </b-table>
        </b-col>
      </b-row>
      <b-button class="mb-3" variant="light" @click="refresh()">Refresh</b-button>
    </div>
  </div>
</template>

<script src="../scripts/ManagerDashboard.js"></script>

<style scoped>
  h2 {
    margin-bottom: 30px;
  }
  h5 {
    margin-bottom: 15px;
  }
  .chart {
    overflow: hidden;
  }
</style>
