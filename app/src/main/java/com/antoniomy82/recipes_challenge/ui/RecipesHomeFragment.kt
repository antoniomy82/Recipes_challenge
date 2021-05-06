package com.antoniomy82.recipes_challenge.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.FragmentRecipesHomeBinding
import com.antoniomy82.recipes_challenge.model.Recipe
import com.antoniomy82.recipes_challenge.viewmodel.RecipesViewModel

class RecipesHomeFragment(
    private val retrieveRecipes: List<Recipe>? = null,
    private val lastSearch: String? = null
) : Fragment() {

    private var fragmentRecipesHomeBinding: FragmentRecipesHomeBinding? = null
    var recipesViewModel: RecipesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentRecipesHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recipes_home, container, false)

        retainInstance = true //Save state

        return fragmentRecipesHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        fragmentRecipesHomeBinding?.recipesVM = recipesViewModel


        //Set fragment values in its view model
        activity?.let {
            context?.let { it1 ->
                fragmentRecipesHomeBinding?.let { it2 ->
                    recipesViewModel?.setRecipesFragmentBinding(
                        it,
                        it1,
                        view,
                        savedInstanceState,
                        it2
                    )
                }
            }
        }

        //Load Close app when press back arrow
        recipesViewModel?.setHomeHeaderBarButtonsFragment()

        //When pressed back arrow return to the last search (from ShowHrefFragment)
        if (retrieveRecipes != null) {
            recipesViewModel?.setRecipesRecyclerViewAdapter(retrieveRecipes)
            fragmentRecipesHomeBinding?.etSearch?.setText(lastSearch)
        }

        //Observer get retrieve recipes list, and load recycler view
        recipesViewModel?.retrieveRecipes?.observe(viewLifecycleOwner) { retrieveList ->
            Log.d("retrieve", retrieveList.toString())
            recipesViewModel?.setRecipesRecyclerViewAdapter(retrieveList)
        }

    }

}