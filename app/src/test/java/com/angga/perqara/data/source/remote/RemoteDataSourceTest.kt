package com.angga.perqara.data.source.remote

import com.angga.perqara.data.source.remote.network.ApiResponse
import com.angga.perqara.data.source.remote.network.ApiService
import com.angga.perqara.data.source.remote.response.ProductListResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.collect
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTest {

    private val apiService = mock<ApiService>()
    private val remoteDataSource = RemoteDataSource(apiService)

    @Test
    fun `flow emits successfully`() = runBlocking {

        // Mock API Service
        val productListResponse = ProductListResponse(ProductData.products)
        val response: Response<ProductListResponse> = Response.success(productListResponse)
        apiService.stub {
            onBlocking { getHome() } doReturn response
        }
        // Test
        val flow = remoteDataSource.getHome()

        // Verify
        flow.collect { result ->
            when (result) {
                is ApiResponse.Success -> {
                    result.data shouldBeEqualTo productListResponse
                }
            }
        }

    }

}