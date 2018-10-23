package com.kubiakdev.safely.ui.main.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.activity.MainActivity
import com.kubiakdev.safely.ui.main.fragment.detail.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.detail.adapter.DetailAdapter
import com.kubiakdev.safely.util.DefaultArgumentsDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment<DetailPresenter>(), DetailView, AdapterListener {

    override val layoutId: Int = R.layout.fragment_detail

    private var NavController.argIconResId by DefaultArgumentsDelegate.Int(
            MainValues.EXTRA_DETAIL_ICON_RES_ID
    )

    private var NavController.argKey by DefaultArgumentsDelegate.String(
            MainValues.EXTRA_DETAIL_KEY
    )

    private val detailAdapter: DetailAdapter by lazy {
        DetailAdapter(mutableListOf(), this)
                .apply { adapterListener = this@DetailFragment }
                .also { _ ->
                    run {
                        (activity as MainActivity).showProgressBar()
                        presenter.loadDataToAdapter()
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().run {
                        if (argIconResId != -1 && argKey != "") {
                updateAdapterList(this)
            }
        }
    }

    override fun updateAdapterList(list: List<DetailModel>) {
        (activity as MainActivity).hideProgressBar()
        detailAdapter.list = list.toMutableList()
        rv_detail?.adapter?.notifyDataSetChanged()
    }

    override fun onAttach() {
        presenter.view = this
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
        hideFullFragment()
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

    override fun addTemplate(model: DetailModel) {}

    private fun updateAdapterList(navController: NavController) {
        detailAdapter.list.add(
                element = DetailModel(navController.argIconResId, navController.argKey)
        )
        rv_detail.adapter?.notifyDataSetChanged()
    }
}