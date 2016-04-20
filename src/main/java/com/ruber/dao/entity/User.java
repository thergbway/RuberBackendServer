package com.ruber.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.getByVkId", query = "select u from User u where :vkId = u.vkId"),
    @NamedQuery(name = "User.getByRuberToken", query = "select u from User u where " +
        "(select r from RuberToken r where :token = r.value) member of u.ruberTokens")
})
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(name = "vk_id", nullable = false, unique = true)
    private Integer vkId;

    @ManyToMany(cascade = ALL)
    @JoinTable(
        name = "users_to_markets",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "market_id")
    )
    private Set<Market> connectedMarkets;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<RuberToken> ruberTokens;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<VkToken> vkTokens;
}
