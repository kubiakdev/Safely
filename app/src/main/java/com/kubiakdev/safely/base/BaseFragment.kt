package com.kubiakdev.safely.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.ui.activity.FrameActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_frame.*

abstract class BaseFragment :
        DaggerFragment(),
        BaseView,
        FragmentListener,
        OnStartDragListener {

    abstract val layoutId: Int

    protected val activity by lazy { this.getActivity() as FrameActivity }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.fragmentListener = this
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

    fun showSnackBar(@StringRes stringRes: Int) {
        activity.showSnackBar(stringRes)
    }

    fun hideFullFragment() = switchBarAndFabVisibility(true).also {
        changeFragmentBottomMargin(false)
    }

    fun showFullFragment() = switchBarAndFabVisibility(false).also {
        changeFragmentBottomMargin(true)
    }

    private fun switchBarAndFabVisibility(shouldBeVisible: Boolean) {
        activity.run {
            changeVisibility(shouldBeVisible, bar_frame, fab_frame, v_shadow_frame)
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
        (activity.nav_host_frame?.view?.layoutParams as ViewGroup.MarginLayoutParams).run {
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