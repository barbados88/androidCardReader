package com.abank.IDCard.presentation.fragment.bottomBar.firstTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.abank.IDCard.R

class FirstTabFragment : Fragment() {

    companion object {
        fun newInstance() = FirstTabFragment()
    }

    private lateinit var viewModel: FirstTabViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.first_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FirstTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
