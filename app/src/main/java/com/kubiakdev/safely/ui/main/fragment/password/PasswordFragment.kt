package com.kubiakdev.safely.ui.main.fragment.password

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.base.BaseActivity
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.ui.main.MainValues
import com.kubiakdev.safely.ui.main.activity.MainActivity
import com.kubiakdev.safely.ui.main.fragment.password.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.password.adapter.PasswordAdapter
import com.kubiakdev.safely.util.KeyboardUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : BaseFragment<PasswordPresenter>(), PasswordView, AdapterListener {

    override val layoutId: Int = R.layout.fragment_password

    lateinit var itemTouchHelper: ItemTouchHelper

    private val passwordAdapter: PasswordAdapter by lazy {
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

    private lateinit var callback: SimpleItemTouchHelperCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callback = SimpleItemTouchHelperCallback(
                passwordAdapter,
                (activity as MainActivity).isInDeletePasswordMode
        )
        itemTouchHelper = ItemTouchHelper(callback).also { it.attachToRecyclerView(rv_password) }

        findNavController().graph.defaultArguments.run {
            if (containsKey(MainValues.EXTRA_DETAIL_ICON_RES_ID) &&
                    containsKey(MainValues.EXTRA_DETAIL_KEY)) {
                updateAdapterList(this)
                remove(MainValues.EXTRA_DETAIL_ICON_RES_ID)
                remove(MainValues.EXTRA_DETAIL_KEY)
            }
        }
        (activity as MainActivity).switchDeleteMode()
    }

    override fun onAttach() {
        presenter.view = this
    }

    override fun initComponents() {
        initActivityComponents()
        rv_password.run {
            layoutManager = LinearLayoutManager(context)
            passwordAdapter.adapterListener = this@PasswordFragment
            adapter = passwordAdapter
        }
        KeyboardUtil.showKeyboard(view?:View(context))
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseActivity<*>).fragmentListener = this
    }

    private fun initActivityComponents() {
        activity?.bar_main?.run {
            navigationIcon = null
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(com.kubiakdev.safely.R.menu.menu_password)
        }

        activity?.fab_main?.run {
            setImageResource(R.drawable.ic_reply)
            setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateAdapterList(bundle: Bundle) {
        val model = DetailModel(
                bundle.getInt(MainValues.EXTRA_DETAIL_ICON_RES_ID),
                bundle.getString(MainValues.EXTRA_DETAIL_KEY) ?: ""
        )
        passwordAdapter.list.add(model)
        rv_password.adapter?.notifyDataSetChanged()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun doOnMenuActionClick(action: String, value: Boolean) {
        when (action) {
            MainValues.ACTION_PASSWORD_DELETE -> {
                switchTouchMode(value)
            }
        }
    }

    override fun switchOffDeleteMode() {
        (activity as MainActivity).isInDeletePasswordMode = false
        switchTouchMode()
    }

    override fun onDestroy() {
//        KeyboardUtil.hideKeyboard(view ?: View(context))
        super.onDestroy()
    }

    private fun switchTouchMode(value: Boolean = false) {
        callback.isInDeleteMode = value
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv_password)
    }
}