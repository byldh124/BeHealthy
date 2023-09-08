package com.moondroid.behealthy.view.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.Extensions.toast
import com.moondroid.behealthy.common.IntentParam
import com.moondroid.behealthy.databinding.ActivityHomeBinding
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.domain.usecase.application.TutorialUseCase
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseActivity
import com.moondroid.behealthy.view.ui.board.BoardFragment
import com.moondroid.behealthy.view.ui.item.ItemListFragment
import com.moondroid.behealthy.view.ui.item.add.ItemAddActivity
import com.moondroid.behealthy.view.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private val binding by viewBinding(ActivityHomeBinding::inflate)
    private var mBackWait = 0L

    @Inject
    lateinit var tutorialUseCase: TutorialUseCase

    private val fragments = arrayOf(
        ItemListFragment(), BoardFragment(), SettingFragment()
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

        CoroutineScope(Dispatchers.Main).launch {
            tutorialUseCase().collect {
                debug("tutorial : $it")
            }
        }
    }

    private fun initView() {
        //initialize bottom navigation

        val transaction = supportFragmentManager.beginTransaction()
        fragments.forEach {
            transaction.add(R.id.fragment_container, it)
        }
        transaction.commit()
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
        try {val transaction = supportFragmentManager.beginTransaction()
            fragments.forEach {
                transaction.hide(it)
            }
            transaction.show(fragment)
            transaction.commit()
        } catch (e: Exception) {
            e.logException()
        }
    }

    private val addItem = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            onAdd()
        }
    }

    private var onAdd: () -> Unit = {}

    fun toAddItem(list: List<Item>?, result: () -> Unit) {
        onAdd = result
        addItem.launch(Intent(mContext, ItemAddActivity::class.java).apply {
            putExtra(IntentParam.ITEMS, Gson().toJson(list))
        })
    }
}