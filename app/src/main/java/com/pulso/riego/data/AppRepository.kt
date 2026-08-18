package com.pulso.riego.data

class AppRepository(private val dao: AppDao) {
    suspend fun login(username: String, password: String): User? {
        val u = dao.getUser(username)
        return if (u != null && u.password == password) u else null
    }

    fun allLots() = dao.getAllLots()
    fun pulsesForLot(lotId: Long) = dao.pulsesForLot(lotId)
    suspend fun addPulse(pulse: Pulse) = dao.insertPulse(pulse)
}
