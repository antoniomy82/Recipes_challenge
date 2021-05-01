package com.antoniomy82.recipes_challenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.FragmentRecipesHomeBinding

class RecipesHomeFragment : Fragment() {

    var fragmentRecipesHomeBinding:FragmentRecipesHomeBinding ?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentRecipesHomeBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_recipes_home, container, false)
        return fragmentRecipesHomeBinding?.root
    }

}