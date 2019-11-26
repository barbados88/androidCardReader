package com.abank.idcard.utils.WXAdapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.properties.Delegates

interface ListSuperClass {

    var titleString: String
    var index: Int
    var layout: Int
    var anyObject: Any?
    var image: Int

}

open class ListViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun configure(type: ListSuperClass, index: Int, listener: ((Int, Any?) -> Unit)? = null, isSelected: Boolean = false) {
        print("super object init")
    }

}

interface AutoUpdatableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(old: List<T>, new: List<T>, compare: (T, T) -> Boolean) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = compare(old[oldItemPosition], new[newItemPosition])
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == new[newItemPosition]
            override fun getOldListSize() = old.size
            override fun getNewListSize() = new.size
        })

        diff.dispatchUpdatesTo(this)
    }
}


class WXRecyclerAdapter<T: ListViewHolder> (private val inflater: (parent: ViewGroup, viewType: Int) -> T,
                                            private val listener: ((index: Int, obj: Any?) -> Unit)? = null): RecyclerView.Adapter<T>(), AutoUpdatableAdapter {

    var items: List<ListSuperClass> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.anyObject == n.anyObject }
    }
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.configure(items[position], position, listener)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T = inflater(parent, viewType)

}

class WXGridAdapter<T: ListSuperClass>(private val inflater: (position: Int, convertView: View?, parent: ViewGroup) -> View,
                                       private val listData: List<T>) : BaseAdapter() {

    override fun getCount(): Int = listData.size

    override fun getItem(position: Int): Any = listData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return inflater.invoke(position, convertView, parent)
    }

}