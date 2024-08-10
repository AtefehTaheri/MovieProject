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

    @Query("select * from remote_keys where id=:key")
    suspend fun getKeyByMovie(key: String): RemoteKey?

    @Query("delete from remote_keys")
    suspend fun clearKeys()
}