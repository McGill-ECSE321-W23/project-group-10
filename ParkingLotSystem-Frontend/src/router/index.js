import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ServicesAdmin from '@/components/ServicesAdmin.vue'
import ServicesCustomer from '@/components/ServicesCustomer.vue'
import Employee from '@/components/Employee.vue'
import ManagerDashboard from '@/components/ManagerDashboard.vue'
import MonthlyCustomer from '@/components/MonthlyCustomer.vue'
import Guest from '@/components/Guest.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/admin/service-requests',
      name: "ServicesAdmin",
      component: ServicesAdmin
    },
    {
      path: '/service-requests',
      name: "ServicesCustomer",
      component: ServicesCustomer
    },
    {
      path: '/manager/dashboard',
      name: "Manager",
      component: ManagerDashboard
    },
    {
      path: '/employee',
      name: "Employee",
      component: Employee
    },
    {
      path: '/monthly-customer',
      name: "MonthlyCustomer",
      component: MonthlyCustomer
    },
    {
      path: '/guest',
      name: "Guest",
      component: Guest
    },
  ]
})
