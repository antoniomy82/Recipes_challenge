package com.antoniomy82.recipes_challenge.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.AdapterRecipesListBinding
import com.antoniomy82.recipes_challenge.model.Recipe
import com.antoniomy82.recipes_challenge.viewmodel.RecipesViewModel
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso


class RecipesListAdapter(private val recipesVM:RecipesViewModel, private val recipeList: List<Recipe>, private val context: Context) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {


    //Inflate view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_recipes_list,
            parent,
            false
        )
    )


    //Binding each element with object element
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.adapterRecipesListBinding.recipesVM = recipesVM
        holder.adapterRecipesListBinding.mRecipe = recipeList[position]

        //Set image
        if (recipeList[position].thumbail != null) {
            Log.d("URL", recipeList[position].thumbail.toString())
            Glide.with(context).load(recipeList[position].thumbail.toString()).into(holder.adapterRecipesListBinding.imageRecipe)
        }
/*
        //on Click item
        holder.adapterPoisDistrictListBinding.root.setOnClickListener{
            poisVm.popUpLocation=0
            PoisUtils.replaceFragment(mDistrict.pois?.get(position)?.let { it1 -> DetailFragment(it1,poisVm) }, (context as AppCompatActivity).supportFragmentManager)
        }
*/
    }


    override fun getItemCount(): Int {
        return recipeList.size
    }

    class ViewHolder(val adapterRecipesListBinding: AdapterRecipesListBinding) :
        RecyclerView.ViewHolder(adapterRecipesListBinding.root)

}
