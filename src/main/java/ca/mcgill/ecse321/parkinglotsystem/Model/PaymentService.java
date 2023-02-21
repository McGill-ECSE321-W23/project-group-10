package ca.mcgill.ecse321.parkinglotsystem.Model;

import javax.persistence.OneToOne;

public class PaymentService extends Payment {
    private ServiceRequest serviceReq;

    @OneToOne(optional = false)
    public ServiceRequest getServiceReq() {
        return serviceReq;
    }

    public void setServiceReq(ServiceRequest serviceReq) {
        this.serviceReq = serviceReq;
    }
}
