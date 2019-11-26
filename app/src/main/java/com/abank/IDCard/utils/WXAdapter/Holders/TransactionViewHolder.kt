package com.abank.idcard.utils.WXAdapter.Holders

import android.view.View
import com.abank.idcard.R
import com.abank.idcard.data.repository.server.pojo.response.TransactionSingle
import com.abank.idcard.utils.WXAdapter.ListSuperClass
import com.abank.idcard.utils.WXAdapter.ListViewHolder
import kotlinx.android.synthetic.main.viewholder_passport_info.view.*

class TransactionViewHolder(containerView: View): ListViewHolder(containerView) {

    override fun configure(type: ListSuperClass, index: Int, listener: ((Int, Any?) -> Unit)?, isSelected: Boolean) {
        val obj = type.anyObject as TransactionSingle
        itemView.titleTextView.text = obj.title
        itemView.detailsTextView.text = obj.id
        itemView.setOnClickListener { listener?.invoke(index, obj) }
    }

}

class TransactionUIObject: ListSuperClass {

    override var anyObject: Any? = null
    override var image: Int = 0
    override var index: Int = 0
    override var titleString: String = ""
    override var layout: Int = R.layout.viewholder_passport_info

}