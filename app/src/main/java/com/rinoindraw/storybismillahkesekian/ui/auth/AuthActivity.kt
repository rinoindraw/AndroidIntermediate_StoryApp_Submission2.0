package com.rinoindraw.storybismillahkesekian.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rinoindraw.storybismillahkesekian.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}