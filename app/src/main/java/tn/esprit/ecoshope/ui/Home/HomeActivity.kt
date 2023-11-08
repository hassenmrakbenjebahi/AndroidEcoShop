package tn.esprit.ecoshope.ui.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPageAdapter()
        setBottomNavigation()
        setViewPagerListener()
    }

    private fun setViewPagerListener() {
        binding.Viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigationView.selectedItemId = when (position) {
                    0 -> R.id.HomePage
                    1 -> R.id.scanMenu
                    2 -> R.id.recentscan
                    3 -> R.id.favourites
                    4 -> R.id.editProfile
                    else -> R.id.HomePage
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            binding.Viewpager.currentItem = when (it.itemId) {
                R.id.HomePage -> 0
                R.id.scanMenu -> 1
                R.id.recentscan -> 2
                R.id.favourites -> 3
                R.id.editProfile -> 4
                else -> 0
            }
            true
        }
    }

    private fun viewPageAdapter() {
        binding.Viewpager.adapter = HomeAdapter(supportFragmentManager)
    }
}