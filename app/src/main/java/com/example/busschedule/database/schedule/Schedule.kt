package com.example.busschedule.database.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * デフォルトでは、Room はクラス名をデータベース テーブル名として使用します。
 * したがって、現時点でクラスで定義されているテーブル名は Schedule になります。
 * 必要に応じて、@Entity(tableName="schedule") を指定することもできますが、
 * Room では大文字と小文字は区別されないため、ここで小文字のテーブル名を明示的に定義する必要はありません。
 */
@Entity
data class Schedule(
    @PrimaryKey
    val id: Int,
    @NotNull
    @ColumnInfo(name = "stop_name")
    val stopName: String,
    @NotNull
    @ColumnInfo(name = "arrival_time")
    val arrivalTime: Int
)