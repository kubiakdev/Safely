package com.kubiakdev.safely.ui.main.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
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
        DetailAdapter(mutableListOf(), this)
                .apply { adapterListener = this@DetailFragment }
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

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().graph.defaultArguments.run {
            if (containsKey(MainValues.EXTRA_TEMPLATE_ICON_RES_ID) &&
                    containsKey(MainValues.EXTRA_TEMPLATE_KEY)) {
                updateAdapterList(this)
                remove(MainValues.EXTRA_TEMPLATE_ICON_RES_ID)
                remove(MainValues.EXTRA_TEMPLATE_KEY)
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
            findNavController().popBackStack()
        }
    }

    override fun addDetail(model: DetailModel) {
        findNavController().run {
            graph.defaultArguments.apply {
                putInt(MainValues.EXTRA_DETAIL_ICON_RES_ID, model.iconResId)
                putString(MainValues.EXTRA_DETAIL_KEY, model.key)
            }
            popBackStack()
        }
    }

    override fun addTemplate(model: DetailModel) {
    }

    override fun doOnMenuActionClick(action: String, value: Boolean) {}

    private fun updateAdapterList(bundle: Bundle) {
        val model = DetailModel(
                bundle.getInt(MainValues.EXTRA_TEMPLATE_ICON_RES_ID),
                bundle.getString(MainValues.EXTRA_TEMPLATE_KEY) ?: ""
        )
        detailAdapter.list.add(model)
        rv_detail.adapter?.notifyDataSetChanged()
    }
}