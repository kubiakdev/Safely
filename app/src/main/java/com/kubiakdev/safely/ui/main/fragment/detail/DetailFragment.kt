package com.kubiakdev.safely.ui.main.fragment.detail

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.mvp.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.fragment.detail.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.detail.adapter.DetailAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment<DetailPresenter>(), AdapterListener {

    override val layoutId: Int = R.layout.fragment_detail

    private val detailAdapter: DetailAdapter by lazy {
        DetailAdapter(mutableListOf())
                .apply { listener = this@DetailFragment }
                .also { adapter ->
            presenter.detailList
                    .subscribe { list ->
                        adapter.list = if (list.isNotEmpty()) {
                            list.toMutableList()
                        } else {
                            MainValues.DETAIL_DEFAULT_TEMPLATE_LIST
                        }
                        rv_detail?.adapter?.notifyDataSetChanged()
                    }
        }
    }

    override fun initComponents() {
        rv_detail.run {
            layoutManager = StaggeredGridLayoutManager(2, 1)
            adapter = detailAdapter
        }

        activity?.bar_main?.replaceMenu(R.menu.menu_detail)
        activity?.fab_main?.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_passwordFragment)
        }
    }

    override fun addDetail(model: DetailModel) {
        findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToPasswordFragment().apply {
                    setIconResId(model.iconResId)
                    setKeyResId(model.keyResId)
                }
        )
    }

    override fun addTemplate(model: DetailModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}