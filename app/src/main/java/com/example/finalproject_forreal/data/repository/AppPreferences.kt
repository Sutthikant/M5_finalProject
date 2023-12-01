package com.example.finalproject_forreal.data.repository

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate


private const val KEY_FAV = "key_fav"

class AppPreferences (val activity: Activity) {
        private val preferences = activity.getSharedPreferences("favorite", Context.MODE_PRIVATE)

        fun addFavorite(id: String) {
            with(preferences.edit()) {
                val favorites = getFavorites().toMutableList()
                favorites.add(id)
                saveFavorites(favorites)
                apply()
            }
        }

        fun removeFavorite(id: String) {
            with(preferences.edit()) {
                val favorites = getFavorites().toMutableList()
                favorites.remove(id)
                saveFavorites(favorites)
                apply()
            }
        }

        fun getFavorites(): List<String> {
            val preferences = activity.getSharedPreferences("favorites", Context.MODE_PRIVATE)
                .getString(KEY_FAV, "") ?: ""
            return preferences.split(",").toList()
        }

        private fun saveFavorites(favorites: List<String>) {
            val preferences = favorites.joinToString(",")
            activity.getSharedPreferences("favorites", Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_FAV, preferences)
                .apply()
        }
    }
