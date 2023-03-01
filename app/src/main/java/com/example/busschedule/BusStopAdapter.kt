package com.example.busschedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.Date

/**
 * RecyclerView を使用するときは RecyclerView.Adapter を使用して、
 * データの静的リストを表示していました。
 * これは Bus Schedule などのアプリでは問題なく動作しますが、
 * データベースを扱う場合、通常はリアルタイムでデータの変更を処理します。
 * 1 つの項目の内容が変更されただけの場合でも、リサイクラー ビュー全体が更新されます。
 * これは、永続性を使用する大半のアプリでは不十分です。
 *
 * ListAdapter は AsyncListDiffer を使用して、
 * 古いデータリストと新しいデータリストの違いを特定します。
 * 次に、リサイクラー ビューは 2 つのリストの違いのみに基づいて更新されます。
 * その結果、データベース アプリでよくあるように、
 * 頻繁に更新されるデータを処理する場合にリサイクラー ビューのパフォーマンスが向上します。
 *
 */
class BusStopAdapter(private val onItemClicked: (Schedule) -> Unit) :
    ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    // これはどうしてcompanion objectに定義するのか？
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            /**
             * areItemsTheSame() は、ID のみを確認することで、
             * オブジェクト（この場合はデータベースの行）が同じかどうかを確認します。
             */
            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            /**
             * areContentsTheSame() は、ID だけでなくすべてのプロパティが同じかどうかを確認します。
             */
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * onCreateViewHolder() をオーバーライドして実装し、
     * レイアウトをインフレートして、
     * 現在の位置にある項目の onItemClicked() を呼び出すように onClickListener() を設定します。
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    /**
     * onBindViewHolder() をオーバーライドして実装し、指定した位置にビューをバインドします。
     */
    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BusStopViewHolder(private var binding: BusStopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text =
                SimpleDateFormat("h:mm a").format(Date(schedule.arrivalTime.toLong() * 1000))
        }
    }
}