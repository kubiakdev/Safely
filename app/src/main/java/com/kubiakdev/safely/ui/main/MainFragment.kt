package com.kubiakdev.safely.ui.main

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.mapper.itemToEntity
import com.kubiakdev.safely.data.mapper.modelToItem
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.ui.main.adapter.MainAdapter
import com.kubiakdev.safely.ui.main.adapter.MainAdapterListener
import com.kubiakdev.safely.ui.main.adapter.item.PasswordItem
import com.kubiakdev.safely.ui.password.PasswordSharedViewModel
import com.kubiakdev.safely.util.extension.getSharedViewModel
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.tint
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import javax.inject.Inject

class MainFragment : BaseFragment(), MainView, MainAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_main

    override val menuResId: Int = R.menu.menu_main

    override val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(callback).also { it.attachToRecyclerView(rvMain) }
    }

    private val adapter by lazy {
        MainAdapter(mutableListOf(), this)
                .apply { listener = this@MainFragment }
                .also {
                    mainViewModel.getPasswords { list ->
                        it.list.addAll(list.map { item -> modelToItem(item) })
                        activity.hideProgressBar()
                        rvMain.adapter?.notifyDataSetChanged()
                    }
                    activity.showProgressBar()
                }
    }

    private val callback by lazy {
        SimpleItemTouchHelperCallback(adapter, false)
    }

    private val onDeleteDialog: MaterialDialog? by lazy {
        context?.let {
            MaterialDialog(it)
                    .message(R.string.dialog_delete_password_question)
                    .negativeButton(R.string.all_no, null, null)
        }
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainSharedViewModel: MainSharedViewModel
    private lateinit var passwordSharedViewModel: PasswordSharedViewModel

    override fun initViewModel() {
        mainViewModel = getViewModel(viewModelFactory)
    }

    override fun initSharedViewModels() {
        mainSharedViewModel = getSharedViewModel(viewModelFactory)
        passwordSharedViewModel = getSharedViewModel(viewModelFactory) {
            observe(newPasswordModel, ::addPasswordToList)
            observe(editedPasswordModel, ::editPasswordInList)
        }
    }

    override fun initActivityComponents() {
        activity.apply {
            bottomAppBar?.apply {
                setNavigationIcon(R.drawable.ic_menu)
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }

            fab?.apply {
                setImageResource(R.drawable.ic_add)
                setOnClickListener {
                    launchPasswordFragment(null)
                }
            }
        }
    }

    override fun initComponents() {
        rvMain.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainFragment.adapter.apply {
                listener = this@MainFragment
            }.also { it.notifyDataSetChanged() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_main_delete -> setDeleteMode()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(position: Int) {
        launchPasswordFragment(adapter.list[position])
    }

    override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition == 0 || toPosition == 0) return
        adapter.apply {
            mainViewModel.switch(
                    itemToEntity(list[fromPosition]).id,
                    itemToEntity(list[toPosition]).id
            ) {
                Collections.swap(list, fromPosition, toPosition)
                notifyItemMoved(fromPosition, toPosition)
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        onDeleteDialog
                ?.positiveButton(R.string.all_yes) {
                    adapter.apply {
                        mainViewModel.remove((list[position]).id) {
                            list.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                }
                ?.onDismiss { adapter.notifyItemChanged(position) }
                ?.show()

    }

    private fun launchPasswordFragment(item: PasswordItem?) {
        mainSharedViewModel.passwordItemToEdit.postValue(item)
        findNavController().navigate(R.id.action_mainFragment_to_passwordFragment)
    }

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
            showSnackbar(R.string.all_delete_mode_on)
            context?.let {
                icon.tint(it, R.color.accent)
            }
            isChecked = true
        }
    }

    private fun setDeleteModeOff() {
        menu?.getItem(MENU_DELETE_ACTION_INDEX)?.apply {
            dismissSnackbar()
            context?.let {
                icon.tint(it, R.attr.iconColor, true)
            }
            isChecked = false
        }
    }

    private fun addPasswordToList(model: PasswordModel?) {
        adapter.apply {
            model?.apply {
                list.add(modelToItem(this))
                notifyDataSetChanged()
            }
        }
    }

    private fun editPasswordInList(model: PasswordModel?) {
        adapter.apply {
            model?.apply {
                list.forEachIndexed { i, item ->
                    if (item.id == id) {
                        list[i] = modelToItem(model)
                        notifyItemChanged(i)
                    }
                }
            }
        }
    }

    companion object {

        private const val MENU_DELETE_ACTION_INDEX = 0

    }

}