package dang.sneazy.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.squareup.picasso.Picasso
import dang.sneazy.Activities.AlbumActivity
import dang.sneazy.Models.Album
import dang.sneazy.Models.Gallery
import dang.sneazy.R
import kotlinx.android.synthetic.main.preview_view.view.*




class MainAdapter(gallery: Gallery): RecyclerView.Adapter<MainAdapter.GalleryViewHolder>() {
    //number of items
    var mGallery = gallery
    override fun getItemCount(): Int {
        return mGallery.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        //how to create a view?
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.preview_view, parent, false)
        return GalleryViewHolder(cellForRow)

    }

    override fun onViewRecycled(holder: GalleryViewHolder) {
        super.onViewRecycled(holder)
        if(holder.view.mp4_preview.player != null){
            holder.view.mp4_preview.player.release()
        }

    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val currentAlbumData = mGallery.data[position]
        val currentAlbumView = holder.view

        currentAlbumView.imageView_preview.setImageResource(0)

        currentAlbumView.titleView_preview.text = currentAlbumData.title
        currentAlbumView.upCount_preview.text = currentAlbumData.ups.toString()
        currentAlbumView.downCount_preview.text = currentAlbumData.downs.toString()

        if(!currentAlbumData.is_album){
            if (currentAlbumData.animated){
                if(currentAlbumData.mp4 == null){
                    Picasso.get().load(currentAlbumData.link).into(currentAlbumView.imageView_preview)
                }
                else{
                    val mGifvView = currentAlbumView.mp4_preview
                    val mPlayer = ExoPlayerFactory.newSimpleInstance(holder.view.context, DefaultTrackSelector(),
                        DefaultLoadControl())
                    mPlayer.playWhenReady = false
                    mPlayer.volume = 0.0F
                    mGifvView.player = mPlayer

                    val uri = Uri.parse(currentAlbumData.mp4)
                    val dataSourceFactory = DefaultDataSourceFactory(holder.view.context, Util.getUserAgent(holder.view.context, "Sneazy"), null)

                    val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
                    mPlayer.prepare(videoSource)
                }

            }else{
                Picasso.get().load(currentAlbumData.link).into(currentAlbumView.imageView_preview)
            }
        }else{
            if (currentAlbumData.images[0].type == "image/jpeg" || currentAlbumData.images[0].type == "image/png"){
                //is picture
                Picasso.get().load(currentAlbumData.images[0].link).into(currentAlbumView.imageView_preview)
            }else {
                //is gif or gifv
                if(currentAlbumData.images[0].mp4 == null){
                    Glide.with(currentAlbumView).asGif().load(currentAlbumData.link).into(currentAlbumView.imageView_preview)
                }
                else{
                    val mGifvView = currentAlbumView.mp4_preview
                    val mPlayer = ExoPlayerFactory.newSimpleInstance(
                        holder.view.context, DefaultTrackSelector(),
                        DefaultLoadControl()
                    )
                    mPlayer.playWhenReady = false
                    mPlayer.volume = 0.0F
                    mGifvView.player = mPlayer

                    val uri = Uri.parse(currentAlbumData.images[0].mp4)
                    val dataSourceFactory = DefaultDataSourceFactory(
                        holder.view.context,
                        Util.getUserAgent(holder.view.context, "Sneazy"),
                        null
                    )
                    val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
                    mPlayer.prepare(videoSource)
                }
            }
        }
        currentAlbumView.setOnClickListener {
            Toast.makeText(holder.view.context, "Item Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.view.context, AlbumActivity::class.java)
            intent.putExtra("CHOSEN_ALBUM", currentAlbumData )
            holder.view.context.startActivity(intent)
        }
    }

    fun addData(newGallery: Gallery) {
        val jointAlbum = ArrayList<Album>()
        jointAlbum.addAll(mGallery.data)
        jointAlbum.addAll(newGallery.data)
        mGallery = Gallery(jointAlbum)
    }

    class GalleryViewHolder(val view: View): RecyclerView.ViewHolder(view){}
}

