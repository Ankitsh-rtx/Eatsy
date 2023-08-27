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

    val items1: ArrayList<Item> = arrayListOf(
        Item(R.drawable.image_wow_momo,"1","Chicken Momo","",399),
        Item(R.drawable.image_wow_momo,"2","Mo-burg","",399),
        Item(R.drawable.image_wow_momo,"3","Burger","",399),
        Item(R.drawable.image_wow_momo,"4","Sandwich","",399),
        Item(R.drawable.image_wow_momo,"5","Sandwich","",399),

    )

    val items: ArrayList<Item> = arrayListOf(
        Item(R.drawable.image_pizza_restaurant,"1","Pizza","",399),
        Item(R.drawable.image_pizza_restaurant,"2","Mo-burg","",399),
        Item(R.drawable.image_pizza_restaurant,"3","Burger","",399),
        Item(R.drawable.image_pizza_restaurant,"4","Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"5","Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"6","Sandwich","",399),
        Item(R.drawable.image_pizza_restaurant,"7","Sandwich","",399)

    )

    val restaurants: List<Restaurants> = listOf(
        Restaurants(
            "1",
            R.drawable.image_pizza_restaurant,
            "Pizza Huts",
            "Pizza",
            4.2,
            "Lake Mall",
            items
        ),
        Restaurants(
            "2",
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo",
            4.0,
            "Park Circus",
            items1
        ),
        Restaurants(
            "3",
            R.drawable.image_pizza_restaurant,
            "Pizza Huts",
            "Pizza",
            4.2,
            "Lake Mall",
            items
        ),
        Restaurants(
            "4",
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo",
            4.0,
            "Park Circus",
            items1
        ),
        Restaurants(
            "5",
            R.drawable.image_pizza_restaurant,
            "Pizza Huts",
            "Pizza",
            4.2,
            "Lake Mall",
            items
        ),
        Restaurants(
            "6",
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo",
            4.0,
            "Park Circus",
            items1
        ),
        Restaurants(
            "5",
            R.drawable.image_pizza_restaurant,
            "Pizza Huts",
            "Pizza",
            4.2,
            "Lake Mall",
            items
        ),
        Restaurants(
            "6",
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo",
            4.0,
            "Park Circus",
            items1
        ),
        Restaurants(
            "5",
            R.drawable.image_pizza_restaurant,
            "Pizza Huts",
            "Pizza",
            4.2,
            "Lake Mall",
            items
        ),
        Restaurants(
            "6",
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo",
            4.0,
            "Park Circus",
            items1
        ),
        Restaurants(
            "5",
            R.drawable.image_pizza_restaurant,
            "Pizza Huts",
            "Pizza",
            4.2,
            "Lake Mall",
            items
        ),
        Restaurants(
            "6",
            R.drawable.image_wow_momo,
            "WOW! Momo",
            "Momo",
            4.0,
            "Park Circus",
            items1
        )

    )

    val orderList : HashMap<String,CartItem> = HashMap()

}