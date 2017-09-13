package popularpenguin.trainingapp.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import popularpenguin.trainingapp.*

class CategoryAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 5

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return CombativesFragment()
            1 -> return CombosFragment()
            2 -> return DefensesFragment()
            3 -> return WeaponsFragment()
            4 -> return UserFragment()

            else -> throw IllegalArgumentException("Invalid ViewPager position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return context.getString(R.string.c_tab)
            1 -> return context.getString(R.string.co_tab)
            2 -> return context.getString(R.string.d_tab)
            3 -> return context.getString(R.string.w_tab)
            4 -> return context.getString(R.string.u_tab)

            else -> throw IllegalArgumentException("Invalid ViewPager title")
        }
    }
}
