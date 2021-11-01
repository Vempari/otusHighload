package ru.otus.socialnetwork.repositories

import org.springframework.stereotype.Repository
import ru.otus.socialnetwork.entity.Client

@Repository
class ClientRepositoryImpl : ClientRepository, AbstractRepository<Client, Long>(Client::class.java) {

    override fun getClients(): List<Client>? {
        val hql = "FROM Client"
        return session().createQuery(hql, Client::class.java).list()
    }
}