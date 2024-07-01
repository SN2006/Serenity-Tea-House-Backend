package com.example.backend.entity.order;

import com.example.backend.entity.User;
import com.example.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tea_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private OrderAddress address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_info_id", referencedColumnName = "id")
    private BuyerInfo buyerInfo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_info_id", referencedColumnName = "id")
    private CardInfo cardInfo;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "delivery_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryAt;
    @Column(name = "total_sum")
    private Double totalSum;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<ProductInOrder> products;

    public void addProduct(ProductInOrder product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

}
