package com.kubiakdev.safely.ui.detailedition

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubiakdev.safely.R
import com.kubiakdev.safely.base.BaseFragment
import com.kubiakdev.safely.data.model.TemplateModel
import com.kubiakdev.safely.ui.detail.DetailSharedViewModel
import com.kubiakdev.safely.ui.icon.IconSharedViewModel
import com.kubiakdev.safely.util.extension.getSharedViewModel
import com.kubiakdev.safely.util.extension.getViewModel
import com.kubiakdev.safely.util.extension.observe
import com.kubiakdev.safely.util.extension.showKeyboard
import kotlinx.android.synthetic.main.fragment_detail_edition.*
import javax.inject.Inject

class DetailEditionFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutId: Int = R.layout.fragment_detail_edition

    override val menuResId: Int = R.menu.menu_template

    private lateinit var detailEditionViewModel: DetailEditionViewModel
    private lateinit var detailSharedViewModel: DetailSharedViewModel
    private lateinit var detailEditionSharedViewModel: DetailEditionSharedViewModel
    private lateinit var iconSharedViewModel: IconSharedViewModel

    override fun initViewModel() {
        detailEditionViewModel = getViewModel(viewModelFactory)
    }

    override fun initActivityComponents() {}

    override fun initComponents() {
        ibDetailEditionIcon.setOnClickListener {
            findNavController().navigate(R.id.action_detailEditionFragment_to_iconFragment)
        }
        etDetailEditionKey.showKeyboard()
    }

    override fun initSharedViewModels() {
        detailSharedViewModel = getSharedViewModel(viewModelFactory) {
            observe(detailModelToEdit, ::setUpIconAndKey)
        }

        detailEditionSharedViewModel = getSharedViewModel(viewModelFactory)
        iconSharedViewModel = getSharedViewModel(viewModelFactory) {
            observe(selectedIconResId, ::setUpIcon)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_template_save -> onTemplateSave()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onTemplateSave() {
        if (etDetailEditionKey.text.toString() == "") {
            showSnackbar(R.string.detail_edition_notnull_key_required)
        } else {
            val model = TemplateModel(
                    ibDetailEditionIcon.contentDescription.toString().toInt(),
                    etDetailEditionKey.text.toString()
            )
            if (detailSharedViewModel.detailModelToEdit.value != null) {
                detailEditionViewModel.editModel(
                        model.apply {
                            model.id = detailSharedViewModel.detailModelToEdit.value?.id ?: 0
                        }
                ) {
                    detailEditionSharedViewModel.editedModel.postValue(it)
                    popBackStack()
                }
            } else {
                detailEditionViewModel.addModel(model) {
                    detailEditionSharedViewModel.newModel.postValue(it)
                    popBackStack()
                }
            }
        }
    }

    private fun setUpIcon(iconResId: Int?) {
        ibDetailEditionIcon.apply {
            setImageResource(iconResId ?: R.drawable.ic_image)
            contentDescription = iconResId.toString()
        }
    }

    private fun setUpIconAndKey(model: TemplateModel?) {
        model?.apply {
            ibDetailEditionIcon.apply {
                setImageResource(iconResId)
                contentDescription = iconResId.toString()
            }

            etDetailEditionKey.setText(key)
        }
    }

}