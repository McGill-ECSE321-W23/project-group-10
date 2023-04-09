import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ServicesAdmin from '@/components/ServicesAdmin.vue'
import ServicesCustomer from '@/components/ServicesCustomer.vue'
import AdminSettings from '@/components/AdminSettings.vue'
import Welcome from '@/components/Welcome.vue'

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
      path: '/admin-settings',
      name: "AdminSettings",
      component: AdminSettings
    },
    {
      path: '/welcome',
      name: "Welcome",
      component: Welcome
    }
  ]
})
