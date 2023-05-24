package com.moondroid.behealthy.view.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.toast
import com.moondroid.behealthy.databinding.ActivityMainBinding
import com.moondroid.behealthy.utils.BindingAdapter.visible
import com.moondroid.behealthy.utils.setupWithNavController
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private var mBackWait = 0L

    fun mFinish() = if (System.currentTimeMillis() - mBackWait >= 2000) {
        mBackWait = System.currentTimeMillis()
        toast("뒤로가기를 한번 더 누르시면 종료됩니다.")
    } else {
        finish()
    }

    private var currentNavController: LiveData<NavController>? = null

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.itemListFragment, R.id.settingFragment, R.id.boardListFragment -> binding.bottomNavigation.visible(true)
                else -> binding.bottomNavigation.visible(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            setUpBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.nav_item,
            R.navigation.nav_board,
            R.navigation.nav_setting,
        )

        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        controller.observe(this) { navController ->
            currentNavController?.value?.removeOnDestinationChangedListener(
                onDestinationChangedListener
            )
            navController.addOnDestinationChangedListener(onDestinationChangedListener)
        }

        //currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}