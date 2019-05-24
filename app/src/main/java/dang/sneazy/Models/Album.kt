package dang.sneazy.Models

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import java.io.Serializable

class Album (
    val is_album: Boolean,
    val animated: Boolean,
    val id: String,
    val title: String,
    val link: String,
    val type: String,
    val mp4: String,
    val description: String,
    val images: List<Picture>,
    val ups: Int,
    val downs: Int) : Serializable {

}