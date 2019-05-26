package dang.sneazy.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import dang.sneazy.Adapters.AlbumAdapter
import dang.sneazy.Adapters.CommentAdapter
import dang.sneazy.Models.Album
import dang.sneazy.Models.CommentContainer
import dang.sneazy.R
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.toolbar.view.*
import okhttp3.*
import java.io.IOException

class AlbumActivity : AppCompatActivity() {

    private fun getComments(albumId:String) {
        println("Attempting to fetch JSON")
        commentsRV.layoutManager = LinearLayoutManager(this)
        val commentURL = "https://api.imgur.com/3/gallery/" + albumId + "/comments/best"
        val request = Request.Builder().url(commentURL)
            .addHeader("Authorization", "Client-ID a42d18df437ba18")
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val commentContainer = gson.fromJson(body, CommentContainer::class.java)
                runOnUiThread {
                    commentsRV.adapter = CommentAdapter(commentContainer)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute request")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        val albumInfo = getIntent().getSerializableExtra("CHOSEN_ALBUM") as Album

        getComments(albumInfo.id)

        val toolbar = toolbar_album as Toolbar
        toolbar.title_toolbar.text = albumInfo.title
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        picturesRV.layoutManager = LinearLayoutManager(this)
        picturesRV.adapter = AlbumAdapter(albumInfo)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        picturesRV.adapter!!.notifyDataSetChanged()
        Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show()
    }
}