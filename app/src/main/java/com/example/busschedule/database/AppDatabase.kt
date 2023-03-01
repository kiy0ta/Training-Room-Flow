package com.example.busschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

/**
 * データベースで定義されているエンティティを指定する
 * 各 DAO クラスのインスタンスの 1 つにアクセスできるようにする
 * データベースの事前入力など、追加のセットアップを行う
 */
@Database(entities = [Schedule::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * データベース クラスを使用すると、他のクラスが DAO クラスに簡単にアクセスできます。
     * ScheduleDao を返す抽象関数を追加します。
     */
    abstract fun scheduleDao(): ScheduleDao

    /**
     * AppDatabase クラスを使用する際、競合状態やその他の起こり得る問題を回避するために、
     * データベースのインスタンスが 1 つしか存在しないようする必要があります。
     */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // これなんでつけないと動かないの？
        // なんでAppDatabase::class.javaでjavaを参照するの？
        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): AppDatabase {
            // synchronized >  internal in coroutines API
            // データベースの既存のインスタンスを返すか（すでに存在する場合）、必要に応じてデータベースを新規作成します。
            // このアプリでは、データが事前入力されているため、createFromAsset() を呼び出して既存のデータを読み込むこともできます。
            // database/bus_schedule.db ファイルは、プロジェクトの assets.database パッケージにあります。
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).createFromAsset("database/bus_schedule.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}