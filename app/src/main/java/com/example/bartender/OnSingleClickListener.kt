package com.example.bartender

import android.os.SystemClock
import android.view.View

/**
 * 一个用于防止短时间内重复点击的监听器封装类。
 *
 * @param onSingleClick 实际的点击事件回调，只会在一秒钟后触发一次。
 */
class OnSingleClickListener(private val onSingleClick: (View) -> Unit) : View.OnClickListener {

    // 上次点击的时间戳，使用 volatile 关键字确保多线程下的可见性
    @Volatile
    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = SystemClock.elapsedRealtime()
        val delta = currentTime - lastClickTime

        // 如果两次点击的时间间隔大于一秒（1000毫秒），则执行点击事件
        if (delta > 1000) {
            lastClickTime = currentTime
            onSingleClick(v)
        }
    }
}

/**
 * View 的扩展函数，提供一个防抖的点击事件监听器。
 *
 * @param onSingleClick 实际的点击事件回调。
 */
fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    this.setOnClickListener(OnSingleClickListener(onSingleClick))
}

/**
 * View 的扩展函数，允许传入一个自定义的时间间隔来防抖。
 *
 * @param intervalMillis 自定义的时间间隔（毫秒）。
 * @param onSingleClick 实际的点击事件回调。
 */
fun View.setOnSingleClickListener(intervalMillis: Long, onSingleClick: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        @Volatile
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            val currentTime = SystemClock.elapsedRealtime()
            val delta = currentTime - lastClickTime

            if (delta > intervalMillis) {
                lastClickTime = currentTime
                onSingleClick(v)
            }
        }
    })
}