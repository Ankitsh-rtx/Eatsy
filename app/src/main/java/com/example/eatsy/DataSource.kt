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

    val restaurants: MutableList<Restaurants> = mutableListOf()

    var orderList : Pair<Restaurants?, HashMap<String,CartItem>> = Pair(null, HashMap())
    var orderAddress:Address?=null

    var sampleRestaurantDataList :MutableList<Item> = mutableListOf(
        Item("Steamed Momo","https://www.thespruceeats.com/thmb/T_R22QniykdQ9aPCLKIk-O22Gh4=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/steamed-momos-wontons-1957616-hero-01-1c59e22bad0347daa8f0dfe12894bc3c.jpg",
            "12","r1","momo",149,true,"momo"),
        Item("Chicken Momo","https://www.archanaskitchen.com/images/archanaskitchen/1-Author/shaikh.khalid7-gmail.com/Chicken_Momos_Recipe_Delicious_Steamed_Chicken_Dumplings.jpg",
            "13","r1","momo",199,false,"delicious momo"),
        Item("Pan Fried Momo","https://indianheartbeat.in/wp-content/uploads/2023/01/6b106be2fe5ad0b986a3450a20b94f51-scaled-1.webp",
            "14","r1","momo",179,true,"delicious momo"),
        Item("Chinese Noodles","https://bellyfull.net/wp-content/uploads/2022/08/Lo-Mein-blog-3.jpg",
            "15","r1","noodles",119,false,"delicious noodles")
//        Item("Choco Lava Cake","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pressurecookrecipes.com%2Finstant-pot-lava-cake%2F&psig=AOvVaw1GC-nGziX7Eau1fWZfFtSL&ust=1694068617475000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCNj0hcivlYEDFQAAAAAdAAAAABAJ",
//            "7","r5","Cake",109,true,"delicious Cake"),
    )
    var address : MutableList<Address>? = mutableListOf()
    var user:User?=null

    var itemSearchList: HashMap<Restaurants,MutableList<Item>> = HashMap()

}