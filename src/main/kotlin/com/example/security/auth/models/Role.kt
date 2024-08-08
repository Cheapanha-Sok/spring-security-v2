package com.example.security.auth.models

import com.panha.base.BaseEntity
import jakarta.persistence.*


@Entity
@Table(name = "roles")
data class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    @Column(unique = true)
    var roleName: String? = null,
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    var user: List<User>? = null,
): BaseEntity()