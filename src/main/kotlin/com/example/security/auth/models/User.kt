package com.example.security.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.panha.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users", indexes = [Index(columnList = "username")])
@JsonIgnoreProperties(value = ["password" , "roles"] , allowSetters = true)
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,
    var userCode: String? = null,
    var name: String? = null,
    @Column(unique = true)
    var username: String? = null,
    @Column(unique = true)
    var email: String? = null,
    var password: String? = null,
    var deviceToken: String? = null,
    var phoneNumber: String? = null,
    @ManyToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)])
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id" , nullable = true)],
        inverseJoinColumns = [JoinColumn(name = "role_id" , nullable = true)]
    )
    var roles: MutableSet<Role>? = null,
): BaseEntity()