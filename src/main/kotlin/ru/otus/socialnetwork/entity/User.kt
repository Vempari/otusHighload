package ru.otus.socialnetwork.entity

import ru.otus.socialnetwork.enums.Status
import javax.persistence.*

@Entity
@Table(name = "users")
@SequenceGenerator(name = "User_seq_gen", sequenceName = "user_seq", allocationSize = 1)
class User(

    @Id
    @GeneratedValue(generator = "User_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    val password: String,

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    val client: Client,

    @Column(name = "status")
    val status: Status,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: List<Role>
) : BaseEntity()