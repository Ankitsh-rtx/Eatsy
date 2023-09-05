package com.example.eatsy

import com.example.eatsy.model.*

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
            R.drawable.image_more,
            "More",
        )
    )

    val items1: ArrayList<Item> = arrayListOf(
//        Item(R.drawable.image_wow_momo,"1","Chicken Momo","",399),
//        Item(R.drawable.image_wow_momo,"2","Mo-burg","",399),
//        Item(R.drawable.image_wow_momo,"3","Burger","",399),
//        Item(R.drawable.image_wow_momo,"4","Sandwich","",399),
//        Item(R.drawable.image_wow_momo,"5","Sandwich","",399),

    )

    val items: ArrayList<Item> = arrayListOf(
//        Item(R.drawable.image_pizza_restaurant,"1","Pizza","",399),
//        Item(R.drawable.image_pizza_restaurant,"2","Mo-burg","",399),
//        Item(R.drawable.image_pizza_restaurant,"3","Burger","",399),
//        Item(R.drawable.image_pizza_restaurant,"4","Sandwich","",399),
//        Item(R.drawable.image_pizza_restaurant,"5","Sandwich","",399),
//        Item(R.drawable.image_pizza_restaurant,"6","Sandwich","",399),
//        Item(R.drawable.image_pizza_restaurant,"7","Sandwich","",399)

    )
    private val address = Address("Kolkata","India",700039,"Bidhannagar")

    val restaurants: MutableList<Restaurants> = mutableListOf()

    var orderList : Pair<Restaurants?, HashMap<String,CartItem>> = Pair(null, HashMap())
    val menu : ArrayList<String> = arrayListOf(
        "1","2"
    )

    var sampleRestaurantDataList :MutableList<Restaurants> = mutableListOf(
        Restaurants("r3","https://b.zmtcdn.com/data/pictures/chains/7/37327/3d7a8a7b601bee632f81581dca960a0e.jpg?output-format=webp&fit=around|771.75:416.25&crop=771.75:416.25;*,*",
            "KFC","fast food",4.6,true,true,items,menu, address),
        Restaurants("r4","https://b.zmtcdn.com/data/pictures/chains/2/20842/ce89a2b9888ae02bc47cb3643bfd5738.jpg?fit=around|771.75:416.25&crop=771.75:416.25;*,*",
            "Barbeque Nation","fast food",4.2,true,true,items,menu, address),
        Restaurants("r5","https://www.jubilantfoodworks.com/images/brand_pic3.jpg",
            "Dominos","Pizza",4.5,true,true,items,menu, address)
    )



}