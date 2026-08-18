package com.pulso.riego.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Lot::class, Pulse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pulso_riego_db"
                ).addCallback(SeedCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val dao = database.appDao()
                    dao.insertUser(User("admin", "admin", "Administrador", "Acceso total al sistema"))
                    dao.insertUser(User("ing_riego", "1234", "Ingeniero", "Gestión técnica de riego"))
                    dao.insertUser(User("regador", "1234", "Operador", "Operación de riego"))
                    val lotId = dao.insertLot(Lot(name = "Lote A"))
                    dao.insertPulse(Pulse(lotId = lotId, durationSeconds = 60, drainedPercentage = 25.0))
                }
            }
        }
    }
}
