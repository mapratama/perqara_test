package com.angga.perqara.data.source.remote.network

import com.angga.perqara.data.source.remote.response.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(ENDPOINT_LIST)
    suspend fun getHome(@Query("key") key: String = KEY,
                        @Query("page") page: Int = 1,
                        @Query("page_siza") pageSize: Int = 10)
    : Response<ProductListResponse>
}
