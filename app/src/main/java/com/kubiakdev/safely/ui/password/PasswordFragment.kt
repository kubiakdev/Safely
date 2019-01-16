package com.kubiakdev.safely.ui.password

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.ui.detail.DetailSharedViewModel
import com.kubiakdev.safely.ui.main.MainSharedViewModel
import com.kubiakdev.safely.ui.main.adapter.item.PasswordItem
import com.kubiakdev.safely.ui.password.adapter.PasswordAdapter
import com.kubiakdev.safely.ui.password.adapter.PasswordAdapterListener
import com.kubiakdev.safely.ui.password.adapter.item.DetailItem
import com.kubiakdev.safely.util.extension.*
import itemToModel
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_password.*
import modelToEntity
import modelToItem
import java.util.*
import javax.inject.Inject

class PasswordFragment : BaseFragment(), PasswordAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_password

    override val menuResId: Int = R.menu.menu_password

    override val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(callback).also { it.attachToRecyclerView(rvPassword) }
    }

    private val adapter by lazy {
        PasswordAdapter(mutableListOf(), this, false)
    }

    private val callback by lazy {
        SimpleItemTouchHelperCallback(adapter, false)
    }

    private lateinit var passwordViewModel: PasswordViewModel
    private lateinit var mainSharedViewModel: MainSharedViewModel
    private lateinit var passwordSharedViewModel: PasswordSharedViewModel
    private lateinit var detailSharedViewModel: DetailSharedViewModel

    override fun initViewModel() {
        passwordViewModel = getViewModel(viewModelFactory)
    }

    override fun initActivityComponents() {
        activity.apply {
            bottomAppBar?.apply {
                navigationIcon = null
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }

            fab?.apply {
                setImageResource(R.drawable.ic_reply)
                setOnClickListener { popBackStack() }
            }
        }
    }

    override fun initComponents() {
        rvPassword.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PasswordFragment.adapter.apply {
                listener = this@PasswordFragment
            }.also { it.notifyDataSetChanged() }
        }
    }

    override fun initSharedViewModels() {
        mainSharedViewModel = getSharedViewModel(viewModelFactory) {
            observe(passwordItemToEdit, ::addDetailListToAdapter)
        }

        passwordSharedViewModel = getSharedViewModel(viewModelFactory)
        detailSharedViewModel = getSharedViewModel(viewModelFactory) {
            observe(newDetailModel, ::addDetail)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_password_add ->
                findNavController().navigate(R.id.action_passwordFragment_to_detailFragment)
            R.id.action_password_save -> savePassword()
            R.id.action_password_delete -> setDeleteMode()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition == 0 || toPosition == 0) return
        adapter.apply {
            passwordViewModel.switch(
                    modelToEntity(itemToModel(list[fromPosition])).id,
                    modelToEntity(itemToModel(list[toPosition])).id
            ) {
                Collections.swap(list, fromPosition, toPosition)
                notifyItemMoved(fromPosition, toPosition)
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        passwordViewModel.remove((adapter.list[position]).id) {
            position.let {
                adapter.apply {
                    list.removeAt(it)
                    notifyItemRemoved(it)
                }
            }.also {
                if (adapter.list.isEmpty()) {
                    detailSharedViewModel.isInDeleteMode.postValue(false)
                }
            }
        }
    }

    override fun setTitleText(text: String) {
        tbPassword.title = text
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

    private fun addDetailListToAdapter(item: PasswordItem?) {
        if (item != null) {
            activity.showProgressBar()
            passwordViewModel.getPassword(item.id) {
                adapter.list.addAll(it.map { model -> modelToItem(model) }.toMutableList())
                tbPassword.title = (adapter.list.first()).value
                activity.hideProgressBar()
                rvPassword.adapter?.notifyDataSetChanged()
            }
        } else {
            adapter.apply {
                list.add(TITLE_DEFAULT_DETAIL_ITEM)
                isNewPassword = true
            }

            tbPassword.setTitle(R.string.password_title_default)
            rvPassword.adapter?.notifyDataSetChanged()
            view?.showKeyboard()
        }
    }

    private fun savePassword() {
        adapter.list.first().let {
            if (adapter.list.first().value.isEmpty()) {
                showSnackbar(R.string.password_notnull_title_required)
            } else {
                mainSharedViewModel.passwordItemToEdit.apply {
                    getModelFromAdapter().let { model ->
                        if (model.id == 0L) {
                            passwordViewModel.addPassword(model) {
                                postValue(null)
                                passwordSharedViewModel.newPasswordModel.postValue(model)
                                popBackStack()
                            }
                        } else {
                            passwordViewModel.updatePassword(model) {
                                postValue(null)
                                passwordSharedViewModel.editedPasswordModel.postValue(model)
                                popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getModelFromAdapter(): PasswordModel =
            mainSharedViewModel.passwordItemToEdit.let {
                return PasswordModel(
                        it.value?.id ?: 0,
                        adapter.list.first().value,
                        adapter.list.map { item -> itemToModel(item) },
                        it.value?.created ?: Date(),
                        Date(),
                        false,
                        R.color.white.toString(),
                        ""
                )
            }

    private fun addDetail(model: DetailModel?) {
        adapter.apply {
            model?.apply {
                list.add(modelToItem(this))
                isNewPassword = true
                notifyDataSetChanged()
                view?.showKeyboard()
            }
        }
    }

    companion object {

        private const val MENU_DELETE_ACTION_INDEX = 0

        private val TITLE_DEFAULT_DETAIL_ITEM = DetailItem(
                R.drawable.ic_title,
                "Title",
                "",
                false
        )

    }

}