import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import MonthlyCustomerReservationPage from '@/components/MonthlyCustomerReservationPage'
import ServicesAdmin from '@/components/ServicesAdmin.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/MonthlyCustomerReservationPage',
      name: 'MonthlyCustomerReservationPage',
      component: MonthlyCustomerReservationPage
    },{
      path: '/admin/service-requests',
      name: "ServicesAdmin",
      component: ServicesAdmin
    }
  ]
})
