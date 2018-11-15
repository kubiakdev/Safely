package com.kubiakdev.safely.ui.main.fragment.icon

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.fragment.icon.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.icon.adapter.IconAdapter
import com.kubiakdev.safely.util.delegate.DefaultArgumentsDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_icon.*

class IconFragment : BaseFragment(), IconView, AdapterListener {

    override val layoutId: Int = R.layout.fragment_icon

    private var NavController.argIconResId by DefaultArgumentsDelegate.Int(
            MainValues.EXTRA_TEMPLATE_ICON_RES_ID
    )

    private val adapter: IconAdapter by lazy {
        IconAdapter(MainValues.DEFAULT_ICON_LIST, this)
                .apply { adapterListener = this@IconFragment }
    }

    override fun initActivityComponents() {
        activity?.run {
            bar_main?.visibility = View.GONE
            fab_main?.setOnClickListener {
                findNavController().popBackStack()
            }
            v_shadow_main?.visibility
        }
    }

    override fun initComponents() {
        rv_icon.run {
            layoutManager = StaggeredGridLayoutManager(5, 1)
            adapter = this@IconFragment.adapter.apply {
                adapterListener = this@IconFragment
            }.also { it.notifyDataSetChanged() }
        }

        ib_icon_arrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onIconClicked(iconResId: Int) {
        findNavController().run {
            argIconResId = iconResId
            popBackStack()
        }
    }
}