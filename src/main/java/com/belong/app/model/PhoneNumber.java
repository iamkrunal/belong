package com.belong.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "phoneNumbers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PhoneNumber {
    @Id
    private String phoneNumber;
    private Boolean activated;
    private Date dateActivated;
}
