package com.antoniomy82.recipes_challenge.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.FragmentRecipesHomeBinding
import com.antoniomy82.recipes_challenge.model.Recipe
import com.antoniomy82.recipes_challenge.model.RecipesRepository
import com.antoniomy82.recipes_challenge.ui.RecipesListAdapter
import java.lang.ref.WeakReference

class RecipesViewModel: ViewModel() {


    //Main Fragment values
    private var frgMainActivity: WeakReference<Activity>? = null
    private var frgMainContext: WeakReference<Context>? = null
    private var frgMainView: WeakReference<View>? = null
    private var mainBundle: Bundle? = null
    private var fragmentRecipesHomeBinding:FragmentRecipesHomeBinding ?=null
    private var mRepository=RecipesRepository()

    //Other values
    val retrieveRecipes  = MutableLiveData<List<Recipe>>()


    //Query values
    private val urlBody: String = "http://www.recipepuppy.com/api/?i="
    private val queryUrl: String = "&q="


    //Set Main fragment parameters in this VM
    fun setRecipesFragmentBinding(
        frgActivity: Activity,
        frgContext: Context,
        frgView: View,
        mainBundle: Bundle?,
        fragmentRecipesHomeBinding: FragmentRecipesHomeBinding
    ) {
        this.frgMainActivity = WeakReference(frgActivity)
        this.frgMainContext = WeakReference(frgContext)
        this.frgMainView = WeakReference(frgView)
        this.mainBundle = mainBundle
        this.fragmentRecipesHomeBinding=fragmentRecipesHomeBinding
    }


    fun searchRecipesButton(){

        val tempUrl = urlBody +fragmentRecipesHomeBinding?.etSearch?.text.toString()
       mRepository.getRecipe(tempUrl, frgMainContext?.get()!!,retrieveRecipes)
    }


    //Set District List RecyclerView
    fun setRecipesRecyclerViewAdapter(mRecipes: List<Recipe>) {

        val mRecycler: RecyclerView =
            frgMainView?.get()?.findViewById(R.id.rvRecipes) as RecyclerView
        val recyclerView: RecyclerView = mRecycler
        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(frgMainActivity?.get()) //Orientation
        recyclerView.layoutManager = manager
        recyclerView.adapter = frgMainContext?.get()?.let {
            RecipesListAdapter(
                this, mRecipes,
                it
            )
        }


        fragmentRecipesHomeBinding?.recipesVM=this
        /*
        fragmentDistrictListBinding?.progressBar?.visibility = View.GONE
        fragmentDistrictListBinding?.mapLayout?.visibility = View.VISIBLE

         */
    }
}