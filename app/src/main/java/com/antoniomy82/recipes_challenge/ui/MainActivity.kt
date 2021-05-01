package com.antoniomy82.recipes_challenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.utils.RecipesUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load Main fragment
        RecipesUtils.replaceFragment(RecipesHomeFragment(), supportFragmentManager)
    }
}