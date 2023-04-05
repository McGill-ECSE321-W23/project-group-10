import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import MonthlyCustomerReservationPage from '@/components/MonthlyCustomerReservationPage'

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
    }
  ]
})
