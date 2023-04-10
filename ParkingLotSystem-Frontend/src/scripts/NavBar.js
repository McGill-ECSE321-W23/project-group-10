export default {
  name: "navbar",
  props: {
    navItems: {
      type: Array,
      required: true
    },
    activeNav: {
      type: String,
      required: true
    },
    username: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      allNavItems: [
        { id: "dashboard", text: "Dashboard", href: "#/manager/dashboard"},
        { id: "employee", text: "Main", href: "#/employee"},
        { id: "monthly-customer", text: "Main", href: "#/monthly-customer"},
        { id: "guest", text: "Main", href: "#/guest"},
        { id: "settings", text: "Settings", href: "#"},
        { id: "subscription", text: "Subscription", href: "#"},
        { id: "reservations-view", text: "View Reservations", href: "#"},
        { id: "services-admin", text: "Services", href: "#/admin/service-requests"},
        { id: "reservations-admin", text: "Reservations", href: "#/admin/reservations"},
        { id: "services-customer", text: "Services", href: "#/service-requests"},
        { id: "reservations-customer", text: "Reservations", href: "#/reservations"},
        { id: "employee-registration", text: "Employee Registration", href: "#/registration-employee"}
        { id: "monthly-customer-reservation", text: "Reservation", href: "#/monthly-customer-reservation"},
      ]
    }
  },
  methods: {
    filterNavItems() {
      let filteredNavItems = [];
      this.allNavItems.forEach(navItem => {
        if(this.navItems.includes(navItem.id)) {
          if(this.activeNav == navItem.id) {
            navItem.active = true;
          }
          filteredNavItems.push(navItem);
        }
      });
      return filteredNavItems;
    }
  }
};
