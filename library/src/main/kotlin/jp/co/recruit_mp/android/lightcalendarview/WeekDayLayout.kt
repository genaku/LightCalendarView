/*
 * Copyright (C) 2016 RECRUIT MARKETING PARTNERS CO., LTD.
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

package jp.co.recruit_mp.android.lightcalendarview

import android.content.Context
import java.util.*

/**
 * 月カレンダー内の曜日ラベルを表示する {@link ViewGroup}
 * Created by masayuki-recruit on 8/19/16.
 */
class WeekDayLayout(context: Context, settings: CalendarSettings) : CellLayout(context, settings) {
    companion object {
        const val DEFAULT_DAYS_IN_WEEK = 7
    }

    override val rowNum: Int = 1

    override val colNum: Int = DEFAULT_DAYS_IN_WEEK

    var dayOfWeekOffset: Int = -1

    init {
        updateLayout()
    }

    private val observer = Observer { observable, any ->
        updateLayout()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        settings.addObserver(observer)
    }

    override fun onDetachedFromWindow() {
        settings.deleteObserver(observer)
        super.onDetachedFromWindow()
    }

    private fun updateLayout() {
        if (dayOfWeekOffset != settings.dayOfWeekOffset) {
            dayOfWeekOffset = settings.dayOfWeekOffset

            // remove all children
            removeAllViews()

            // populate children
            populateViews()
        }
    }

    private fun populateViews() {
        // add WeekDayViews in 7x1 grid
        WeekDay.getPermutation(dayOfWeekOffset).forEach { weekDay ->
            addView(WeekDayView(context, settings, weekDay))
        }
    }
}
