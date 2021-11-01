package ru.otus.socialnetwork.repositories

import ru.otus.socialnetwork.entity.BaseEntity

interface EntityRepository {

    fun <E : BaseEntity> persist(entity: E): Long
    fun <E : BaseEntity> getRequired(entityClass: Class<E>, id: Long): E?
    fun <E : BaseEntity> delete(entity: E)
}