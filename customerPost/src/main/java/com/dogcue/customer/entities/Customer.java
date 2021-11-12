package com.dogcue.customer.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author Mario
 */
@Entity
@Data
@ApiModel(description = "Customer API model")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(position = 0, name = "id", required = false, notes = "Unique customer identifier. If present updates customer if exist, creates customer otherwise.")
    private long id;

    @Column(nullable = false)
    @ApiModelProperty(position = 1, name = "name", required = true, example = "Mario")
    private String name;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(position = 2, name = "email", required = true, example = "m@email.com")
    private String email;

    @Column(unique = true, nullable = true)
    @ApiModelProperty(position = 3, name = "phone", required = false, example = "111222333")
    private String phone;

    @Column(nullable = true)
    @ApiModelProperty(position = 4, name = "address", required = false, example = "C/Calle")
    private String address;

}
