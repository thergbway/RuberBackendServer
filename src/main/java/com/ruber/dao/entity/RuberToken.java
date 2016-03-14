package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "ruber_token")
@NamedQueries({
    @NamedQuery(name = "RuberToken.getByValue", query = "select r from RuberToken r where r.value = :ruberToken")
})
public class RuberToken {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "token", unique = true, nullable = false)
    private String value;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "vk_token", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "token")
    private Set<String> vkTokens;
}