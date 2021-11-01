package ru.otus.socialnetwork.services

import ru.otus.socialnetwork.entity.Client

interface ClientService {
    fun getList() : List<Client>
    fun addFriendToClient(currentClient: Client, clientId: Long)
}