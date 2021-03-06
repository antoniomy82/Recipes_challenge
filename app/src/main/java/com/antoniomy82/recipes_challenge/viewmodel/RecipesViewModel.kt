package com.antoniomy82.recipes_challenge.viewmodel

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.FragmentRecipesHomeBinding
import com.antoniomy82.recipes_challenge.databinding.FragmentShowHrefBinding
import com.antoniomy82.recipes_challenge.model.Recipe
import com.antoniomy82.recipes_challenge.model.RecipesRepository
import com.antoniomy82.recipes_challenge.ui.RecipesHomeFragment
import com.antoniomy82.recipes_challenge.ui.RecipesListAdapter
import com.antoniomy82.recipes_challenge.utils.RecipesUtils
import java.lang.ref.WeakReference
import kotlin.system.exitProcess

class RecipesViewModel : ViewModel() {


    //Main Fragment values
    private var frgMainActivity: WeakReference<Activity>? = null
    private var frgMainContext: WeakReference<Context>? = null
    private var frgMainView: WeakReference<View>? = null
    private var mainBundle: Bundle? = null
    private var fragmentRecipesHomeBinding: FragmentRecipesHomeBinding? = null
    private var mRepository = RecipesRepository()


    //Other values
    val retrieveRecipes = MutableLiveData<List<Recipe>>()
    private var lastRecipesList: List<Recipe>? = null
    private var lastSearch: String = ""
    var isFavourite: Boolean = false
    private var recyclerView: WeakReference<RecyclerView>? = null


    //Query url
    private val urlBody: String = "http://www.recipepuppy.com/api/?i="

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
        this.fragmentRecipesHomeBinding = fragmentRecipesHomeBinding
    }


    fun setHomeHeaderBarButtonsFragment() {

        //Back arrow (Home Fragment) close app
        frgMainView?.get()?.findViewById<View>(R.id.headerBack)?.setOnClickListener {
            frgMainActivity?.get()?.finish()
            exitProcess(0)
        }

        frgMainView?.get()?.findViewById<View>(R.id.headerBack)?.visibility = View.GONE

        //Favorite button
        frgMainView?.get()?.findViewById<View>(R.id.favorite_icon)?.setOnClickListener {
            fragmentRecipesHomeBinding?.recipesTitleLayout?.visibility = View.GONE
            frgMainView?.get()?.findViewById<View>(R.id.favorite_icon)?.visibility = View.GONE
            frgMainView?.get()?.findViewById<View>(R.id.search_icon)?.visibility = View.VISIBLE
            frgMainContext?.get()?.let { it1 -> mRepository.getAllRecipes(it1, retrieveRecipes) }
            fragmentRecipesHomeBinding?.instructions?.visibility = View.GONE
            frgMainContext?.get()?.let { it1 ->
                frgMainView?.get()?.let { it2 ->
                    RecipesUtils.hideKeyboard(
                        it1,
                        it2
                    )
                }
            }
            isFavourite = true
        }

        //Search button
        frgMainView?.get()?.findViewById<View>(R.id.search_icon)?.setOnClickListener {
            fragmentRecipesHomeBinding?.recipesTitleLayout?.visibility = View.VISIBLE
            frgMainView?.get()?.findViewById<View>(R.id.favorite_icon)?.visibility = View.VISIBLE
            frgMainView?.get()?.findViewById<View>(R.id.search_icon)?.visibility = View.GONE
            isFavourite = false
        }
        checkServerResponse()
    }


    fun backShowURL(view: View, context: Context?) {
        view.findViewById<View>(R.id.headerBack)?.setOnClickListener {

            if (lastRecipesList != null) RecipesUtils.replaceFragment(
                RecipesHomeFragment(
                    lastRecipesList,
                    lastSearch
                ), (context as AppCompatActivity).supportFragmentManager
            ) else {
                RecipesUtils.replaceFragment(
                    RecipesHomeFragment(), (context as AppCompatActivity).supportFragmentManager
                )
            }
        }
    }


    fun searchRecipesButton() {

        lastSearch = fragmentRecipesHomeBinding?.etSearch?.text.toString()
        val tempUrl = urlBody + fragmentRecipesHomeBinding?.etSearch?.text.toString()
        fragmentRecipesHomeBinding?.instructions?.visibility = View.GONE

        //Hide keyboard
        frgMainContext?.get()?.let {
            frgMainView?.get()?.let { it1 ->
                RecipesUtils.hideKeyboard(
                    it,
                    it1
                )
            }
        }

        //Show progress bar
        fragmentRecipesHomeBinding?.progressBar?.visibility = View.VISIBLE

        //Call to getRecipe repository to retrieve data
        frgMainContext?.get()?.let {
            mRepository.getRecipe(
                tempUrl, it, retrieveRecipes
            ) { checkServerResponse() }
        }
    }


    //Set Recipes List in RecyclerView
    fun setRecipesRecyclerViewAdapter(mRecipes: List<Recipe>) {

        lastRecipesList = mRecipes

        recyclerView =
            WeakReference(frgMainView?.get()?.findViewById(R.id.rvRecipes) as RecyclerView)
        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(frgMainActivity?.get()) //Orientation
        recyclerView?.get()?.layoutManager = manager
        mRecipes.sortedBy { it.title }
        recyclerView?.get()?.adapter = frgMainContext?.get()?.let {
            RecipesListAdapter(
                this, mRecipes,
                it
            )
        }

        fragmentRecipesHomeBinding?.recipesVM = this

    }

    fun makeFavoriteButton(mRecipe: Recipe) {

        Toast.makeText(frgMainContext?.get(), "Recipe saved in favorite list", Toast.LENGTH_LONG)
            .show()

        frgMainContext?.get()?.let { mRepository.insertRecipe(it, mRecipe) }

    }

    private fun checkServerResponse() {
        fragmentRecipesHomeBinding?.progressBar?.visibility = View.GONE

        if (frgMainContext?.get()?.let { RecipesUtils.isOnline(it) } == false) {
            frgMainView?.get()?.findViewById<View>(R.id.connected_icon)?.visibility = View.GONE
            frgMainView?.get()?.findViewById<View>(R.id.offline_icon)?.visibility = View.VISIBLE

            fragmentRecipesHomeBinding?.instructions?.visibility = View.VISIBLE
            fragmentRecipesHomeBinding?.instructions?.text =
                frgMainContext?.get()?.getString(R.string.offline_message)
        } else {
            //Close progress bar & instructions
            fragmentRecipesHomeBinding?.progressBar?.visibility = View.GONE

            //Show instructions
            if (lastRecipesList == null) fragmentRecipesHomeBinding?.instructions?.visibility =
                View.VISIBLE

            //Set connection icons
            frgMainView?.get()?.findViewById<View>(R.id.connected_icon)?.visibility = View.VISIBLE
            frgMainView?.get()?.findViewById<View>(R.id.offline_icon)?.visibility = View.GONE
        }
    }

    //Load url with progress bar in showHrefFragment into its webView
    fun loadUrlInWebView(href: String, fragmentShowHrefBinding: FragmentShowHrefBinding) {

        //Load url
        fragmentShowHrefBinding.webViewId.webViewClient = WebViewClient()
        fragmentShowHrefBinding.webViewId.loadUrl(href)

        //Progress bar
        fragmentShowHrefBinding.webViewId.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                fragmentShowHrefBinding.progressBarWebView.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                fragmentShowHrefBinding.progressBarWebView.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }

}