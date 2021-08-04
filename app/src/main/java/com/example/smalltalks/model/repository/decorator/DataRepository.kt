package com.example.smalltalks.model.repository.decorator

import com.example.smalltalks.model.repository.local.LocalData
import com.example.smalltalks.model.repository.remote.RemoteData

interface DataRepository: LocalData, RemoteData