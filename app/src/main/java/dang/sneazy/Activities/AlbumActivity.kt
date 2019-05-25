package dang.sneazy.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import dang.sneazy.Adapters.AlbumAdapter
import dang.sneazy.Models.Album
import dang.sneazy.R
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.toolbar.view.*

class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        val albumInfo = getIntent().getSerializableExtra("CHOSEN_ALBUM") as Album

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