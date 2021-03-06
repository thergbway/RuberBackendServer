package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "vk_tokens")
@NamedQueries({
    @NamedQuery(name = "VkToken.getByValue", query = "select t from VkToken t where t.value = :value")
})
public class VkToken {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String value;
}
