package com.abank.IDCard.presentation.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abank.IDCard.R
import com.abank.IDCard.presentation.activity.scan.NFCReaderActivity
import com.abank.IDCard.utils.NFC.Passport
import com.abank.IDCard.utils.WXAdapter.Holders.PassportInfo
import com.abank.IDCard.utils.WXAdapter.WXRecyclerAdapter
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*

class PassportDetailsFragment : androidx.fragment.app.Fragment() {

    private var passportDetailsFragmentListener: PassportDetailsFragmentListener? = null
    private var passport: Passport? = null
    private lateinit var adapter: WXRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_passport_details, container, false)
        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        if (arguments!!.containsKey(IntentData.KEY_PASSPORT)) {
            passport = arguments.getParcelable(IntentData.KEY_PASSPORT)
        }
        passportDetailsFragmentListener = activity as NFCReaderActivity
    }

    override fun onResume() {
        super.onResume()
        refreshData(passport)
    }

    private fun refreshData(passport: Passport?) {
        if (passport == null) return
        adapter = WXRecyclerAdapter(PassportInfo.values(), passport)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onDetach() {
        passportDetailsFragmentListener = null
        super.onDetach()
    }

    interface PassportDetailsFragmentListener {
        fun onImageSelected(bitmap: Bitmap?)
    }

    companion object {

        fun newInstance(passport: Passport): PassportDetailsFragment {
            val myFragment = PassportDetailsFragment()
            val args = Bundle()
            args.putParcelable(IntentData.KEY_PASSPORT, passport)
            myFragment.arguments = args
            return myFragment
        }
    }

}