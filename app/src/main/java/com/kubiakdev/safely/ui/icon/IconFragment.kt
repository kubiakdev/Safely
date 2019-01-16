package com.kubiakdev.safely.ui.icon

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.icon.adapter.IconAdapter
import com.kubiakdev.safely.ui.icon.adapter.IconAdapterListener
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.extension.getSharedViewModel
import kotlinx.android.synthetic.main.fragment_icon.*
import javax.inject.Inject

class IconFragment : BaseFragment(), IconAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_icon

    private val adapter: IconAdapter by lazy {
        IconAdapter(Const.DEFAULT_ICON_LIST, this)
    }

    private lateinit var iconSharedViewModel: IconSharedViewModel

    override fun initComponents() {
        rvIcon.apply {
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, ORIENTATION)
            adapter = this@IconFragment.adapter.also {
                it.notifyDataSetChanged()
            }
        }
    }

    override fun initSharedViewModels() {
        iconSharedViewModel = getSharedViewModel(viewModelFactory)
    }

    override fun onIconClicked(iconResId: Int) {
        iconSharedViewModel.selectedIconResId.postValue(iconResId)
        popBackStack()
    }

    companion object {

        private const val SPAN_COUNT = 4
        private const val ORIENTATION = 1

    }

}