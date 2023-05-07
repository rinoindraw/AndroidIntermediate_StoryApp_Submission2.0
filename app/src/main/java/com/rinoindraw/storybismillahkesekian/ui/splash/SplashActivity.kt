package com.rinoindraw.storybismillahkesekian.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rinoindraw.storybismillahkesekian.databinding.ActivitySplashBinding
import com.rinoindraw.storybismillahkesekian.ui.auth.AuthActivity
import com.rinoindraw.storybismillahkesekian.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserDirection()

    }

    private fun setUserDirection() {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getAuthToken().collect { token ->
                    if (token.isNullOrEmpty()) {
                        binding.imgLogo.alpha = 0f
                        binding.imgLogo.animate().setDuration(1500).alpha(1f).withEndAction {
                            val intentSplash = Intent(this@SplashActivity, AuthActivity::class.java)
                            startActivity(intentSplash)
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                            finish()
                        }
                    } else {
                        binding.imgLogo.alpha = 0f
                        binding.imgLogo.animate().setDuration(1500).alpha(1f).withEndAction {
                            val intentSplash = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intentSplash)
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                            finish()
                        }
                    }
                }
            }
        }
    }
}