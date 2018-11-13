package com.kubiakdev.safely.ui.main.fragment.detail

import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.activity.MainActivity
import com.kubiakdev.safely.ui.main.fragment.detail.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.detail.adapter.DetailAdapter
import com.kubiakdev.safely.util.delegate.DefaultArgumentsDelegate
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.withViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : BaseFragment(), DetailView, AdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_detail

    override var isInEditMode: Boolean? = null

    private val viewModel: DetailViewModel by lazy {
        getViewModel<DetailViewModel>(viewModelFactory)
    }

    private var NavController.detailIconResId by DefaultArgumentsDelegate.Int(
            MainValues.EXTRA_DETAIL_ICON_RES_ID
    )

    private var NavController.detailKey by DefaultArgumentsDelegate.String(
            MainValues.EXTRA_DETAIL_KEY
    )

    private var NavController.templateIconResId by DefaultArgumentsDelegate.Int(
            MainValues.EXTRA_TEMPLATE_ICON_RES_ID
    )

    private var NavController.templateKey by DefaultArgumentsDelegate.String(
            MainValues.EXTRA_TEMPLATE_KEY
    )

    private var NavController.detailEditedItemIndex by DefaultArgumentsDelegate.Int(
            MainValues.EXTRA_DETAIL_EDITED_ITEM_INDEX
    )

    private lateinit var callback: SimpleItemTouchHelperCallback

    private lateinit var detailAdapter: DetailAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var dataList: List<DetailModel>

    override fun initActivityComponents() {
        activity?.run {
            bar_main?.replaceMenu(R.menu.menu_detail)
            fab_main?.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initComponents() {
        withViewModel(viewModel) {
            observe(isInDeleteMode, ::trySwitchDeleteMode)
            observe(isInEditMode, ::trySwitchEditMode)
        }

        detailAdapter = DetailAdapter(mutableListOf(), this, false)
                .apply { adapterListener = this@DetailFragment }
                .also {
                    getViewModel<DetailViewModel>(viewModelFactory).getData { list ->
                        detailAdapter.list = list.toMutableList()
                        dataList = list.toMutableList()
                        (activity as MainActivity).hideProgressBar()
                        rv_detail.adapter?.notifyDataSetChanged()
                    }
                    (activity as MainActivity).showProgressBar()
                }


        callback = SimpleItemTouchHelperCallback(
                detailAdapter,
                getViewModel<DetailViewModel>(viewModelFactory).isInDeleteMode.value
                        ?: false
        ) { getViewModel<DetailViewModel>(viewModelFactory).updateDatabase(detailAdapter.list) }

        itemTouchHelper = ItemTouchHelper(callback).also { it.attachToRecyclerView(rv_detail) }

        rv_detail.run {
            layoutManager = StaggeredGridLayoutManager(2, 1)
            detailAdapter.adapterListener = this@DetailFragment
            adapter = detailAdapter
            detailAdapter.notifyDataSetChanged()
        }

        isInEditMode = viewModel.isInDeleteMode.value ?: false

        findNavController().run {
            if (detailIconResId != -1 && detailKey != "") {
                updateAdapterList(this)
                detailIconResId = -1
                detailKey = ""
            }
        }

        hideFullFragment()
    }


    override fun doOnMenuActionClick(action: String, value: Boolean) {
        when (action) {
            MainValues.ACTION_DETAIL_ADD -> {
                findNavController().navigate(R.id.action_detailFragment_to_templateFragment)
            }
            MainValues.ACTION_DETAIL_DELETE -> {
                getViewModel<DetailViewModel>(viewModelFactory).run {
                    if (isInDeleteMode.value != true) {
                        isInDeleteMode.postValue(true)
                        isInEditMode.postValue(false)
                    } else {
                        isInDeleteMode.postValue(false)
                        (activity as MainActivity).dismissSnackBar()
                    }
                }
            }
            MainValues.ACTION_DETAIL_EDIT -> {
                getViewModel<DetailViewModel>(viewModelFactory).run {
                    if (isInEditMode.value != true) {
                        isInEditMode.postValue(true)
                        isInDeleteMode.postValue(false)

                    } else {
                        isInEditMode.postValue(false)
                        (activity as MainActivity).dismissSnackBar()
                    }
                }
                isInEditMode = !(isInEditMode ?: true)
            }
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun tryUpdateAdapterList(list: List<DetailModel>?) {
        val shouldReloadAdapter = detailAdapter.list.size != list?.size
        detailAdapter.list = list?.toMutableList() ?: mutableListOf()
        (activity as MainActivity).hideProgressBar()
        if (shouldReloadAdapter) {
            rv_detail?.adapter?.notifyDataSetChanged()
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

    override fun addTemplate(model: DetailModel) {}


    override fun onEditTemplate(currentModel: DetailModel, index: Int) {
        findNavController().run {
            templateIconResId = currentModel.iconResId
            templateKey = currentModel.key
            detailEditedItemIndex = index
            navigate(R.id.action_detailFragment_to_templateFragment)
        }
    }

    override fun switchOffDeleteMode() {
        getViewModel<DetailViewModel>(viewModelFactory).isInDeleteMode.postValue(false)
        switchDeleteMode(false)
    }

    override fun switchOffEditMode() {
        getViewModel<DetailViewModel>(viewModelFactory).isInEditMode.postValue(false)
        switchEditMode(false)
    }

    private fun updateAdapterList(navController: NavController) {
        val newModel = DetailModel(navController.detailIconResId, navController.detailKey)
        detailAdapter.list.add(newModel)
        viewModel.updateDatabase(detailAdapter.list)
    }

    private fun trySwitchEditMode(shouldLaunchEditMode: Boolean?) {
        if (detailAdapter.list.isNotEmpty()) {
            switchEditMode(shouldLaunchEditMode)
        }
    }

    private fun switchEditMode(shouldLaunchEditMode: Boolean?) {
        detailAdapter.isInEditMode = shouldLaunchEditMode ?: false
        setMenuEditIcon(shouldLaunchEditMode ?: false)

    }

    private fun trySwitchDeleteMode(shouldLaunchDeleteMode: Boolean?) {
        if (detailAdapter.list.isNotEmpty()) {
            switchDeleteMode(shouldLaunchDeleteMode)
        }
    }

    private fun switchDeleteMode(shouldLaunchDeleteMode: Boolean?) {
        callback.isInDeleteMode = shouldLaunchDeleteMode ?: false
        setMenuDeleteIcon(shouldLaunchDeleteMode ?: false)
    }

    private fun setMenuDeleteIcon(isInDeleteMode: Boolean) {
        (activity as MainActivity).run {
            menu?.get(0)?.icon = if (isInDeleteMode) {
                ContextCompat.getDrawable(this, R.drawable.ic_delete_on).also {
                    showSnackBar(R.string.all_delete_on)
                }
            } else {
                ContextCompat.getDrawable(this, R.drawable.ic_delete_off)
            }
        }
    }

    private fun setMenuEditIcon(isInEditMode: Boolean) {
        (activity as MainActivity).run {
            menu?.get(1)?.icon = if (isInEditMode) {
                ContextCompat.getDrawable(this, R.drawable.ic_edit_on).also {
                    showSnackBar(R.string.all_edit_on)
                }
            } else {
                ContextCompat.getDrawable(this, R.drawable.ic_edit_off)
            }
        }
    }
}