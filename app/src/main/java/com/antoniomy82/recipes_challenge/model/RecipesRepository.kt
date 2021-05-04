package com.antoniomy82.recipes_challenge.model


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


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
                                        tittle = recipeObj.getString("title"),
                                        href = recipeObj.getString("href"),
                                        ingredients = recipeObj.getString("ingredients"),
                                        thumbail = recipeObj.getString("thumbnail")
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

}
