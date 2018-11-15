package com.kubiakdev.safely.ui.icon

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.icon.adapter.AdapterListener
import com.kubiakdev.safely.ui.icon.adapter.IconAdapter
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.delegate.DefaultArgumentsDelegate
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_icon.*

class IconFragment : BaseFragment(), IconView, AdapterListener {

    override val layoutId: Int = R.layout.fragment_icon

    private var NavController.argIconResId by DefaultArgumentsDelegate.Int(
            Const.EXTRA_TEMPLATE_ICON_RES_ID
    )

    private val adapter: IconAdapter by lazy {
        IconAdapter(Const.DEFAULT_ICON_LIST, this)
                .apply { adapterListener = this@IconFragment }
    }

    override fun initActivityComponents() {
        activity.run {
            bar_frame?.visibility = View.GONE
            fab_frame?.setOnClickListener {
                findNavController().popBackStack()
            }
            v_shadow_frame?.visibility
        }
    }

    override fun initComponents() {
        rv_icon.run {
            layoutManager = StaggeredGridLayoutManager(5, 1)
            adapter = this@IconFragment.adapter.apply {
                adapterListener = this@IconFragment
            }.also { it.notifyDataSetChanged() }
        }
    }

    override fun onIconClicked(iconResId: Int) {
        findNavController().run {
            argIconResId = iconResId
            popBackStack()
        }
    }
}