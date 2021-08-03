package com.example.smalltalks.model.repository

import com.example.smalltalks.model.di.remote.Socket
import com.example.smalltalks.model.repository.local.LocalRepository
import com.example.smalltalks.model.repository.remote.LocalChatContract
import com.example.smalltalks.model.repository.remote.SocketRepository
import com.example.smalltalks.view.chat.MessageItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class RepositoryDecorator @Inject constructor(
    private val localRepository: LocalRepository,
    @Socket private val socketRepository: SocketRepository
) : DataRepository, LocalChatContract {
    override val connect
        get() = socketRepository.connect
    override val me
        get() = socketRepository.me
    override val users
        get() = socketRepository.users

    override fun connect(userName: String) {
        socketRepository.connect(userName)
    }

    @ExperimentalCoroutinesApi
    override val messages: Flow<MessageItem>
        get() {
            println(localRepository.getMessages())
            val localFlow = localRepository.getMessages().asFlow()
            val remoteFlow = socketRepository.messages
            return merge(localFlow, remoteFlow)
        }

    override fun sendMessage(to: String, message: String) =
        socketRepository.sendMessage(to, message)

    override fun saveMessage(messageItem: MessageItem) {
        localRepository.addMessage(messageItem)
    }
}