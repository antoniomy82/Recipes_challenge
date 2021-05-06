package com.antoniomy82.recipes_challenge.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.antoniomy82.recipes_challenge.R
import com.antoniomy82.recipes_challenge.databinding.AdapterRecipesListBinding
import com.antoniomy82.recipes_challenge.model.Recipe
import com.antoniomy82.recipes_challenge.utils.RecipesUtils
import com.antoniomy82.recipes_challenge.viewmodel.RecipesViewModel
import com.squareup.picasso.Picasso


class RecipesListAdapter(
    private val recipesVM: RecipesViewModel,
    private val recipeList: List<Recipe>,
    private val context: Context
) :
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
        if (recipeList[position].thumbnail != null) {
            Picasso.get().load(recipeList[position].thumbnail.toString())
                .placeholder(R.mipmap.no_image)
                .into(holder.adapterRecipesListBinding.imageRecipe)
        }


        //Set background color so that different cells are noticeable
        if (position % 2 == 0) holder.adapterRecipesListBinding.root.setBackgroundColor(
            Color.parseColor(
                "#E3E3E3"
            )
        )
        else holder.adapterRecipesListBinding.root.setBackgroundColor(Color.parseColor("#dce6ee"))


        //Contains lactose
        if (recipeList[position].ingredients?.contains("milk") == true || recipeList[position].ingredients?.contains(
                "cheese"
            ) == true || recipeList[position].ingredients?.contains("butter") == true || recipeList[position].ingredients?.contains(
                "cream"
            ) == true
        ) {
            holder.adapterRecipesListBinding.containLactoseText.visibility = View.VISIBLE
        }


        //on Click item - open URL
        holder.adapterRecipesListBinding.root.setOnClickListener {

            RecipesUtils.replaceFragment(recipeList[position].href?.let { it1 ->
                ShowHrefFragment(
                    it1,
                    recipesVM
                )
            }, (context as AppCompatActivity).supportFragmentManager)
        }

        //on Click  make favorite button
        holder.adapterRecipesListBinding.btnMakeFavorite.setOnClickListener {

            holder.adapterRecipesListBinding.btnMakeFavorite.visibility = View.GONE
            recipesVM.makeFavoriteButton(recipeList[position], holder.adapterRecipesListBinding)
        }

        if (recipesVM.isFavourite) holder.adapterRecipesListBinding.btnMakeFavorite.visibility =
            View.GONE
        else holder.adapterRecipesListBinding.btnMakeFavorite.visibility = View.VISIBLE

    }


    override fun getItemCount(): Int {
        return recipeList.size
    }

    class ViewHolder(val adapterRecipesListBinding: AdapterRecipesListBinding) :
        RecyclerView.ViewHolder(adapterRecipesListBinding.root)

}
