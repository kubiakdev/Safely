package com.kubiakdev.safely.ui.password

import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.db.entity.PasswordEntity
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.detail.DetailViewModel
import com.kubiakdev.safely.ui.password.adapter.AdapterListener
import com.kubiakdev.safely.ui.password.adapter.PasswordAdapter
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.showKeyboard
import com.kubiakdev.safely.util.extension.withViewModel
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_password.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class PasswordFragment : BaseFragment(), PasswordView, AdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_password

    override val menuResId: Int? = R.menu.menu_password

    private var color = R.color.white

    private val passwordViewModel by lazy {
        getViewModel<PasswordViewModel>(viewModelFactory)
    }

    private val detailViewModel by lazy {
        getViewModel<DetailViewModel>(viewModelFactory)
    }

    private val adapter by lazy {
        PasswordAdapter(list, this)
    }

    private val list = mutableListOf(
            DetailModel(
                    R.drawable.ic_title,
                    "Title",
                    "",
                    false
            )
    )

    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var callback: SimpleItemTouchHelperCallback

    override fun initActivityComponents() {
        activity.run {
            bar_frame?.run {
                navigationIcon = null
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                replaceMenu(R.menu.menu_password)
            }

            fab_frame?.run {
                setImageResource(R.drawable.ic_reply)
                setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun initComponents() {
        setHasOptionsMenu(true)

        rv_password.setBackgroundResource(color)

        withViewModel(passwordViewModel) {
            observe(isInDeleteMode, ::trySwitchDeleteMode)
        }

        callback = SimpleItemTouchHelperCallback(
                adapter, passwordViewModel.isInDeleteMode.value ?: false) {}


        rv_password.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PasswordFragment.adapter.apply {
                adapterListener = this@PasswordFragment
            }.also { it.notifyDataSetChanged() }
        }

        itemTouchHelper = ItemTouchHelper(callback).also { it.attachToRecyclerView(rv_password) }

        findNavController().run {
            detailViewModel.run {
                if (newDetailIconResId.isNotEqualDefValue() && newDetailKey.isNotEqualDefValue()) {
                    updateAdapterList()
                    newDetailIconResId.reset()
                    newDetailKey.reset()
                }
            }
        }

        rv_password.showKeyboard()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_password_add ->
                findNavController().navigate(R.id.action_passwordFragment_to_detailFragment)
            R.id.action_password_save -> savePassword()
            R.id.action_password_delete -> passwordViewModel.isInDeleteMode.run {
                color = R.color.colorAccent
                if (this.value != true) {
                    value = true
                } else {
                    value = false
                    activity.dismissSnackBar()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun switchOffDeleteMode() {
        passwordViewModel.isInDeleteMode.postValue(false)
        switchDeleteMode(false)
    }

    override fun setTitleText(text: String) {
        activity.tv_frame.text = text
    }

    private fun updateAdapterList() {
        detailViewModel.run {
            adapter.list.add(element = DetailModel(
                    newDetailIconResId.value ?: R.drawable.ic_image,
                    newDetailKey.value ?: "")
            )
        }
        rv_password.adapter?.notifyDataSetChanged()
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
        activity.run {
            menu?.get(0)?.icon = if (isInDeleteMode) {
                ContextCompat.getDrawable(this, R.drawable.ic_delete_on).also {
                    showSnackBar(R.string.all_delete_on)
                }
            } else {
                ContextCompat.getDrawable(this, R.drawable.ic_delete_off)
            }
        }
    }

    private fun savePassword() {
        adapter.list.run {
            val entity = PasswordEntity(
                    this[0].value,
                    getHoldersInString(),
                    Date(),
                    Date(),
                    false,
                    (R.color.colorAccent).toString(),
                    ""
            )
            passwordViewModel.addPassword(entity) {
                findNavController().popBackStack()
            }
        }
    }

    private fun getHoldersInString(): String = JSONArray().apply {
        adapter.list.forEach {
            put(JSONObject().apply { put(Const.PASSWORD_JSON_OBJECT, it) })
        }
    }.toString()
}