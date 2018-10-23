package com.kubiakdev.safely.ui.main.fragment.icon

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.fragment.icon.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.icon.adapter.IconAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_icon.*

class IconFragment : BaseFragment<IconPresenter>(), IconView, AdapterListener {

    override val layoutId: Int = R.layout.fragment_icon

    private val iconAdapter: IconAdapter by lazy {
        IconAdapter(MainValues.DEFAULT_ICON_LIST, this)
                .apply { adapterListener = this@IconFragment }
    }

    override fun onAttach() {
        presenter.view = this
    }

    override fun initComponents() {
        hideFullFragment()
        rv_icon.run {
            layoutManager = StaggeredGridLayoutManager(5, 1)
            adapter = iconAdapter
        }

        activity?.bar_main?.visibility = View.GONE
        activity?.fab_main?.setOnClickListener {
            findNavController().popBackStack()
        }
        activity?.v_shadow_main?.visibility

        ib_icon_arrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onIconClicked(iconResId: Int) {
        findNavController().run {
            graph.defaultArguments.run {
                putInt(MainValues.EXTRA_ICON_RES_ID, iconResId)
            }
            popBackStack()
        }
    }
}