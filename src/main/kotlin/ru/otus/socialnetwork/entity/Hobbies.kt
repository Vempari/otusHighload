package ru.otus.socialnetwork.entity

import org.hibernate.annotations.BatchSize
import javax.persistence.*

@Entity
@Table(name = "hobbies")
@BatchSize(size = 10)
@SequenceGenerator(name = "Hobbie_seq_gen", sequenceName = "hobbie_seq", allocationSize = 1)
class Hobbies(

    @Id
    @GeneratedValue(generator = "Hobbie_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "name")
    val name: String
) : BaseEntity()