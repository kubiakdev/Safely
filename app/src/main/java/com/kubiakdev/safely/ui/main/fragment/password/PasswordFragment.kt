package com.kubiakdev.safely.ui.main.fragment.password

import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseActivity
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.activity.MainActivity
import com.kubiakdev.safely.ui.main.fragment.password.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.password.adapter.PasswordAdapter
import com.kubiakdev.safely.util.delegate.DefaultArgumentsDelegate
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.showKeyboard
import com.kubiakdev.safely.util.extension.withViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_password.*
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class PasswordFragment : BaseFragment(), PasswordView, AdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_password

    private val viewModel: PasswordViewModel by lazy {
        getViewModel<PasswordViewModel>(viewModelFactory)
    }

    private val adapter: PasswordAdapter by lazy {
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

    lateinit var itemTouchHelper: ItemTouchHelper

    private var NavController.detailIconResId by DefaultArgumentsDelegate.Int(
            MainValues.EXTRA_DETAIL_ICON_RES_ID
    )

    private var NavController.detailKey by DefaultArgumentsDelegate.String(
            MainValues.EXTRA_DETAIL_KEY
    )

    private lateinit var callback: SimpleItemTouchHelperCallback

    override fun initActivityComponents() {
        activity?.run {
            bar_main?.run {
                navigationIcon = null
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                replaceMenu(R.menu.menu_password)
            }

            fab_main?.run {
                setImageResource(R.drawable.ic_reply)
                setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun initComponents() {

        withViewModel(viewModel) {
            observe(isInDeleteMode, ::trySwitchDeleteMode)
        }

        callback = SimpleItemTouchHelperCallback(
                adapter, viewModel.isInDeleteMode.value ?: false) {}


        rv_password.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PasswordFragment.adapter.apply {
                adapterListener = this@PasswordFragment
            }.also { it.notifyDataSetChanged() }
        }

        itemTouchHelper = ItemTouchHelper(callback).also { it.attachToRecyclerView(rv_password) }

        findNavController().run {
            if (detailIconResId != -1 && detailKey != "") {
                updateAdapterList(detailIconResId, detailKey).also {
                    detailIconResId = -1
                    detailKey = ""
                }
            }
        }

        rv_password.showKeyboard()
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity).fragmentListener = this
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun doOnMenuActionClick(action: String, value: Boolean) {
        when (action) {
            MainValues.ACTION_PASSWORD_DELETE -> {
                viewModel.isInDeleteMode.run {
                    if (this.value != true) {
                        postValue(true)
                    } else {
                        postValue(false)
                        (activity as MainActivity).dismissSnackBar()
                    }
                }
            }
            MainValues.ACTION_PASSWORD_ADD -> {
                findNavController().navigate(R.id.action_passwordFragment_to_detailFragment)
            }
            MainValues.ACTION_PASSWORD_SAVE -> {
                savePassword()
            }
        }
    }

    override fun switchOffDeleteMode() {
        viewModel.isInDeleteMode.postValue(false)
        switchDeleteMode(false)
    }

    override fun setTitleText(text: String) {
        password_title.text = text
    }

    private fun updateAdapterList(iconResId: Int, key: String) {
        adapter.list.add(element = DetailModel(iconResId, key))
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

    private fun savePassword() {
        val array = JSONArray()
        adapter.list.forEach {
            array.put(JSONObject().apply { put(MainValues.PASSWORD_JSON_OBJECT, it) })
        }
    }
}