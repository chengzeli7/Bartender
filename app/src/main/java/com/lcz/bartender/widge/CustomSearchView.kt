package com.lcz.bartender.widge // 建议的包名

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lcz.bartender.databinding.CustomSearchViewBinding

/**
 * 一个带有左侧返回按钮的自定义 SearchView。
 * 可以在 XML 布局中直接使用，并提供设置返回按钮点击事件和搜索查询监听器的方法。
 */
class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // 绑定自定义布局
    private val binding: CustomSearchViewBinding =
        CustomSearchViewBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * 设置返回按钮的点击监听器。
     * @param listener 返回按钮的点击事件回调。
     */
    fun setOnBackClickListener(listener: OnClickListener?) {
        binding.backButton.setOnClickListener(listener)
    }

    /**
     * 设置 SearchView 的查询文本监听器。
     * @param listener SearchView 的查询文本监听器回调。
     */
    fun setOnQueryTextListener(listener: SearchView.OnQueryTextListener?) {
        binding.customSearchView.setOnQueryTextListener(listener)
    }

    /**
     * 设置 SearchView 的查询提示文本。
     * @param hint 提示文本字符串。
     */
    fun setQueryHint(hint: CharSequence?) {
        binding.customSearchView.queryHint = hint
    }

    /**
     * 获取 SearchView 实例，以便进行更高级的配置（如设置初始查询）。
     * @return 内部的 SearchView 实例。
     */
    fun getSearchView(): SearchView {
        return binding.customSearchView
    }
}
