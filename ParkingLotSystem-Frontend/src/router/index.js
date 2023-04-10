import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import CreateParkingSpotType from '@/components/CreateParkingSpotType.vue'
import ServicesAdmin from '@/components/ServicesAdmin.vue'
import ServicesCustomer from '@/components/ServicesCustomer.vue'
import CreateParkingSpot from '@/components/CreateParkingSpot.vue'
import LoginPage from '@/components/LoginPage.vue'
import ListOfReservationPage from '@/components/ListOfReservationPage.vue'
import AdminSettings from '@/components/AdminSettings.vue'
import Welcome from '@/components/Welcome.vue'

import Employee from '@/components/Employee.vue'
import EditProfileEmployee from "@/components/EditProfileEmployee.vue";
import EditProfileMonthlyCustomer from "@/components/EditProfileMonthlyCustomer.vue";
import EditProfileManager from "@/components/EditProfileManager.vue";
import ManagerDashboard from '@/components/ManagerDashboard.vue'
import MonthlyCustomer from '@/components/MonthlyCustomer.vue'
import Guest from '@/components/Guest.vue'
import RegistrationCustomer from '@/components/RegistrationCustomer.vue'
import RegistrationEmployee from '@/components/RegistrationEmployee.vue'
import Payment from '@/components/Payment.vue'
import CreateMonthlyCustomerReservationPage from '@/components/CreateMonthlyCustomerReservationPage.vue'
import MonthlyCustomerReservation from '@/components/MonthlyCustomerReservation.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/create-monthly-customer-reservationPage', 
      name: "CreateMonthlyCustomerReservationPage",
      component: CreateMonthlyCustomerReservationPage

    },
    {
      path: '/admin/service-requests',
      name: "ServicesAdmin",
      component: ServicesAdmin
    }, 
    {
      path: '/parking-spot-type', // change to admin/parking-spot-types
      name: "CreateParkingSpotType",
      component: CreateParkingSpotType
    },
    {
      path: '/service-requests',
      name: "ServicesCustomer",
      component: ServicesCustomer
    },
    {
      path: '/parking-spot', // admin/parking-spot
      name: "CreateParkingSpot",
      component: CreateParkingSpot
    },
    {
      path: '/login-page',
      name: "LoginPage",
      component: LoginPage
    },
    {
      path: '/list-of-reservation-page',
      name: "ListOfReservationPage",
      component: ListOfReservationPage

    },
    {
      path: '/parking-spot', // admin/parking-spot
      name: "CreateParkingSpot",
      component: CreateParkingSpot
    },
    {
      path: '/login-page',
      name: "LoginPage",
      component: LoginPage
    },
    {
      path: '/list-of-reservation-page',
      name: "ListOfReservationPage",
      component: ListOfReservationPage

    },
    {
      path: '/admin-settings',
      name: "AdminSettings",
      component: AdminSettings
    },
    {
      path: '/welcome',
      name: "Welcome",
      component: Welcome
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
    {
      path: '/registration-customer',
      name: "RegistrationCustomer",
      component: RegistrationCustomer
    },
    {
      path: '/registration-employee',
      name: "RegistrationEmployee",
      component: RegistrationEmployee
    },
    {
      path: '/payment',
      name: "Payment",
      component: Payment
    },
    {
      path: '/monthly-customer-reservation',
      name: "MonthlyCustomerReservation",
      component: MonthlyCustomerReservation

    }
  ]
})
