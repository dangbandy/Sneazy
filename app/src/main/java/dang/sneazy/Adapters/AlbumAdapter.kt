package dang.sneazy.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
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

    override fun onViewRecycled(holder: AlbumViewHolder) {
        super.onViewRecycled(holder)
        if(holder.view.mp4View.player != null){
            holder.view.mp4View.player.release()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.image_view, parent, false)

        return AlbumViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        if(album.is_album ){
            holder.view.descriptionView_album.text =  album.images[position].description
            if (album.images[position].type == "image/jpeg" || album.images[position].type == "image/png" ) {
                //Picasso.get().load(currentAlbumData.images[0].link).into(currentAlbumView.imageView_preview)
                Picasso.get().load(album.images[position].link).into(holder.view.pictureView_album)
            }else{
                val mGifvView = holder.view.mp4View
                val mPlayer = ExoPlayerFactory.newSimpleInstance(holder.view.context, DefaultTrackSelector(),
                    DefaultLoadControl()
                )
                mPlayer.playWhenReady = false
                mGifvView.player = mPlayer

                val uri = Uri.parse(album.images[position].mp4)
                val dataSourceFactory = DefaultDataSourceFactory(holder.view.context, Util.getUserAgent(holder.view.context, "Sneazy"), null)

                val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
                //val videoSource = ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
                mPlayer.prepare(videoSource)
//                //player = mPlayer
            }
        }else {
            holder.view.descriptionView_album.text =  album.description
            if (album.animated) {
                val mGifvView = holder.view.mp4View
                val mPlayer = ExoPlayerFactory.newSimpleInstance(holder.view.context, DefaultTrackSelector(),
                    DefaultLoadControl()
                )
                mPlayer.playWhenReady = false
                mGifvView.player = mPlayer

                val uri = Uri.parse(album.mp4)
                val dataSourceFactory = DefaultDataSourceFactory(holder.view.context, Util.getUserAgent(holder.view.context, "Sneazy"), null)

                val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
                //val videoSource = ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
                mPlayer.prepare(videoSource)

            }else{
                Picasso.get().load(album.link).into(holder.view.pictureView_album)

            }
        }


    }
}

class AlbumViewHolder(val view: View): RecyclerView.ViewHolder(view){

}