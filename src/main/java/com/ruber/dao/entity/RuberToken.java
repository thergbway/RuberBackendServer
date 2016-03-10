package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "ruber_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "getByVkToken", query = "select r from RuberToken r where :vkToken member of r.vkTokens")
public class RuberToken {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "token")
    private String value;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "vk_token", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "token")
    private Set<String> vkTokens;
}