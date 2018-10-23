package com.kubiakdev.safely.ui.main.fragment.template

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_template.*

class TemplateFragment : BaseFragment<TemplatePresenter>(), TemplateView {

    override val layoutId: Int = R.layout.fragment_template

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().graph.defaultArguments.run {
            if (containsKey(MainValues.EXTRA_ICON_RES_ID)) {
                ib_template_icon.setImageResource(getInt(MainValues.EXTRA_ICON_RES_ID))
                remove(MainValues.EXTRA_ICON_RES_ID)
            }
        }
    }

    override fun onAttach() {
        presenter.view = this
    }

    override fun initComponents() {
        hideFullFragment()
        activity?.bar_main?.replaceMenu(R.menu.menu_template)
        ib_template_icon.setOnClickListener {
            findNavController().navigate(R.id.action_templateFragment_to_iconFragment)
        }
    }

    override fun doOnMenuActionClick(action: String, value: Boolean) {
        when (action) {
            MainValues.ACTION_TEMPLATE_SAVE -> {
                onTemplateSave()
            }
            MainValues.ACTION_PASSWORD_DELETE -> {
                println("dupa")
            }
        }
    }

    private fun onTemplateSave() {
        et_template_key.run {
            if (text.toString() == "") {
                showSnackBar(R.string.template_notnull_key_required)
            } else {
                addTemplate(
                        TemplateModel(
                                ib_template_icon.contentDescription.toString().toInt(),
                                text.toString()
                        )
                )
            }
        }
    }

    private fun addTemplate(model: TemplateModel) {
        findNavController().run {
            graph.defaultArguments.apply {
                putInt(MainValues.EXTRA_TEMPLATE_ICON_RES_ID, model.iconResId)
                putString(MainValues.EXTRA_TEMPLATE_KEY, model.key)
            }
            popBackStack()
        }
    }
}