package com.antoniomy82.recipes_challenge.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("DELETE FROM Recipe WHERE title =:title")
    fun deleteRecipe(title: String)

    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): List<Recipe>

}