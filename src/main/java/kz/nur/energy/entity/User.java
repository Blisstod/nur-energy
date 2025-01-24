package kz.nur.energy.entity;

import jakarta.persistence.*;
import kz.nur.energy.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
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

    @Column(name = "USERNAME", nullable = false)
    protected String username;

    @Column(name = "PASSWORD")
    protected String password;

    @Column(name = "FIRST_NAME")
    protected String firstName;

    @Column(name = "LAST_NAME")
    protected String lastName;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "MOBILE_NUM")
    private String mobileNum;

//    @OnDelete(DeletePolicy.CASCADE)
//    @JoinColumn(name = "BALANCE_ID")
//    @Composition
//    @OneToOne(fetch = FetchType.LAZY)
//    private Balance balance;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Auto> autoList;

    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
}
