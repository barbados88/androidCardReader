package com.abank.IDCard.domain

import android.app.Application
import com.abank.IDCard.data.repository.database.DataBase
import com.abank.IDCard.data.repository.server.ServerCommunicator

class ExampleViewModel(application: Application,
                       private val dataBase: DataBase,
                       private val communicator: ServerCommunicator): BaseViewModel(application) {
}