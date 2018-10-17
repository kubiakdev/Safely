package com.kubiakdev.safely.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.safely.mvp.adapter.OnStartDragListener
import com.kubiakdev.safely.ui.main.activity.MainActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<P : BasePresenter<*>> :
        DaggerFragment(),
        BaseView,
        FragmentListener,
        OnStartDragListener {

    @Inject
    lateinit var presenter: P
        internal set

    abstract val layoutId: Int

//    abstract val canBeDestroyedOnNavigate: Boolean

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity<*>).fragmentListener = this
        findNavController().addOnNavigatedListener { _, _ ->
            run {
//                if (canBeDestroyedOnNavigate) {
//                    onDetach()
//                }
            }
        }
        initComponents()
    }

    override fun onStop() {
        presenter.onDetach()
//        (activity as BaseActivity<*>).fragmentListener = null
        super.onStop()
    }

    protected open fun inflateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    open fun initComponents() {}

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {}

    override fun doOnMenuActionClick(action: String, value: Boolean) {}

    fun showMessage(message: String) {
        (activity as BaseActivity<*>).showMessage(message)
    }

    fun showSnackBar(@StringRes stringRes: Int) {
        if (activity is MainActivity) {
            (activity as MainActivity).showSnackBar(stringRes)
        }
    }
}