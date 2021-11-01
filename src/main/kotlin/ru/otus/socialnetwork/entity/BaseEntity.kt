package ru.otus.socialnetwork.entity

import org.hibernate.annotations.OptimisticLock
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.Column
import javax.persistence.Version

open class BaseEntity (
    @Version
    @Column(name = "entity_version", nullable = false)
    val entityVersion: Long = 0,

    @OptimisticLock(excluded = true)
    @Column(name = "entity_created", nullable = false)
    val entityCreated: LocalDateTime = now(),

    @OptimisticLock(excluded = true)
    @Column(name = "entity_updated", nullable = false)
    val entityUpdated: LocalDateTime = now()
)