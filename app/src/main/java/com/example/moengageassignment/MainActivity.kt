package com.example.moengageassignment

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        // Set custom  icon for the menu
        val drawable = ContextCompat.getDrawable(this, R.drawable.baseline_menu_24)
        toolbar.overflowIcon = drawable

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val newsResponse = fetchNews()
            val sortedArticles = newsResponse.articles.sortedByDescending { it.publishedAt }
            adapter = NewsAdapter(sortedArticles)
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_spinner, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_new_to_old -> {
                adapter.sortByNewToOld()
                true
            }
            R.id.action_sort_old_to_new -> {
                adapter.sortByOldToNew()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private suspend fun fetchNews(): NewsResponse {
        return withContext(Dispatchers.IO) {
            val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
            val jsonString = url.readText()
            Gson().fromJson(jsonString, NewsResponse::class.java)
        }
    }
}





