package dang.sneazy.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import dang.sneazy.Models.Album
import dang.sneazy.R
import kotlinx.android.synthetic.main.image_view.view.*

class AlbumAdapter(val album: Album): RecyclerView.Adapter<AlbumViewHolder>() {
    override fun getItemCount(): Int {
        if(album.is_album)
            return album.images.count()

        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.image_view, parent, false)

        return AlbumViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        if(album.is_album ){
            holder.view.descriptionView_album.text =  album.images[position].description
            if (album.images[position].type == "image/jpeg") {
                Picasso.get().load(album.images[position].link).into(holder.view.pictureView_album)
            }else{
                Glide.with(holder.view.context).asGif().load(album.images[position].link)
                    .into(holder.view.pictureView_album)
            }
        }else {
            holder.view.descriptionView_album.text =  album.description
            if (album.animated) {
                Glide.with(holder.view.context).asGif().load(album.link)
                    .into(holder.view.pictureView_album)
            }else{
                Picasso.get().load(album.link).into(holder.view.pictureView_album)

            }
        }


    }
}

class AlbumViewHolder(val view: View): RecyclerView.ViewHolder(view){

}