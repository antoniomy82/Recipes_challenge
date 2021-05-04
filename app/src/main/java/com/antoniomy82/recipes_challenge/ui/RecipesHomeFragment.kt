package com.antoniomy82.recipes_challenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.FragmentRecipesHomeBinding
import com.antoniomy82.recipes_challenge.model.RecipesRepository
import com.antoniomy82.recipes_challenge.viewmodel.RecipesViewModel

class RecipesHomeFragment : Fragment() {

    var fragmentRecipesHomeBinding: FragmentRecipesHomeBinding? = null
    var recipesViewModel: RecipesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentRecipesHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recipes_home, container, false)
        return fragmentRecipesHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        fragmentRecipesHomeBinding?.recipesVM = recipesViewModel


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



        recipesViewModel?.retrieveRecipes?.observe(viewLifecycleOwner){retrieveList ->
            Log.d("retrieve",retrieveList.toString())
            recipesViewModel?.setRecipesRecyclerViewAdapter(retrieveList)
        }


    }

}