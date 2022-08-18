package com.belong.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Setter
public class Customer {
    @Id
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(targetEntity = PhoneNumber.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private List<PhoneNumber> phoneNumbers;

    public List<PhoneNumber> getPhoneNumbers() {
        return this.phoneNumbers;
    }
}
