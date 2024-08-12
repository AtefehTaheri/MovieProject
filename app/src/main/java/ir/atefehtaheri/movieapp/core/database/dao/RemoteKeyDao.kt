package ir.atefehtaheri.movieapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.atefehtaheri.movieapp.core.database.entities.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKey)

    @Query("select * from remote_keys where id=:id")
    suspend fun getKeyById(id: Int): RemoteKey?

    @Query("delete from remote_keys where mediaType=:mediaType")
    suspend fun clearKeys(mediaType: String)
}