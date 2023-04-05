import NavBar from '@/components/NavBar.vue'

export default {
  name: "services-admin",
  data() {
    return {
      items: [
        { service: "Service 1", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: false },
        { service: "Service 2", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: false },
        { service: "Service 3", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
        { service: "Service 4", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
        { service: "Service 6", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
        { service: "Service 5", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
        { service: "Service 7", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
        { service: "Service 8", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
        { service: "Service 9", licenseNumber: "13418", paymentDate: "2023-03-05", isAssigned: true },
      ],
      fields: [
        { key: 'service', label: 'Service', sortDirection: 'desc' },
        { key: 'licenseNumber', label: 'License number', class: 'text-center' },
        { key: 'paymentDate', label: 'Payment date' },
        { key: 'assignment', label: 'Assignment' }
      ],
      totalRows: 1,
      currentPage: 1,
      perPage: 10,
      pageOptions: [5, 10, 15, { value: 100, text: "Show a lot" }],
      navItems: [
        { text: "Dashboard", href: "#"},
        { text: "Settings", href: "#"},
        { text: "Services", href: "#"},
        { text: "Reservations", href: "#"}
      ],
      username: "Marco"
    }
  },
  mounted() {
    // Set the initial number of items
    this.totalRows = this.items.length
    // Set the row variant to each item
    this.items.forEach(item => {
      item._rowVariant = !item.isAssigned ? 'info':'';
    });
  },
  methods: {
    assign(item) {
      console.log("Assign button pressed for: ", item.service);
    }
  },
  components: {NavBar}
}