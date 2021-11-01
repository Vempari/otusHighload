package ru.otus.socialnetwork.repositories

import ru.otus.socialnetwork.entity.Client

interface ClientRepository {

    fun getRequired(id: Long) : Client?
    fun getClients() : List<Client>?
}