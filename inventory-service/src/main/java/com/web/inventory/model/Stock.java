package com.web.inventory.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_id_generator")
    @SequenceGenerator(name = "stock_id_generator", sequenceName = "stock_id", initialValue = 100, allocationSize = 1)
    private int id;
    private String productCode;
    private Date createdDate;
    private Date updatedDate;
    private int quantity;
}
