package com.kubiakdev.safely.ui.template

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.ui.detail.DetailViewModel
import com.kubiakdev.safely.ui.icon.IconViewModel
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.toEditable
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_template.*
import javax.inject.Inject

class TemplateFragment : BaseFragment(), TemplateView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_template

    override val menuResId: Int? = R.menu.menu_template

    private val templateViewModel by lazy {
        getViewModel<TemplateViewModel>(viewModelFactory)
    }

    private val iconViewModel by lazy {
        getViewModel<IconViewModel>(viewModelFactory)
    }

    private val detailViewModel by lazy {
        getViewModel<DetailViewModel>(viewModelFactory)
    }

    override fun initComponents() {
        activity.bar_frame?.replaceMenu(R.menu.menu_template)

        ib_template_icon.setOnClickListener {
            findNavController().navigate(R.id.action_templateFragment_to_iconFragment)
        }

        findNavController().run {
            iconViewModel.iconResId.run {
                if (isNotEqualDefValue()) {
                    ib_template_icon.run {
                        setImageResource(value ?: R.drawable.ic_image)
                        contentDescription = value.toString()
                    }
                    reset()
                    return
                }
            }

            detailViewModel.run {
                if (editedDetailIconResId.isEqualDefValue()) {
                    editedDetailIconResId.value = R.drawable.ic_image
                }
                et_template_key.text =
                        toEditable(detailViewModel.editedDetailKey.value ?: "")
                ib_template_icon.run {
                    setImageResource(editedDetailIconResId.value ?: R.drawable.ic_image)
                    contentDescription = editedDetailIconResId.value.toString()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_template_save -> onTemplateSave()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onTemplateSave() {
        et_template_key.run {
            if (text.toString() == "") {
                showSnackBar(R.string.template_notnull_key_required)
            } else {
                val currentIconResId = ib_template_icon.contentDescription.toString().toInt()
                val currentKey = text.toString()
                detailViewModel.run {
                    if (editedDetailIndex.isNotEqualDefValue()) {
                        templateViewModel.editTemplateAndPopBackStack(
                                TemplateModel(currentIconResId, currentKey),
                                editedDetailIndex.value ?: -1
                        ) {
                            editedDetailIndex.reset()
                            editedDetailIconResId.reset()
                            editedDetailKey.reset()
                            findNavController().popBackStack()
                        }
                    } else {
                        templateViewModel.addTemplateAndPopBackStack(
                                TemplateModel(currentIconResId, currentKey)
                        ) {
                            templateViewModel.newTemplateIconResId.reset()
                            templateViewModel.newTemplateKey.reset()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }
}