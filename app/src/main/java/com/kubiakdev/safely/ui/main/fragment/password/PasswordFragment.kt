package com.kubiakdev.safely.ui.main.fragment.password

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.DetailModel
import com.kubiakdev.safely.mvp.BaseFragment
import com.kubiakdev.safely.ui.main.fragment.password.adapter.DetailAdapter
import com.kubiakdev.safely.ui.main.fragment.password.adapter.DetailItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : BaseFragment<PasswordPresenter>() {

    override val layoutId: Int = R.layout.fragment_password

    private val detailAdapter: DetailAdapter by lazy {
        DetailAdapter(list)
    }

    private val list = mutableListOf(
            DetailModel(
                    R.drawable.ic_title,
                    R.string.all_new_title,
                    "",
                    true
            )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = DetailModel(
                PasswordFragmentArgs.fromBundle(arguments).iconResId,
                PasswordFragmentArgs.fromBundle(arguments).keyResId
        )
        if (model.iconResId != -1 && model.keyResId != -1) {
            detailAdapter.list.add(model)
            rv_password.adapter?.notifyDataSetChanged()
        }
    }

    override fun initComponents() {
        initActivityComponents()
        rv_password.run {
            layoutManager = LinearLayoutManager(context)
            adapter = detailAdapter
        }
    }

    private fun initActivityComponents() {
        activity?.bar_main?.run {
            navigationIcon = null
            fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            replaceMenu(R.menu.menu_password)
        }

        activity?.fab_main?.run {
            setImageResource(R.drawable.ic_reply)
            setOnClickListener {
                findNavController().navigate(R.id.action_passwordFragment_to_mainFragment)
            }
        }
    }

//    fun addNewItem(iconId: Int, keyId: Int) {
//        list.add(
//                DetailItem(
//                        iconId,
//                        context?.getString(keyId) ?: "",
//                        "",
//                        true)
//        )
////        detailAdapter.itemNews = list
//        detailAdapter.notifyDataSetChanged()
//    }

}