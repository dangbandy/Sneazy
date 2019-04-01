package dang.sneazy.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dang.sneazy.Activities.AlbumActivity
import dang.sneazy.Models.Gallery
import dang.sneazy.R
import kotlinx.android.synthetic.main.preview_view.view.*

class MainAdapter(val gallery: Gallery): RecyclerView.Adapter<MainAdapter.GalleryViewHolder>() {

    val listOfTitles = listOf("First title","Second","Third")

    //number of items
    override fun getItemCount(): Int {
        return gallery.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        //how to create a view?
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.preview_view, parent, false)

        return GalleryViewHolder(cellForRow)

    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val currentAlbumData = gallery.data[position]
        val currentAlbumView = holder.view


        currentAlbumView.titleView_preview.text = currentAlbumData.title
        currentAlbumView.upCount_preview.text = currentAlbumData.ups.toString()
        currentAlbumView.downCount_preview.text = currentAlbumData.downs.toString()

        //There is an issue with playing mp4/gifv
        if(!currentAlbumData.is_album){
            if (currentAlbumData.animated){
                Glide.with(holder.view.context).asGif().load(currentAlbumData.link)
                    .into(currentAlbumView.imageView_preview)
            }else{
                //Glide.with(holder.view.context).load(currentAlbumData.link)
                //    .into(currentAlbumView.imageView_preview)
                Picasso.get().load(currentAlbumData.link).into(currentAlbumView.imageView_preview)
            }
        }else{
            if (currentAlbumData.images[0].type == "image/jpeg") {
                //Glide.with(holder.view.context).load(currentAlbumData.images[0].link)
                 //   .into(currentAlbumView.imageView_preview)
                Picasso.get().load(currentAlbumData.images[0].link).into(currentAlbumView.imageView_preview)
            }else{
                Glide.with(holder.view.context).asGif().load(currentAlbumData.images[0].link)
                    .into(currentAlbumView.imageView_preview)

            }

            currentAlbumView.setOnClickListener {
                val intent = Intent(holder.view.context, AlbumActivity::class.java)
                intent.putExtra("CHOSEN_ALBUM", currentAlbumData )
                holder.view.context.startActivity(intent)
            }
        }



    }

    class GalleryViewHolder(val view: View): RecyclerView.ViewHolder(view){
    }
}

