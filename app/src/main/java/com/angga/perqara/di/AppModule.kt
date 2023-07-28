package com.angga.perqara.di

import androidx.room.Room
import com.angga.perqara.data.source.SehatQRepository
import com.angga.perqara.data.source.local.LocalDataSource
import com.angga.perqara.data.source.local.room.SehatQDatabase
import com.angga.perqara.data.source.remote.RemoteDataSource
import com.angga.perqara.data.source.remote.network.ApiService
import com.angga.perqara.data.source.remote.network.HeaderInterceptor
import com.angga.perqara.domain.repository.ISehatQRepository
import com.angga.perqara.domain.usecase.SehatQInteractor
import com.angga.perqara.domain.usecase.SehatQUseCase
import com.angga.perqara.ui.detail.ProductDetailViewModel
import com.angga.perqara.ui.history.PurchasedHistoryViewModel
import com.angga.perqara.ui.main.MainViewModel
import com.angga.perqara.ui.search.SearchViewModel
import com.angga.perqara.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<SehatQDatabase>().productDao() }
    factory { get<SehatQDatabase>().transactionDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            SehatQDatabase::class.java, "SehatQ.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(HeaderInterceptor(get()))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
//            .baseUrl("https://private-4639ce-ecommerce56.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ISehatQRepository> { SehatQRepository(get(), get(), get()) }
}

val useCaseModule = module {
    factory<SehatQUseCase> { SehatQInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { PurchasedHistoryViewModel(get()) }
}