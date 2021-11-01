package ru.otus.socialnetwork.repositories

import org.hibernate.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import ru.otus.socialnetwork.entity.BaseEntity
import java.io.Serializable
import javax.persistence.EntityManager

@Repository
abstract class AbstractRepository <T : BaseEntity, ID : Serializable> (
    private val entityClass: Class<T>
    ) {

    @Autowired
    private lateinit var entityManager: EntityManager

    fun session() : Session {
        return entityManager.unwrap(Session::class.java)
    }

    fun persist(entity : T) : ID {
        return session().save(entity) as ID
    }

    fun getRequired(id: ID) : T {
        return session().get(entityClass, id) as T
    }

    fun getEntityClass() : Class<T> {
        return entityClass
    }



}