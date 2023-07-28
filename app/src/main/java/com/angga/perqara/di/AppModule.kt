package com.angga.perqara.di

import androidx.room.Room
import com.angga.perqara.data.source.PerqaraRepository
import com.angga.perqara.data.source.local.LocalDataSource
import com.angga.perqara.data.source.local.room.PerqaraDatabase
import com.angga.perqara.data.source.remote.RemoteDataSource
import com.angga.perqara.data.source.remote.network.ApiService
import com.angga.perqara.data.source.remote.network.HeaderInterceptor
import com.angga.perqara.domain.repository.IPerqaraRepository
import com.angga.perqara.domain.usecase.PerqaraInteractor
import com.angga.perqara.domain.usecase.PerqaraUseCase
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
    factory { get<PerqaraDatabase>().productDao() }
    factory { get<PerqaraDatabase>().transactionDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            PerqaraDatabase::class.java, "SehatQ.db"
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
    single<IPerqaraRepository> { PerqaraRepository(get(), get(), get()) }
}

val useCaseModule = module {
    factory<PerqaraUseCase> { PerqaraInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { PurchasedHistoryViewModel(get()) }
}