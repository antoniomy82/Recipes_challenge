package com.antoniomy82.recipes_challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")

data class Recipe(
    @PrimaryKey @ColumnInfo(name = "TITLE") var title: String,
    @ColumnInfo(name = "HREF") var href: String? = "",
    @ColumnInfo(name = "INGREDIENTS") var ingredients: String? = "",
    @ColumnInfo(name = "THUMBNAIL") var thumbnail: String? = "",
)