package kz.nur.energy.entity;

import jakarta.persistence.*;
import kz.nur.energy.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Table(name = "orders")
@Entity(name = "order")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @GeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Column(name = "DELETED_BY")
    private String deletedBy;

    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus status;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "START_POINT")
    private ControlPoint startPoint;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATION_POINT")
    private ControlPoint destinationPoint;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVER_ID")
    private User driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_ID")
    private Auto car;

    @Column(name = "DISTANCE")
    private Double distance;

    @Column(name = "PICK_UP_TIME")
    private LocalDateTime pickUpTime;

    @Column(name = "START_ADDRESS")
    private String startAddress;

    @Column(name = "DESTINATION_ADDRESS")
    private String destinationAddress;

    public String getInstanceName(){
        return String.format("Время: %s, Пассажир: %s", pickUpTime, phoneNumber);
    }
}
