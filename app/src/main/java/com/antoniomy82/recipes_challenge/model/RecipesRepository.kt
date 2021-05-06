package com.antoniomy82.recipes_challenge.model


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject


class RecipesRepository {

    fun getRecipe(url: String, context: Context, retrieveMutable : MutableLiveData<List<Recipe>>) {

        val volleyRequest: RequestQueue? = Volley.newRequestQueue(context)
        val recipeList = mutableListOf<Recipe>()

            volleyRequest?.add(
                JsonObjectRequest(
                    Request.Method.GET, url, { response: JSONObject ->
                        try {

                            val resultArray = response.getJSONArray("results")

                            for (i in 0 until resultArray.length()) {

                                val recipeObj = resultArray.getJSONObject(i)

                                recipeList.add(
                                    Recipe(
                                        title = recipeObj.getString("title"),
                                        href = recipeObj.getString("href"),
                                        ingredients = recipeObj.getString("ingredients"),
                                        thumbnail = recipeObj.getString("thumbnail")
                                    )
                                )
                            }
                            retrieveMutable.value=recipeList

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    },
                    { error: VolleyError? ->
                        try {
                            Log.d("Error:", error.toString())

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    })
            )
        }


    /**
     * Access SQL lite block -- Offline mode
     */


    //DB instance
    private var recipeDataBase: RecipeDataBase? = null

    //LiveData objects from recipeDB
    private var recipeList: LiveData<Recipe>? = null


    private fun initializeDB(context: Context): RecipeDataBase? {
        return RecipeDataBase.getDatabaseClient(context)
    }


    fun insertRecipe(context: Context, recipe: Recipe) {

        recipeDataBase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            recipeDataBase?.recipeDAO()?.insertRecipe(recipe)
        }

    }



    fun deleteRecipe(context: Context, title: String) {
        recipeDataBase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                recipeDataBase?.recipeDAO()?.deleteRecipe(title)
            } catch (e: Exception) {
                Log.e("__deleteError", e.toString())
            }

        }

    }

    fun getAllRecipes(context: Context, mRecipeList : MutableLiveData<List<Recipe>>){
        recipeDataBase = initializeDB(context)

        val mList = mutableListOf<Recipe>()

        CoroutineScope(Dispatchers.IO).launch {

            val mSize = recipeDataBase?.recipeDAO()?.getAllRecipes()?.size ?: 0

            for (i in 0 until mSize) {
                recipeDataBase?.recipeDAO()?.getAllRecipes()?.get(i)?.let {
                    mList.add(
                        i,
                        it
                    )
                }
            }
            mRecipeList.postValue(mList)
        }

    }

}
