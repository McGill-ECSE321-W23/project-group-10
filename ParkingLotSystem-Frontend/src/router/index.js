import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import MonthlyCustomerReservationPage from '@/components/MonthlyCustomerReservationPage'
import ServicesAdmin from '@/components/ServicesAdmin.vue'
import CreateParkingSpotType from '@/components/CreateParkingSpotType.vue'
import ServicesCustomer from '@/components/ServicesCustomer.vue'
import CreateParkingSpot from '@/components/CreateParkingSpot.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/m', // change to MonthlyCustomerReservationPage
      name: 'MonthlyCustomerReservationPage',
      component: MonthlyCustomerReservationPage
    },
    {
      path: '/admin/service-requests',
      name: "ServicesAdmin",
      component: ServicesAdmin
    }, 
    {
      path: '/p', // change to admin/parking-spot-types
      name: "CreateParkingSpotType",
      component: CreateParkingSpotType
    },
    {
      path: '/service-requests',
      name: "ServicesCustomer",
      component: ServicesCustomer
    },
    {
      path: '/ps', // admin/parking-spot
      name: "CreateParkingSpot",
      component: CreateParkingSpot
    }
  ]
})
