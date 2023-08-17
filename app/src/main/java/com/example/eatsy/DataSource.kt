package com.example.eatsy

import com.example.eatsy.model.CartItem
import com.example.eatsy.model.Dishes
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants

object DataSource {
    val dish: List<Dishes> = listOf(
        Dishes(
            R.drawable.image_burger,
            "Burger",

        ),
        Dishes(
            R.drawable.image_pizza,
            "Pizza",

        ),
        Dishes(
            R.drawable.image_fried_chicken,
            "Chicken",
        ),
        Dishes(
            R.drawable.image_spaguetti,
            "Noddles",
        ),
        Dishes(
            R.drawable.image_sandwich,
            "Sandwich",
        ),
        Dishes(
            R.drawable.image_chinese_noodles,
            "Chinese",
        ),
        Dishes(
            R.drawable.image_ice_cream,
            "Ice Cream",
        ),
        Dishes(
            R.drawable.image_juice,
            "Drinks",
        ),
        Dishes(
            R.drawable.image_cake,
            "Cake",
        ),
        Dishes(
            R.drawable.image_application,
            "More",
        )
    )

    val restaurants: List<Restaurants> = listOf(
        Restaurants(
            R.drawable.image_pizza_restaurant,
            "Pizza Hut",
            "Pizza"
        ),
        Restaurants(
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo"
        )

    )

    val items: List<Item> = listOf(
        Item(R.drawable.image_pizza_restaurant,"Pizza","",399),
        Item(R.drawable.image_pizza_restaurant,"Mo-burg","",399),
        Item(R.drawable.image_pizza_restaurant,"Burger","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"Sandwich","",399)
    )

    val orders: List<CartItem> = listOf(
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249),
        CartItem("Chicken Pizza",1,249)
    )

}