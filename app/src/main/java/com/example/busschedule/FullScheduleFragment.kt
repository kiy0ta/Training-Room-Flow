/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.databinding.FullScheduleFragmentBinding
import com.example.busschedule.viewmodels.BusScheduleViewModel
import com.example.busschedule.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class FullScheduleFragment : Fragment() {

    /**
     * by viewModels や by activityViewModels の中で、
     * ViewModelProvider経由でViewModelを取得してるので直接インスタンス化するのとは全く別物です。
     *
     * 今回は直接インスタンス化する代わりに、
     * ビューモデル オブジェクトをインスタンス化する、ファクトリーというクラスを実装します。
     *
     * フラグメントで直接ViewModelをインスタンス化する場合、
     * フラグメント オブジェクトがすべてのメモリ管理を含めて何もかも処理する必要がありますが、
     * これはアプリのコードで行うべきことの範囲を超えています。
     */
    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            /**
             * (1) これはBusScheduleApplicationクラスのメンバーフィールドへのアクセスです
             * (activity?.application as BusScheduleApplication).database.scheduleDao()
             *
             * (2) これはクラスのstaticフィールドへのアクセス（元のソースには存在しない）
             * BusScheduleApplication.database.scheduleDao()
             */
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    private var _binding: FullScheduleFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val busStopAdapter = BusStopAdapter {
            val action =
                FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)
        }
        recyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
