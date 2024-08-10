package ir.atefehtaheri.movieapp.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_keys"
)
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val next_page: Int?,
    val prev_page: Int?,

)
