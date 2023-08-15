package com.example.eatsy

import com.example.eatsy.model.Dishes
import com.example.eatsy.model.Restaurants

object DataSource {
    val dish: List<Dishes> = listOf(
        Dishes(
            R.drawable.burger,
            "Burger",

        ),
        Dishes(
            R.drawable.pizza,
            "Pizza",

        ),
        Dishes(
            R.drawable.fried_chicken,
            "Chicken",
        ),
        Dishes(
            R.drawable.spaguetti,
            "Noddles",
        ),
        Dishes(
            R.drawable.sandwich,
            "Sandwich",
        ),
        Dishes(
            R.drawable.chinese_noodles,
            "Chinese",
        ),
        Dishes(
            R.drawable.ice_cream,
            "Ice Cream",
        ),
        Dishes(
            R.drawable.juice,
            "Drinks",
        ),
        Dishes(
            R.drawable.cake,
            "Cake",
        ),
        Dishes(
            R.drawable.application,
            "More",
        )
    )

    val restaurants: List<Restaurants> = listOf(
        Restaurants(
            R.drawable.pizza_restaurant_image,
            "Pizza Hut",
            "Pizza"
        ),
        Restaurants(
            R.drawable.wow_momo,
            "WOW! Momo",
            "Momo"
        )

    )

}