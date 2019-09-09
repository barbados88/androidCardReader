package com.abank.IDCard.utils.WXAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity

// USAGE:
// inherit you viewHolder class from ListViewHolder
//
//class MyViewHolder(containerView: View): ListViewHolder(containerView) {
//
//    override fun configure(type: ListSuperClass, listener: ((Int, Entity?) -> Unit)?) {
//        super.configure(type, listener)
//    }
//
//}
//
//inherit ui object from ListSuperClass
//
//class BankEnum: ListSuperClass {
//
//    override var titleString: String =""
//    override var index: Int = ordinal
//    override var uiObject: Entity? = null
//
//    override var title: Int = 0
//        get() {}
//
//    override var details: Int = 0
//        get() {}
//
//    override var layout: Int = 0
//        get() {}
//
//}
// pass ui objects to WXRecyclerAdapter

interface ListSuperClass {

    var title: Int
    var details: Int
    var titleString: String
    var index: Int
    var layout: Int
    var uiObject: Entity?
    var image: Int

}

open class ListViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    open fun configure(type: ListSuperClass, listener: ((Int, Entity?) -> Unit)? = null, isSelected: Boolean = false) {
        print("super opject init")
    }

}

class WXRecyclerAdapter ( private val list: Array<ListSuperClass>,
                          private val onCreated: (holder: ListViewHolder, position: Int) -> Unit): RecyclerView.Adapter<ListViewHolder>() {

    private lateinit var context: Context

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        onCreated(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        context = parent.context
        LayoutInflater
                .from(parent.context)
                .inflate(list[viewType].layout, parent, false)
                .let {
                    return ListViewHolder(it)
                }
    }



}