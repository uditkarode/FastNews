package com.ukexample.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.revely.gradient.RevelyGradient
import com.ukexample.R
import com.ukexample.adapters.NewsAdapter
import com.ukexample.databinding.ActivityMainBinding
import com.ukexample.repositories.NewsRepository
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext = Dispatchers.Main + SupervisorJob()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/inter.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build())

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RevelyGradient
                .linear()
                .colors(intArrayOf(
                        Color.parseColor("#74ebd5"),
                        Color.parseColor("#ACB6E5"),
                ))
                .on(binding.headerText)

        launch(Dispatchers.IO) {
            val news = NewsRepository().getNews()
            val adapter = NewsAdapter(news = news)
            runOnUiThread {
                binding.newsRv.adapter = adapter
                binding.newsRv.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }
}