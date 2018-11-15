package com.kubiakdev.safely.ui.main.fragment.main

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.base.adapter.SimpleItemTouchHelperCallback
import com.kubiakdev.safely.data.model.PasswordModel
import com.kubiakdev.safely.ui.main.activity.MainActivity
import com.kubiakdev.safely.ui.main.fragment.main.adapter.AdapterListener
import com.kubiakdev.safely.ui.main.fragment.main.adapter.MainAdapter
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.withViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment(), MainView, AdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_main

    private val viewModel: MainViewModel by lazy {
        getViewModel<MainViewModel>(viewModelFactory)
    }

    private lateinit var callback: SimpleItemTouchHelperCallback

    private lateinit var adapter: MainAdapter

    private lateinit var itemTouchHelper: ItemTouchHelper

    private lateinit var dataList: MutableList<PasswordModel>

    override fun initActivityComponents() {
        activity?.run {
            bar_main?.run {
                replaceMenu(R.menu.menu_main)
                setNavigationIcon(R.drawable.ic_menu)
                fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
            fab_main?.run {
                setImageResource(R.drawable.ic_add)
                setOnClickListener {
                    findNavController().navigate(R.id.action_mainFragment_to_passwordFragment)
                }
            }
        }
    }

    override fun initComponents() {
        withViewModel(viewModel) {

        }

        adapter = MainAdapter(mutableListOf(), this)
                .apply { adapterListener = this@MainFragment }
                .also {
                    viewModel.getData { list ->
                        adapter.list = list.toMutableList()
                        dataList = list.toMutableList()
                        (activity as MainActivity).hideProgressBar()
                        rv_main.adapter?.notifyDataSetChanged()
                    }
                    (activity as MainActivity).showProgressBar()
                }


        callback = SimpleItemTouchHelperCallback(adapter) { viewModel.updateDatabase(adapter.list) }

        itemTouchHelper = ItemTouchHelper(callback).also { it.attachToRecyclerView(rv_main) }

        rv_main.run {
            layoutManager = StaggeredGridLayoutManager(2, 1)
            adapter = this@MainFragment.adapter.apply {
                adapterListener = this@MainFragment
            }.also { it.notifyDataSetChanged() }

        }
    }

    override fun showOnDeleteDialog(position: Int) {
        AlertDialog.Builder(rv_main.context)
                .setMessage(R.string.delete_password_question)
                .setPositiveButton(R.string.delete_password_yes) { _, _ ->
                    deletePassword(position)
                }
                .setNegativeButton(R.string.delete_password_no) { _, _ -> }
    }

    private fun deletePassword(position: Int) {
        adapter.list.run {
            removeAt(position)
            if (isEmpty()) {
//                switchOffDeleteMode()
            }
        }.also { adapter.notifyItemRemoved(position) }
    }
}