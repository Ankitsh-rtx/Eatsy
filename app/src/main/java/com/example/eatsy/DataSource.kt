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
    val orderAddress=Address("near Community hall","Asansol","India",713325,"Rambandh","West Bengal")

    var sampleRestaurantDataList :MutableList<Item> = mutableListOf(
        Item("Pizza","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DgUlsgxzY9GU&psig=AOvVaw31fPOVe6NOew5kV1kP_TMM&ust=1694068680660000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCPCimuWvlYEDFQAAAAAdAAAAABAW",
            "3","r3","Pizza",109,true,"delicious pizza"),
        Item("Vada Paw","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cookwithmanali.com%2Fvada-pav%2F&psig=AOvVaw1TUc6olfUarmLvOEXCthrt&ust=1694068741223000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCLCng4GwlYEDFQAAAAAdAAAAABAE",
            "4","r3","Paw",79,true,""),
        Item("Corn Cheese Pizaa","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DgUlsgxzY9GU&psig=AOvVaw31fPOVe6NOew5kV1kP_TMM&ust=1694068680660000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCPCimuWvlYEDFQAAAAAdAAAAABAW",
            "5","r4","Pizza",109,true,"delicious pizza"),
        Item("Sandwich","https://www.google.com/url?sa=i&url=https%3A%2F%2Frecipes.timesofindia.com%2Frecipes%2Fclub-sandwich%2Frs83740315.cms&psig=AOvVaw1ToSeMDSbZB9PyJvRA_jGJ&ust=1694068766370000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCJC5qIuwlYEDFQAAAAAdAAAAABAJ",
            "6","r5","Sandwich",89,true,"delicious pizza"),
        Item("Choco Lava Cake","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pressurecookrecipes.com%2Finstant-pot-lava-cake%2F&psig=AOvVaw1GC-nGziX7Eau1fWZfFtSL&ust=1694068617475000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCNj0hcivlYEDFQAAAAAdAAAAABAJ",
            "7","r5","Cake",109,true,"delicious Cake"),
    )
    val address : MutableList<Address> = mutableListOf(
        Address("near Community hall","Asansol","India",713325,"Rambandh","West Bengal"),
        Address("near Community hall","Asansol","India",713325,"Rambandh","West Bengal")
    )
    var user:User?=null

}