package com.kubiakdev.safely.ui.detail

import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.detail.adapter.AdapterListener
import com.kubiakdev.safely.ui.detail.adapter.DetailAdapter
import com.kubiakdev.safely.ui.template.TemplateViewModel
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.withViewModel
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : BaseFragment(), DetailView, AdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_detail

    override var menuResId: Int? = R.menu.menu_detail

    override var isInEditMode: Boolean? = null

    private val detailViewModel by lazy {
        getViewModel<DetailViewModel>(viewModelFactory)
    }

    private val templateViewModel by lazy {
        getViewModel<TemplateViewModel>(viewModelFactory)
    }

    private lateinit var callback: SimpleItemTouchHelperCallback

    private lateinit var adapter: DetailAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var dataList: List<DetailModel>

    override fun initActivityComponents() {
        activity.run {
            bar_frame?.replaceMenu(R.menu.menu_detail)
            fab_frame?.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initComponents() {
        withViewModel(detailViewModel) {
            observe(isInDeleteMode, ::trySwitchDeleteMode)
            observe(isInEditMode, ::trySwitchEditMode)
        }

        adapter = DetailAdapter(mutableListOf(), this, false)
                .apply { adapterListener = this@DetailFragment }
                .also {
                    detailViewModel.getData { list ->
                        adapter.list = list.toMutableList()
                        dataList = list.toMutableList()
                        activity.hideProgressBar()
                        rv_detail.adapter?.notifyDataSetChanged()
                    }
                    activity.showProgressBar()
                }


        callback = SimpleItemTouchHelperCallback(
                adapter,
                getViewModel<DetailViewModel>(viewModelFactory).isInDeleteMode.value
                        ?: false
        ) { detailViewModel.updateDatabase(adapter.list) }

        itemTouchHelper = ItemTouchHelper(callback).also { it.attachToRecyclerView(rv_detail) }

        rv_detail.run {
            layoutManager = StaggeredGridLayoutManager(2, 1)
            adapter = this@DetailFragment.adapter.apply {
                adapterListener = this@DetailFragment
            }.also { it.notifyDataSetChanged() }
        }

        isInEditMode = detailViewModel.isInDeleteMode.value ?: false

        findNavController().run {
            templateViewModel.run {
                if (newTemplateIconResId.isNotEqualDefValue() &&
                        newTemplateKey.isNotEqualDefValue()) {
                    updateAdapterList()
                    newTemplateIconResId.reset()
                    newTemplateKey.reset()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_detail_add -> {
                findNavController().navigate(R.id.action_detailFragment_to_templateFragment)
            }
            R.id.action_detail_edit -> {
                getViewModel<DetailViewModel>(viewModelFactory).run {
                    if (isInEditMode.value != true) {
                        isInEditMode.postValue(true)
                        isInDeleteMode.postValue(false)

                    } else {
                        isInEditMode.postValue(false)
                        activity.dismissSnackBar()
                    }
                }
                isInEditMode = !(isInEditMode ?: true)
            }
            R.id.action_detail_delete -> {
                getViewModel<DetailViewModel>(viewModelFactory).run {
                    if (isInDeleteMode.value != true) {
                        isInDeleteMode.postValue(true)
                        isInEditMode.postValue(false)
                    } else {
                        isInDeleteMode.postValue(false)
                        activity.dismissSnackBar()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun tryUpdateAdapterList(list: List<DetailModel>?) {
        val shouldReloadAdapter = adapter.list.size != list?.size
        adapter.list = list?.toMutableList() ?: mutableListOf()
        activity.hideProgressBar()
        if (shouldReloadAdapter) {
            rv_detail?.adapter?.notifyDataSetChanged()
        }
    }

    override fun addDetail(model: DetailModel) {
        findNavController().run {
            detailViewModel.run {
                newDetailIconResId.value = model.iconResId
                newDetailKey.value = model.key
            }
            popBackStack()
        }
    }

    override fun addTemplate(model: DetailModel) {}


    override fun onEditTemplate(currentModel: DetailModel, index: Int) {
        findNavController().run {
            detailViewModel.run {
                editedDetailIndex.value = index
                editedDetailIconResId.value = currentModel.iconResId
                editedDetailKey.value = currentModel.key
            }
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

    private fun updateAdapterList() {
        val newModel = DetailModel(
                templateViewModel.newTemplateIconResId.value ?: R.drawable.ic_image,
                templateViewModel.newTemplateKey.value ?: ""
        )
        adapter.list.add(newModel)
        detailViewModel.updateDatabase(adapter.list)
    }

    private fun trySwitchEditMode(shouldLaunchEditMode: Boolean?) {
        if (adapter.list.isNotEmpty()) {
            switchEditMode(shouldLaunchEditMode)
        }
    }

    private fun switchEditMode(shouldLaunchEditMode: Boolean?) {
        adapter.isInEditMode = shouldLaunchEditMode ?: false
        setMenuEditIcon(shouldLaunchEditMode ?: false)

    }

    private fun trySwitchDeleteMode(shouldLaunchDeleteMode: Boolean?) {
        if (adapter.list.isNotEmpty()) {
            switchDeleteMode(shouldLaunchDeleteMode)
        }
    }

    private fun switchDeleteMode(shouldLaunchDeleteMode: Boolean?) {
        callback.isInDeleteMode = shouldLaunchDeleteMode ?: false
        setMenuDeleteIcon(shouldLaunchDeleteMode ?: false)
    }

    private fun setMenuDeleteIcon(isInDeleteMode: Boolean) {
        menu?.get(0)?.icon = if (isInDeleteMode) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_on).also {
                showSnackBar(R.string.all_delete_on)
            }
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_off)
        }
    }

    private fun setMenuEditIcon(isInEditMode: Boolean) {
        menu?.get(1)?.icon = if (isInEditMode) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_edit_on).also {
                showSnackBar(R.string.all_edit_on)
            }
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_edit_off)
        }
    }
}