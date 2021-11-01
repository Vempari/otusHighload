package ru.otus.socialnetwork.services

import org.springframework.stereotype.Service
import ru.otus.socialnetwork.entity.Client
import ru.otus.socialnetwork.repositories.ClientRepository
import ru.otus.socialnetwork.repositories.EntityRepository
import java.lang.IllegalStateException
import javax.transaction.Transactional

@Service
@Transactional
class ClientServiceImpl (
    private val clientRepository: ClientRepository,
    private val entityRepository: EntityRepository
) : ClientService{

    override fun getList() : List<Client> {
        return clientRepository.getClients() ?: throw IllegalStateException("Clients not found")
    }

    override fun addFriendToClient(currentClient: Client, clientId: Long) {
        val friend = clientRepository.getRequired(clientId) ?: throw IllegalStateException("Client with id: $clientId is not found")

        currentClient.friends.add(friend)
        entityRepository.persist(currentClient)
    }
}