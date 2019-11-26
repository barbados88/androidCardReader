package com.abank.idcard.utils.Classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abank.idcard.R
import com.abank.idcard.utils.WXAdapter.ListSuperClass
import com.abank.idcard.utils.WXAdapter.ListViewHolder
import com.abank.idcard.utils.WXAdapter.WXRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.item_image_and_text.view.*

val BOTTOM_DIALOG_HELPER_TAG: String = "1"

class BottomDialogViewHolder(containerView: View) : ListViewHolder(containerView) {

    override fun configure(type: ListSuperClass, index: Int, listener: ((Int, Any?) -> Unit)?, isSelected: Boolean) {
        itemView.textView.text = type.titleString
        itemView.imageView.setImageDrawable(null)
        itemView.setOnClickListener {
            listener?.invoke(index, null)
        }
    }

}

class BottomDialogUIObject: ListSuperClass {

    override var anyObject: Any? = null
    override var image: Int = 0
    override var index: Int = 0
    override var titleString: String = ""
    override var layout: Int = R.layout.item_image_and_text

}

class BottomDialogHelper: BottomSheetDialogFragment() {

    private lateinit var adapter: WXRecyclerAdapter<BottomDialogViewHolder>
    private lateinit var array: ArrayList<ListSuperClass>
    private var dialogTitle: String? = null
    lateinit var itemClick: (index: Int, entity: Any?) -> Unit

    companion object {

        fun newInstance(list: ArrayList<ListSuperClass>, title: String? = null, onClickListener: ((index: Int, entity: Any?) -> Unit)? = null): BottomDialogHelper {
            val fragment = BottomDialogHelper()
            fragment.array = list
            fragment.dialogTitle = title
            if (onClickListener != null) fragment.itemClick = onClickListener
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTextView.text = dialogTitle
        initRecycler()
    }

    private fun initRecycler() {
        adapter = WXRecyclerAdapter(this::instantiateHolder, this::onSelected)
        adapter.items = array
        recyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        recyclerView.adapter = adapter
    }

    private fun onSelected(index: Int, entity: Any?) {
        itemClick.invoke(index, entity)
        dismiss()
    }

    private fun instantiateHolder(parent: ViewGroup, viewType: Int): BottomDialogViewHolder {
        LayoutInflater
                .from(parent.context)
                .inflate(array[viewType].layout, parent, false)
                .let {
                    return BottomDialogViewHolder(it)
                }
    }

}