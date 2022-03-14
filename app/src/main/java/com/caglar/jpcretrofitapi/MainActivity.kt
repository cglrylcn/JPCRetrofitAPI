package com.caglar.jpcretrofitapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caglar.jpcretrofitapi.model.CryptoModel
import com.caglar.jpcretrofitapi.service.CryptoAPI
import com.caglar.jpcretrofitapi.ui.theme.JPCRetrofitAPITheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPCRetrofitAPITheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val BASE_URL = "https://raw.githubusercontent.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)
    val call = retrofit.getData()
    call.enqueue(object : Callback<List<CryptoModel>> {
        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }

        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    it.forEach {
                        println(it.currency)
                        println(it.price)
                    }
                }
            }
        }

    })

    Scaffold(topBar = {AppBar()}) {
        
    }
}

@Composable
fun AppBar() {
    TopAppBar(contentPadding = PaddingValues(5.dp)) {
        Text(text = "Retrofit Compose", fontSize = 24.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JPCRetrofitAPITheme {
        MainScreen()
    }
}