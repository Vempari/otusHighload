package ru.otus.socialnetwork.entity

import ru.otus.socialnetwork.enums.RolesEnum
import javax.persistence.*

@Entity
@Table(name = "roles")
@SequenceGenerator(name = "Role_seq_gen", sequenceName = "role_seq", allocationSize = 1)
class Role (
    @Id
    @GeneratedValue(generator = "Role_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    val name: RolesEnum,

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    val users: List<User>
) : BaseEntity() {

    override fun toString(): String {
        return "Role(id=$id, name='$name', users=$users)"
    }
}