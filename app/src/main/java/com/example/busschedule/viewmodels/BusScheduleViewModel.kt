package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel クラスは、アプリの UI に関連するデータを格納するために使用されます。
 * また、このクラスはライフサイクル対応であり、
 * アクティビティまたはフラグメントと同様にライフサイクル イベントに応答します。
 * 画面の回転などのライフサイクル イベントによって、
 * アクティビティまたはフラグメントが破棄されて再作成される場合、
 * 関連する ViewModel を再作成する必要はありません。
 * DAO クラスに直接アクセスする方法では不可能であるため、
 * ViewModel サブクラスを使用して、データを読み込む責任を
 * アクティビティまたはフラグメントから分離することをおすすめします。
 * 大規模なアプリでは、フラグメントごとに別々のビューモデルを使用することをおすすめします。
 */
class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {
    fun fullSchedule(): Flow<List<Schedule>> = scheduleDao.getAll()
    fun scheduleForStopName(stopName: String): Flow<List<Schedule>> =
        scheduleDao.getByStopName(stopName = stopName)
}