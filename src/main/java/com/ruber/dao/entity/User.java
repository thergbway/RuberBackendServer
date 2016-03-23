package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
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

    @ElementCollection
    @CollectionTable(name = "connected_vk_groups")
    @Column(name = "vk_group_id", nullable = false)
    @JoinColumn(name = "user_id")
    private Set<Integer> connectedVkGroupIds;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<RuberToken> ruberTokens;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<VkToken> vkTokens;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<Order> orders;
}
