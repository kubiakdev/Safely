package com.kubiakdev.safely.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.ui.main.activity.MainActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment :
        DaggerFragment(),
        BaseView,
        FragmentListener,
        OnStartDragListener {

    abstract val layoutId: Int

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).fragmentListener = this
        initActivityComponents()
        initComponents()
    }

    protected open fun inflateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)


    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {}

    override fun doOnMenuActionClick(action: String, value: Boolean) {}

    protected open fun initActivityComponents() {}

    protected open fun initComponents() {}

    fun showToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }

    fun showSnackBar(@StringRes stringRes: Int) {
        if (activity is MainActivity) {
            (activity as MainActivity).showSnackBar(stringRes)
        }
    }

    fun hideFullFragment() = switchBarAndFabVisibility(true).also {
        changeFragmentBottomMargin(false)
    }

    fun showFullFragment() = switchBarAndFabVisibility(false).also {
        changeFragmentBottomMargin(true)
    }

    private fun switchBarAndFabVisibility(shouldBeVisible: Boolean) {
        if (activity is MainActivity) {
            activity?.run {
                changeVisibility(shouldBeVisible, bar_main, fab_main, v_shadow_main)
            }
        }
    }

    private fun changeVisibility(shouldBeVisible: Boolean, vararg views: View) {
        views.forEach {
            it.visibility = if (shouldBeVisible && it.visibility != View.VISIBLE) {
                View.VISIBLE
            } else if (!shouldBeVisible && it.visibility != View.GONE) {
                View.GONE
            } else {
                it.visibility
            }
        }

    }

    private fun changeFragmentBottomMargin(shouldBeZero: Boolean) {
        val barHeight = resources.getDimension(R.dimen.height_bar).toInt()
        (activity?.nav_host_main?.view?.layoutParams as ViewGroup.MarginLayoutParams).run {
            bottomMargin = if (shouldBeZero && bottomMargin != 0) {
                0
            } else if (!shouldBeZero && bottomMargin != barHeight) {
                barHeight
            } else {
                bottomMargin
            }
        }

    }

}