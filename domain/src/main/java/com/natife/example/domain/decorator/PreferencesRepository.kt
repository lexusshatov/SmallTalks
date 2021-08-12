package com.natife.example.domain.decorator

import com.example.core.repository.local.LocalData
import com.example.core.repository.local.PreferencesData
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val localRepository: LocalData
) : PreferencesData {

    override fun deleteUserName() = localRepository.deleteUserName()

    override fun getUserName() = localRepository.getUserName()

    override fun saveUserName(userName: String) = localRepository.saveUserName(userName)
}