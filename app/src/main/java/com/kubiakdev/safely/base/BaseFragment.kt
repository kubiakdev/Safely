package com.kubiakdev.safely.base

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.base.adapter.OnStartDragListener
import com.kubiakdev.safely.util.extension.hideKeyboard
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), BaseView, OnStartDragListener {

    protected val activity by lazy { getActivity() as BaseActivity }

    protected abstract val layoutId: Int

    protected open val menuResId: Int? = null

    protected open val itemTouchHelper: ItemTouchHelper? = null

    var menu: Menu? = null

    private var isSharedViewModelsInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(menuResId != null)
        initActivityComponents()
        initComponents()
        itemTouchHelper
        if (!isSharedViewModelsInitialized) {
            initSharedViewModels()
            isSharedViewModelsInitialized = true
        }
    }

    protected open fun inflateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menuResId?.let {
            activity.menuInflater.inflate(it, menu)
            this@BaseFragment.menu = menu
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {}

    protected open fun initViewModel() {}

    protected open fun initActivityComponents() {}

    protected open fun initComponents() {}

    protected open fun initSharedViewModels() {}

    fun popBackStack() {
        view?.hideKeyboard()
        findNavController().popBackStack()
    }

    fun showSnackbar(@StringRes stringResId: Int) {
        activity.showSnackbar(stringResId)
    }

    fun dismissSnackbar() {
        activity.dismissSnackbar()
    }

}