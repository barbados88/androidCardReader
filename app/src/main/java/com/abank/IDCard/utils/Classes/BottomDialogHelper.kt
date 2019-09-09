package com.abank.IDCard.utils.Classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Entity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.abank.IDCard.App
import com.abank.IDCard.R
import com.abank.IDCard.utils.WXAdapter.ListSuperClass
import com.abank.IDCard.utils.WXAdapter.ListViewHolder
import com.abank.IDCard.utils.WXAdapter.WXRecyclerAdapter
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.item_image_and_text.view.*

val BOTTOM_DIALOG_HELPER_TAG: String = "1"

class BottomDialogViewHolder(containerView: View) : ListViewHolder(containerView) {

    override fun configure(type: ListSuperClass, listener: ((Int, Entity?) -> Unit)?, isSelected: Boolean) {
        super.configure(type, listener, isSelected)
        print("binded child")
        itemView.textView.text = type.titleString
        itemView.imageView.setImageDrawable(itemView.context.getDrawable(type.image))
        itemView.setOnClickListener {
            listener?.invoke(type.index, null)
        }
    }

}

class BottomDialogHelper : BottomSheetDialogFragment() {

    private lateinit var adapter: WXRecyclerAdapter
    private lateinit var array: Array<ListSuperClass>
    private var dialogTitle: String? = null
    lateinit var itemClick: (index: Int, entity: Entity?) -> Unit

    companion object {

        fun newInstance(list: Array<ListSuperClass>, title: String? = null, onClickListener: ((index: Int, entity: Entity?) -> Unit)? = null): BottomDialogHelper {
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
        adapter = WXRecyclerAdapter(array, this::onCreated)
        recyclerView.layoutManager = LinearLayoutManager(App.applicationContext)
        recyclerView.adapter = adapter
    }

    private fun onSelected(index: Int, entity: Entity?) {
        itemClick.invoke(index, entity)
        dismiss()
    }

    private fun onCreated(viewHolder: ListViewHolder, position: Int) {
        val holder = BottomDialogViewHolder(viewHolder.itemView)
        holder.configure(array[position], this::onSelected)
    }

}