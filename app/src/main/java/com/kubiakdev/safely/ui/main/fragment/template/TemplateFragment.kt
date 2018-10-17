package com.kubiakdev.safely.ui.main.fragment.template

import androidx.navigation.fragment.findNavController
import com.kubiakdev.safely.R
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.mvp.BaseFragment
import com.kubiakdev.safely.ui.main.MainValues
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_template.*

class TemplateFragment : BaseFragment<TemplatePresenter>() {

    override val layoutId: Int = R.layout.fragment_template

    override fun initComponents() {
        activity?.bar_main?.replaceMenu(R.menu.menu_template)
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
        if (et_template_key.text.toString() == "") {
            showSnackBar(R.string.template_notnull_key_required)
        } else {
            addTemplate(TemplateModel(R.drawable.ic_image, et_template_key.text.toString()))
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