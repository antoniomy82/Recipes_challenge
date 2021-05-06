package com.antoniomy82.recipes_challenge.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Recipe::class],
    version = 1
)

abstract  class RecipeDataBase: RoomDatabase() {

    abstract fun recipeDAO() : RecipeDAO

    companion object{

        private var RecipeINSTANCE : RecipeDataBase ?=null

        fun getDatabaseClient(context: Context): RecipeDataBase? {


            if(RecipeINSTANCE !=null) return RecipeINSTANCE

            synchronized(RecipeDataBase::class.java){
                RecipeINSTANCE =
                    Room.databaseBuilder(context, RecipeDataBase::class.java, "RecipeListDB").fallbackToDestructiveMigration().build()
                return RecipeINSTANCE
            }
        }
    }
}