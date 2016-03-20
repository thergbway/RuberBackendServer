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
@Table(name = "external_app_credentials")
@NamedQuery(name = "ExternalAppCredential.getByAppId", query = "select c from ExternalAppCredential c where :appId = c.appId")
public class ExternalAppCredential {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(name = "app_id", nullable = false, unique = true)
    private Integer appId;

    @Column(name = "app_secret", nullable = false, unique = true)
    private String appSecret;

    @Column(nullable = false)
    private String description;
}
