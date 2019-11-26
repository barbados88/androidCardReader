package com.abank.idcard.presentation.screens.scan.select

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.abank.idcard.databinding.ItemPassportInfoBinding
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.utils.Classes.BOTTOM_DIALOG_HELPER_TAG
import com.abank.idcard.utils.Classes.BottomDialogHelper
import com.abank.idcard.utils.Classes.BottomDialogUIObject
import com.abank.idcard.utils.Extensions.bindDataTo
import com.abank.idcard.utils.WXAdapter.ListSuperClass
import com.abank.idcard.utils.list.AsyncObservableList
import com.example.data.scan.model.TransactionSingle
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.Holder
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_select.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SelectFragment : BaseFragment() {

    override val navigator: SelectNavigator = get()

    private val viewModel: SelectViewModel by viewModel()

    override fun getLayout() = R.layout.fragment_select

    private val orders: AsyncObservableList<TransactionSingle> = AsyncObservableList()

    private val adapter = LastAdapter(orders, BR.item)
           .map<TransactionSingle, ItemPassportInfoBinding>(R.layout.item_passport_info) {
               onClick(::handleListClick)
           }

    private var bottomDialog: BottomDialogHelper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()
        addListeners()
        initRefresh()
        viewModel.loadOrders()
        viewModel.loadProfile()
    }

    private fun initRefresh() {
        recyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            viewModel.loadOrders()
        }
    }

    private fun addListeners() {
        userInfoView.fillInfo()
        userInfoView.listener = {
            if (bottomDialog == null) {
                val actions: ArrayList<ListSuperClass> = arrayListOf()
                SettingsActions.values().forEach {
                    val action = BottomDialogUIObject()
                    action.titleString = getString(it.title)
                    actions.add(action)
                }
                bottomDialog = BottomDialogHelper.newInstance(actions, getString(R.string.bottom_dialog_title), ::bottomDialogTapped)
            }
            bottomDialog?.show(requireFragmentManager(), BOTTOM_DIALOG_HELPER_TAG)
        }
    }

    private fun handleListClick(holder: Holder<ItemPassportInfoBinding>) {
        holder.itemView.transitionName = resources.getString(R.string.transition_inputs)
        holder.binding.item?.id?.let { navigator.goContainer(it, holder.itemView, userInfoView, tvInfo) }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.ordersLiveData) { items -> onListRequestResult { orders.update(items) } }
        bindDataTo(viewModel.errorLiveData) { onListRequestResult { showSnack(it) } }
        bindDataTo(viewModel.logoutLiveData) { navigator.logout(requireContext()) }
        bindDataTo(viewModel.profileInfoLiveData) { userInfoView.userInfo = it }
    }

    private fun bottomDialogTapped(position: Int, obj: Any?) {
        when (position) {
            0 -> { viewModel.logout() }
            1 -> { bottomDialog?.dismiss() }
        }
    }

    private fun onListRequestResult(action: () -> Unit) {
        orders.update(listOf())
        refreshLayout.isRefreshing = false
        action.invoke()
    }

    companion object {
        fun newInstance() = SelectFragment()
    }

}

enum class SettingsActions(@StringRes val title: Int) {
    LOGOUT(R.string.bottom_dialog_logout),
    CANCEL(R.string.bottom_dialog_cancel);
}