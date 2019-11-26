package com.abank.idcard.data.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example")
data class ExampleEntity(@PrimaryKey
                         var id: Int = 0) {
}