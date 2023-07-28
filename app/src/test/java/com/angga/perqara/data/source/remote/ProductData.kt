package com.angga.perqara.data.source.remote

import com.angga.perqara.data.source.remote.response.ProductResponse
import com.google.gson.annotations.SerializedName

object ProductData {

    val products = listOf(
        ProductResponse(
            id = 3498,
            slug = "grand-theft-auto-v",
            name = "Grand Theft Auto V",
            imageUrl = "https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg",
            released = "2013-09-07",
            rating = 4.47F,
            playtime = 74
        ),
        ProductResponse(
            id = 3499,
            slug = "grand-theft-auto-w",
            name = "Grand Theft Auto W",
            imageUrl = "https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg",
            released = "2013-09-07",
            rating = 4.47F,
            playtime = 74
        ),
        ProductResponse(
            id = 3500,
            slug = "grand-theft-auto-x",
            name = "Grand Theft Auto X",
            imageUrl = "https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg",
            released = "2013-09-07",
            rating = 4.47F,
            playtime = 74
        )
    )

}