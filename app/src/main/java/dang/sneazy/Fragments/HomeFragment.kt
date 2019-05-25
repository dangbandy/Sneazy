package dang.sneazy.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.GsonBuilder
import dang.sneazy.Adapters.MainAdapter
import dang.sneazy.Models.Album
import dang.sneazy.Models.Gallery

import dang.sneazy.R
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private var currentAdapter = MainAdapter(Gallery(ArrayList<Album>()))
    private var page = 0

    private fun fetchJson() {
        println("Attempting to fetch JSON")

        val baseURL = "https://api.imgur.com/3/gallery/hot/time/month/0?showViral=true&mature=true&album_previews=true"
        val request = Request.Builder().url(baseURL)
            .addHeader("Authorization","Client-ID a42d18df437ba18")
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()
                val gallery = gson.fromJson(body,Gallery::class.java)
                currentAdapter = MainAdapter(gallery)
                activity?.runOnUiThread {
                    recyclerView_main.adapter = currentAdapter
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute request")
            }
        })
    }

    fun getNextData(page: Int){
        val baseURL = "https://api.imgur.com/3/gallery/hot/time/month/" + page + "?showViral=true&mature=true&album_previews=true"
        val request = Request.Builder().url(baseURL)
            .addHeader("Authorization","Client-ID a42d18df437ba18")
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val nextGal = gson.fromJson(body,Gallery::class.java)
                currentAdapter.addData(nextGal)
            }
            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute request")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fetchJson()
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val toolbar = rootView.findViewById(R.id.toolbar_home) as Toolbar
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        val recyclerView_main = rootView.findViewById(R.id.recyclerView_main) as RecyclerView // Add this
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView_main.layoutManager = layoutManager

        recyclerView_main.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItems = layoutManager.findFirstVisibleItemPositions(null)
                var pastVisibleItems = 0

                if (firstVisibleItems != null && firstVisibleItems.isNotEmpty()) {
                    pastVisibleItems = firstVisibleItems[0]
                }

                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    Toast.makeText(activity, "Got next stuff", Toast.LENGTH_SHORT).show()
                    page += 1
                    getNextData(page)
                    println(currentAdapter)
                }
            }
        })

        return rootView
    }


}
