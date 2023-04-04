package ca.mcgill.ecse321.parkinglotsystem.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "service_req_type")
public abstract class ServiceRequest {
    private int id;
    private boolean isAssigned;
    private Service services;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean getIsAssigned() {
        return isAssigned;
    }
    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }
    @ManyToOne(optional = false)
    public Service getService() {
        return services;
    }
    public void setService(Service services) {
        this.services = services;
    }
}
