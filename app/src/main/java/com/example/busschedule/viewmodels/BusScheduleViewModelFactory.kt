package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.schedule.ScheduleDao

/**
 * ViewModel クラス BusScheduleViewModel はライフサイクル対応であるため、
 * ライフサイクル イベントに応答できるオブジェクトによってインスタンス化する必要があります。
 * フラグメントのいずれかで直接インスタンス化する場合、
 * フラグメント オブジェクトがすべてのメモリ管理を含めて何もかも処理する必要がありますが、
 * これはアプリのコードで行うべきことの範囲を超えています。
 * 代わりに、ビューモデル オブジェクトをインスタンス化する、ファクトリーというクラスを作成できます。
 */
class BusScheduleViewModelFactory(private val scheduleDao: ScheduleDao) :
    ViewModelProvider.Factory {
    /**
     * クラスを直接初期化するのではなく、エラーチェックを行って
     * BusScheduleViewModelFactory を返す create() というメソッドをオーバーライドします。
     * これで、BusScheduleViewModelFactory.create() を使用して
     * BusScheduleViewModelFactory オブジェクトをインスタンス化し、
     * フラグメントで直接処理しなくてもビューモデルをライフサイクル対応にできるようになりました。
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}