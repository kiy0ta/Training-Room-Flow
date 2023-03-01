package com.example.busschedule

import android.app.Application
import com.example.busschedule.database.AppDatabase

/**
 * Application クラスのカスタム サブクラスを用意して、
 * getDatabase() の結果を保持する lazy プロパティを作成する必要があります
 */
// これ何してるの？
class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}