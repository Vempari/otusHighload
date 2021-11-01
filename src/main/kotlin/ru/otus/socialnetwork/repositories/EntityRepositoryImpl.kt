package ru.otus.socialnetwork.repositories

import org.hibernate.Session
import org.springframework.stereotype.Repository
import ru.otus.socialnetwork.entity.BaseEntity
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
@Transactional
class EntityRepositoryImpl(private val entityManager: EntityManager) : EntityRepository {

    override fun <E : BaseEntity> persist(entity: E): Long {
        return getSession().save(entity) as Long
    }

    override fun <E : BaseEntity> getRequired(entityClass: Class<E>, id: Long): E? {
        return getSession().get(entityClass, id)
    }

    override fun <E : BaseEntity> delete(entity: E) {
        getSession().delete(entity)
    }

    fun getSession() : Session {
        return entityManager.unwrap(Session::class.java)
    }
}