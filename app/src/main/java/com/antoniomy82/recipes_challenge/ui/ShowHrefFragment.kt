package com.antoniomy82.recipes_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.FragmentShowHrefBinding
import com.antoniomy82.recipes_challenge.viewmodel.RecipesViewModel



class ShowHrefFragment(private val href:String, val recipesVM:RecipesViewModel) : Fragment() {

    private var fragmentShowHrefBinding:FragmentShowHrefBinding ?=null
    var recipesViewModel: RecipesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentShowHrefBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_show_href, container, false)
        retainInstance = true //Save state
        return fragmentShowHrefBinding?.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        recipesViewModel = recipesVM
        fragmentShowHrefBinding?.recipesVM = recipesViewModel

        //load url in webView
        fragmentShowHrefBinding?.let { recipesViewModel?.loadUrlInWebView(href, it) }

        //Back home recyclerView when press back arrow
        recipesViewModel?.backShowURL(view, context)


    }
}