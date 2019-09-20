package com.abank.IDCard.utils.WXAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abank.IDCard.R
import com.abank.IDCard.utils.NFC.Passport
import com.abank.IDCard.utils.WXAdapter.Holders.InfoViewHolder
import com.abank.IDCard.utils.WXAdapter.Holders.PassportInfo

class WXRecyclerAdapter (private val list: Array<PassportInfo>,
                         private val passport: Passport): RecyclerView.Adapter<InfoViewHolder>() {

    private lateinit var context: Context

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.configure(list[position], passport)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        context = parent.context
        LayoutInflater
                .from(parent.context)
                .inflate(R.layout.viewholder_passport_info, parent, false)
                .let {
                    return InfoViewHolder(it)
                }
    }



}