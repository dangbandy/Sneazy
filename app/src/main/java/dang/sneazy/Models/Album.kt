package dang.sneazy.Models

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
    val images: ArrayList<Picture>,
    val comment_count: Int,
    val ups: Int,
    val downs: Int) : Serializable {

    private var commentContainer = CommentContainer(ArrayList<Comment>())

    fun setCommentContainer(newCommentContainer: CommentContainer){
        commentContainer = newCommentContainer
    }

}