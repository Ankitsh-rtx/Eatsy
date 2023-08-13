import androidx.annotation.DrawableRes

class Dishes(
    @DrawableRes val imageResourceId: Int,
    val name: String,

) {
    fun getDishName(): String {
        return name
    }

}