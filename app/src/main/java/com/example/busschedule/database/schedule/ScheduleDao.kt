package com.example.busschedule.database.schedule

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO で関数を呼び出すことは、データベースで SQL コマンドを実行することと同じです
 */
@Dao
interface ScheduleDao {
    /**
     * 昇順 > ASC / ascending order
     * 降順 > DESC / descending order
     */
    @Query("SELECT * FROM schedule ORDER BY arrival_time ASC")
    fun getAll(): Flow<List<Schedule>>

    /**
     * クエリの前にコロン（:）を付けると（例: 関数パラメータの :stopName）、Kotlin 値を参照できます
     */
    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
    fun getByStopName(stopName: String): Flow<List<Schedule>>
}