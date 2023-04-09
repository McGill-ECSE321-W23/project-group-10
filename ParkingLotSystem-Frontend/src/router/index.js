import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ServicesAdmin from '@/components/ServicesAdmin.vue'
import ServicesCustomer from '@/components/ServicesCustomer.vue'
import Employee from '@/components/Employee.vue'
import Manager from '@/components/Manager.vue'
import EditProfileEmployee from "@/components/EditProfileEmployee.vue";
import EditProfileMonthlyCustomer from "@/components/EditProfileMonthlyCustomer.vue";
import EditProfileManager from "@/components/EditProfileManager.vue";

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
      path: '/manager',
      name: "Manager",
      component: Manager
    },
    {
      path: '/employee',
      name: "Employee",
      component: Employee
    },
    {
      path: '/edit-profile-manager',
      name: "EditProfileManager",
      component: EditProfileManager
    },
    {
      path: '/edit-profile-employee',
      name: "EditProfileEmployee",
      component: EditProfileEmployee
    },
    {
      path: '/edit-profile-monthly-customer',
      name: "EditProfileMonthlyCustomer",
      component: EditProfileMonthlyCustomer
    },
  ]
})
