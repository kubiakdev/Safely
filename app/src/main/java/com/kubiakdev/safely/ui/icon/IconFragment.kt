package com.kubiakdev.safely.ui.icon

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.icon.adapter.AdapterListener
import com.kubiakdev.safely.ui.icon.adapter.IconAdapter
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.extension.getViewModel
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_icon.*
import javax.inject.Inject

class IconFragment : BaseFragment(), IconView, AdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_icon

    override val menuResId: Int? = null

    private val adapter: IconAdapter by lazy {
        IconAdapter(Const.DEFAULT_ICON_LIST, this)
                .apply { adapterListener = this@IconFragment }
    }

    private val iconViewModel by lazy {
        getViewModel<IconViewModel>(viewModelFactory)
    }

    override fun initActivityComponents() {
        activity.run {
            fab_frame?.setOnClickListener {
                findNavController().popBackStack()
            }
            v_shadow_frame?.visibility
        }
    }

    override fun initComponents() {
        rv_icon.run {
            layoutManager = StaggeredGridLayoutManager(3, 1)
            adapter = this@IconFragment.adapter.apply {
                adapterListener = this@IconFragment
            }.also { it.notifyDataSetChanged() }
        }
    }

    override fun onIconClicked(iconResId: Int) {
        iconViewModel.iconResId.value = iconResId
        findNavController().popBackStack()
    }
}