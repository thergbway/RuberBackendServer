package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "markets")
@NamedQueries({
    @NamedQuery(name = "Market.getByVkGroupId", query = "select m from Market m where m.vkId = :vk_group_id")
})
public class Market {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(name = "vk_id", nullable = false)
    private Integer vkId;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "market_id")
    private List<Order> orders;
}
