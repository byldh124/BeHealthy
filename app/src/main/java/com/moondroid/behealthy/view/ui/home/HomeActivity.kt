package com.moondroid.behealthy.view.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.Extensions.toast
import com.moondroid.behealthy.databinding.ActivityHomeBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity(R.layout.activity_home) {
    private val binding by viewBinding(ActivityHomeBinding::inflate)
    private var mBackWait = 0L

    private val fragments = arrayOf(
        ItemFragment(), BoardFragment(), SettingFragment()
    )

    fun mFinish() {
        if (binding.bottomNavigation.selectedItemId != R.id.homeFragment) {
            binding.bottomNavigation.selectedItemId = R.id.homeFragment
        } else {
            if (System.currentTimeMillis() - mBackWait >= 2000) {
                mBackWait = System.currentTimeMillis()
                toast("뒤로 가기를 한번 더 누르시면 종료됩니다.")
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mFinish()
            }
        })
    }

    private fun initView() {
        //initialize bottom navigation
        binding.bottomNavigation.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        changeFragment(fragments[0])
                    }

                    R.id.boardFragment -> {
                        changeFragment(fragments[1])
                    }

                    R.id.settingFragment -> {
                        changeFragment(fragments[2])
                    }
                }
                true
            }
            selectedItemId = R.id.homeFragment
        }
    }

    /**
     * BottomNavigationView 클릭시 Fragment 전환
     */
    @SuppressLint("CommitTransaction")
    private fun changeFragment(fragment: Fragment) {
        try {
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                .commit()
        } catch (e: Exception) {
            e.logException()
        }
    }
}