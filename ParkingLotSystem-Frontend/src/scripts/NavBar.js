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
        { id: "dashboard-employee", text: "Dashboard", href: "#/employee"},
        { id: "dashboard-monthly-customer", text: "Dashboard", href: "#/monthly-customer"},
        { id: "dashboard-guest", text: "Dashboard", href: "#"},
        { id: "settings", text: "Settings", href: "#"},
        { id: "subscription", text: "Subscription", href: "#"},
        { id: "reservations-view", text: "View Reservations", href: "#"},
        { id: "services-admin", text: "Services", href: "#/admin/service-requests"},
        { id: "reservations-admin", text: "Reservations", href: "#/admin/reservations"},
        { id: "services-customer", text: "Services", href: "#/service-requests"},
        { id: "reservations-customer", text: "Reservations", href: "#/reservations"}
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
