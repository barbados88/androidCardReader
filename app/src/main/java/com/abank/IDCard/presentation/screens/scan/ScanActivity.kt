package com.abank.idcard.presentation.screens.scan

import android.os.Bundle
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseActivity
import com.abank.idcard.presentation.screens.scan.container.ContainerFragment
import com.abank.idcard.presentation.screens.scan.select.SelectFragment

class ScanActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.scan_container, SelectFragment.newInstance())
                    .commit()

    }
}