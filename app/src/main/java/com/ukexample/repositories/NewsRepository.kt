package com.ukexample.repositories

import android.util.Log
import com.ukexample.models.News
import com.ukexample.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.Exception

class NewsRepository {

    companion object {
        private val okhttp = OkHttpClient()
    }

    fun getNews(): ArrayList<News> {
        val call = okhttp.newCall(
            Request.Builder()
                .url(Constants.NewsEndpoint)
                .get().build()
        )

        val response = call.execute().body?.string() ?: throw Exception("Check your connection!")

        val articles = JSONObject(response).getJSONArray("articles")

        val ret = ArrayList<News>()
        for(i in 0 until articles.length()) {
            val article = articles.getJSONObject(i)

            ret.add(News(
                title = article.getString("title"),
                author = article.getString("author"),
                imgUrl = article.getString("urlToImage"),
            ))
        }

        return ret
    }
}