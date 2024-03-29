package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Entity
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
