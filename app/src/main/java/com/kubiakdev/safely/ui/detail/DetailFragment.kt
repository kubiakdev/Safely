package com.kubiakdev.safely.ui.detail

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.mapper.itemToEntity
import com.kubiakdev.safely.data.mapper.modelToItem
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.ui.detail.adapter.DetailAdapter
import com.kubiakdev.safely.ui.detail.adapter.DetailAdapterListener
import com.kubiakdev.safely.ui.detailedition.DetailEditionSharedViewModel
import com.kubiakdev.safely.ui.password.PasswordSharedViewModel
import com.kubiakdev.safely.util.extension.getSharedViewModel
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.tint
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*
import javax.inject.Inject

class DetailFragment : BaseFragment(), DetailView, DetailAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_detail

    override val menuResId: Int = R.menu.menu_detail

    override val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(callback).also { it.attachToRecyclerView(rvDetail) }
    }

    private val adapter by lazy {
        DetailAdapter(mutableListOf(), this)
                .also {
                    detailViewModel.getData { list ->
                        it.list.addAll(list.map { item -> modelToItem(item) })
                        activity.hideProgressBar()
                        rvDetail.adapter?.notifyDataSetChanged()
                    }
                    activity.showProgressBar()
                }
    }

    private val callback by lazy {
        SimpleItemTouchHelperCallback(adapter, false)
    }

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var passwordSharedViewModel: PasswordSharedViewModel
    private lateinit var detailSharedViewModel: DetailSharedViewModel
    private lateinit var detailEditionSharedViewModel: DetailEditionSharedViewModel

    override fun initViewModel() {
        detailViewModel = getViewModel(viewModelFactory)
    }

    override fun initActivityComponents() {
        activity.fab?.setOnClickListener { popBackStack() }
    }

    override fun initComponents() {
        rvDetail.apply {
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, ORIENTATION)
            adapter = this@DetailFragment.adapter.apply {
                listener = this@DetailFragment
            }.also { it.notifyDataSetChanged() }
        }
    }

    override fun initSharedViewModels() {
        passwordSharedViewModel = getSharedViewModel(viewModelFactory)
        detailSharedViewModel = getSharedViewModel(viewModelFactory)
        detailEditionSharedViewModel = getSharedViewModel(viewModelFactory) {
            observe(newModel, ::addModel)
            observe(editedModel, ::updateModel)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_detail_add -> launchDetailEditionFragment(null)
            R.id.action_detail_edit -> setEditMode()
            R.id.action_detail_delete -> setDeleteMode()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(position: Int, model: TemplateModel) {
        model.apply {
            when {
                isInEditMode() -> launchDetailEditionFragment(this)
                isInDeleteMode() -> adapter.apply {
                    list.removeAt(position)
                    notifyItemRemoved(position)
                }
                else -> {
                    detailSharedViewModel.newDetailModel.postValue(
                            DetailModel(
                                    id,
                                    iconResId,
                                    key,
                                    EMPTY_VALUE,
                                    false
                            )
                    )
                    popBackStack()
                }
            }
        }
    }

    override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition == 0 || toPosition == 0) return
        adapter.apply {
            detailViewModel.switch(
                    itemToEntity(list[fromPosition]).id,
                    itemToEntity(list[toPosition]).id
            ) {
                Collections.swap(list, fromPosition, toPosition)
                notifyItemMoved(fromPosition, toPosition)
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        detailViewModel.remove((adapter.list[position]).id) {
            position.let {
                adapter.apply {
                    list.removeAt(it)
                    notifyItemRemoved(it)
                }
            }.also {
                if (adapter.list.isEmpty()) {
                    detailSharedViewModel.apply {
                        isInDeleteMode.postValue(false)
                        isInEditMode.postValue(false)
                    }
                }
            }
        }
    }

    private fun launchDetailEditionFragment(model: TemplateModel?) {
        detailSharedViewModel.detailModelToEdit.postValue(model)
        findNavController().navigate(R.id.action_detailFragment_to_detailEditionFragment)
    }

    private fun isInDeleteMode() = menu?.getItem(MENU_DELETE_ACTION_INDEX)?.isChecked ?: false

    private fun isInEditMode() = menu?.getItem(MENU_EDIT_ACTION_INDEX)?.isChecked ?: false

    private fun setDeleteMode() {
        menu?.getItem(MENU_DELETE_ACTION_INDEX)?.apply {
            if (!isChecked && adapter.list.isNotEmpty()) {
                setDeleteModeOn()
            } else {
                setDeleteModeOff()
            }.also {
                callback.isInDeleteMode = isChecked
            }
        }
    }

    private fun setDeleteModeOn() {
        menu?.getItem(MENU_DELETE_ACTION_INDEX)?.apply {
            if (isInEditMode()) {
                setEditModeOff(requestNotChecked = true)
            }
            showSnackbar(R.string.all_delete_mode_on)
            context?.let {
                icon.tint(it, R.color.accent)
            }
            isChecked = true
        }
    }

    private fun setDeleteModeOff(requestNotChecked: Boolean = false) {
        menu?.getItem(MENU_DELETE_ACTION_INDEX)?.apply {
            if (!requestNotChecked) {
                dismissSnackbar()
            }
            context?.let {
                icon.tint(it, R.attr.iconColor, true)
            }
            isChecked = false
        }
    }

    private fun setEditMode() {
        menu?.getItem(MENU_EDIT_ACTION_INDEX)?.apply {
            if (!isChecked && adapter.list.isNotEmpty()) {
                setEditModeOn()
            } else {
                setEditModeOff()
            }
        }
    }

    private fun setEditModeOn() {
        menu?.getItem(MENU_EDIT_ACTION_INDEX)?.apply {
            if (isInDeleteMode()) {
                setDeleteModeOff(requestNotChecked = true)
            }
            showSnackbar(R.string.all_edit_mode_on)
            context?.let {
                icon.tint(it, R.color.accent)
            }
            isChecked = true
        }
    }

    private fun setEditModeOff(requestNotChecked: Boolean = false) {
        menu?.getItem(MENU_EDIT_ACTION_INDEX)?.apply {
            if (!requestNotChecked) {
                dismissSnackbar()
            }
            context?.let {
                icon.tint(it, R.attr.iconColor, true)
            }
            isChecked = false
        }
    }

    private fun updateModel(model: TemplateModel?) {
        model?.let {
            adapter.apply {
                list
                        .forEachIndexed { i, m ->
                            if (m.id == it.id) {
                                (list[i]).apply {
                                    iconResId = it.iconResId
                                    key = it.key
                                }
                                adapter.notifyItemChanged(i)
                            }
                        }
            }.notifyDataSetChanged()
        }
    }

    private fun addModel(model: TemplateModel?) {
        model?.let {
            adapter.apply {
                list.add(modelToItem(it))
                notifyItemInserted(list.lastIndex)
            }
        }
    }

    companion object {

        private const val SPAN_COUNT = 2
        private const val ORIENTATION = 1
        private const val MENU_DELETE_ACTION_INDEX = 0
        private const val MENU_EDIT_ACTION_INDEX = 1
        private const val EMPTY_VALUE = ""

    }
}