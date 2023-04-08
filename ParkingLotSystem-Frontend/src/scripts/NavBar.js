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
        { id: "dashboard", text: "Dashboard", href: "#/dashboard"},
        { id: "settings", text: "Settings", href: "#"},
        { id: "services-admin", text: "Services", href: "#/admin/service-requests"},
        { id: "reservations", text: "Reservations", href: "#"},
        { id: "subscription", text: "Subscription", href: "#"},
        { id: "services-customer", text: "Services", href: "#/service-requests"}
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
