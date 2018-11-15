package com.kubiakdev.safely.ui.template

import android.text.Editable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.util.Const
import com.kubiakdev.safely.util.delegate.DefaultArgumentsDelegate
import com.kubiakdev.safely.util.extension.getViewModel
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_template.*
import javax.inject.Inject

class TemplateFragment : BaseFragment(), TemplateView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_template

    private val viewModel: TemplateViewModel by lazy {
        getViewModel<TemplateViewModel>(viewModelFactory)
    }

    private var NavController.templateIconResId by DefaultArgumentsDelegate.Int(
            Const.EXTRA_TEMPLATE_ICON_RES_ID
    )

    private var NavController.templateKey by DefaultArgumentsDelegate.String(
            Const.EXTRA_TEMPLATE_KEY
    )

    private var NavController.detailIconResId by DefaultArgumentsDelegate.Int(
            Const.EXTRA_DETAIL_ICON_RES_ID
    )

    private var NavController.detailKey by DefaultArgumentsDelegate.String(
            Const.EXTRA_DETAIL_KEY
    )

    private var NavController.detailEditedItemIndex by DefaultArgumentsDelegate.Int(
            Const.EXTRA_DETAIL_EDITED_ITEM_INDEX
    )

    private var cachedIconResId: Int? = null
    private var cachedKey: String? = null

    override fun initComponents() {
        activity.bar_frame?.replaceMenu(R.menu.menu_template)

        ib_template_icon.setOnClickListener {
            findNavController().navigate(R.id.action_templateFragment_to_iconFragment)
        }

        findNavController().run {
            if (templateIconResId != -1 && templateKey != "") {
                ib_template_icon.run {
                    setImageResource(templateIconResId)
                    contentDescription = templateIconResId.toString()
                }
                et_template_key.text = Editable.Factory.getInstance().newEditable(templateKey)
                cachedIconResId = templateIconResId
                cachedKey = templateKey
            } else {
                if (templateIconResId == -1) {
                    templateIconResId = R.drawable.ic_image
                }
                ib_template_icon.run {
                    setImageResource(templateIconResId)
                    contentDescription = templateIconResId.toString()
                }
            }
        }
    }

    override fun doOnMenuActionClick(action: String, value: Boolean) {
        when (action) {
            Const.ACTION_TEMPLATE_SAVE -> {
                onTemplateSave()
            }
            Const.ACTION_PASSWORD_DELETE -> {
            }
        }
    }

    private fun onTemplateSave() {
        et_template_key.run {
            if (text.toString() == "") {
                showSnackBar(R.string.template_notnull_key_required)
            } else {
                val currentIconResId = ib_template_icon.contentDescription.toString().toInt()
                val currentKey = text.toString()
                if ((cachedIconResId != null && cachedKey != null)
                        && (currentIconResId != cachedIconResId || currentKey != cachedKey)) {
                    viewModel.editTemplateAndBack(
                            TemplateModel(currentIconResId, currentKey),
                            findNavController().detailEditedItemIndex
                    ) {
                        findNavController().popBackStack()
                    }
                } else {
                    addTemplateAndBack(TemplateModel(currentIconResId, currentKey))
                }
            }
        }
    }

    private fun addTemplateAndBack(model: TemplateModel) {
        findNavController().run {
            detailIconResId = model.iconResId
            detailKey = model.key
            popBackStack()
        }
    }
}