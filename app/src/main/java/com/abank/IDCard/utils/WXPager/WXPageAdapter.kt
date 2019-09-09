package com.abank.IDCard.utils.WXPager

import android.content.Context
import androidx.fragment.app.FragmentPagerAdapter
import com.abank.IDCard.presentation.base.BaseFragment

// USAGE:
//enum class YourPager: PagerInterface {
//
//    past, scheduled;
//
//    companion object {
//        val allValues = values() as Array<PagerInterface>
//    }
//
//    override val item: BaseFragment
//        get() {
//            when(this) {
//                past -> return FirstPagerFragment.newInstance()
//                scheduled -> return SecondPagerFragment.newInstance()
//            }
//        }
//
//    override val title: Int
//        get() {
//            when(this) {
//                past -> return R.string.title_first_tab
//                scheduled -> return R.string.title_second_tab
//            }
//        }
//
//}
//
//create ui object of android.support.v4.view.ViewPager
//
//class YourPagerFragment: BaseFragment() {
//
//    companion object {
//        fun newInstance() = YourPagerFragment()
//    }
//
//    override fun getLayout() = R.layout.your_fragment_view_pager
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        pager.adapter = WXPageAdapter(YourPager.allValues, requireContext(), childFragmentManager)
//        tabLayout.setupWithViewPager(pager)
//    }
//
//}
//
//class FirstPagerFragment: BaseFragment() {
//
//    companion object {
//        fun newInstance() = FirstPagerFragment()
//    }
//
//    private val viewModel: YourViewModel by viewModel()
//
//    override fun getLayout() = R.layout.fragment_page_first
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//}

interface PagerInterface {

    val item: BaseFragment
    val title: Int

    // using companion object in descendants, because enum cannot be init
    // in case we can use object with enum inside

}

class WXPageAdapter(private val pager: Array<PagerInterface>,
                    val context: Context, fm: androidx.fragment.app.FragmentManager) :
        FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = pager[position].item

    override fun getCount() = pager.size

    override fun getPageTitle(position: Int): String = context.getString(pager[position].title)

}