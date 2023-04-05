<template>
  <div class="services-admin">
    <NavBar :navItems="navItems" :username="username"/>
    <div class="content">
      <h1>Services</h1>
      <b-container class="mt-3" fluid>
        <!-- Main table element -->
        <b-table
          :items="items"
          :fields="fields"
          :current-page="currentPage"
          :per-page="perPage"
          stacked="md"
          show-empty
          small
        >

          <template #cell(assignment)="row">
            <b-button v-if="!row.item.isAssigned" size="sm" @click="assign(row.item)" class="mr-1">
              Assign
            </b-button>
            <span v-else>Assigned</span>
          </template>

          <!-- <template #row-details="row">
            <b-card>
              <ul>
                <li v-for="(value, key) in row.item" :key="key">{{ key }}: {{ value }}</li>
              </ul>
            </b-card>
          </template> -->
        </b-table>

        <!-- User Interface controls -->
        <b-row>
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
      </b-container>
    </div>
  </div>
</template>

<script src="@/scripts/ServicesAdmin.js"></script>

<style>

</style>