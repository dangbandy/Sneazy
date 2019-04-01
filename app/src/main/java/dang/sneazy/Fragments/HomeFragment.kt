package dang.sneazy.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.GsonBuilder
import dang.sneazy.Adapters.MainAdapter
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

    private fun fetchJson() {
        println("Attempting to fetch JSON")

        val baseURL = "https://api.imgur.com/3/gallery/hot/time/month/1?showViral=true&mature=true&album_previews=true"
        val request = Request.Builder().url(baseURL)
            .addHeader("Authorization","Client-ID a42d18df437ba18")
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()
                val gallery = gson.fromJson(body,Gallery::class.java)

                activity?.runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(gallery)
                }

                println(body)
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
        recyclerView_main.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        return rootView
    }


}
