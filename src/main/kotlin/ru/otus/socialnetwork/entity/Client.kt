package ru.otus.socialnetwork.entity

import org.hibernate.annotations.BatchSize
import ru.otus.socialnetwork.enums.Gender
import javax.persistence.*

@Entity
@Table(name = "clients")
@BatchSize(size = 10)
@SequenceGenerator(name = "Client_seq_gen", sequenceName = "client_seq", allocationSize = 1)
class Client(

    @Id
    @GeneratedValue(generator = "Client_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "surname")
    val surname: String,

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(name = "city")
    val city: String,

    @OneToMany(fetch = FetchType.LAZY)
    val hobbies: MutableList<Hobbies>?,

    @OneToMany(fetch = FetchType.LAZY)
    val friends: MutableList<Client> = ArrayList()
) : BaseEntity()