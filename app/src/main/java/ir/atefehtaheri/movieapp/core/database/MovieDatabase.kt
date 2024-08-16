package ir.atefehtaheri.movieapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.atefehtaheri.movieapp.core.database.dao.FavoriteMovieDao
import ir.atefehtaheri.movieapp.core.database.dao.MovieDao
import ir.atefehtaheri.movieapp.core.database.dao.RemoteKeyDao
import ir.atefehtaheri.movieapp.core.database.entities.FavoriteMovieEntity
import ir.atefehtaheri.movieapp.core.database.entities.MovieEntity
import ir.atefehtaheri.movieapp.core.database.entities.RemoteKey


@Database(
    entities = [MovieEntity::class, RemoteKey::class, FavoriteMovieEntity::class], version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val remoteKeyDao: RemoteKeyDao
    abstract val favoriteMovieDao: FavoriteMovieDao

}