package dang.sneazy.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dang.sneazy.Models.CommentContainer
import dang.sneazy.R
import kotlinx.android.synthetic.main.comments_view.view.*

class CommentAdapter(val commentContainer: CommentContainer): RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.comments_view, parent, false)

        return CommentViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return commentContainer.data.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentCommentData = commentContainer.data[position]
        holder.view.comment_text.text = currentCommentData.comment
        holder.view.comment_upCount.text = currentCommentData.ups.toString()
        holder.view.comment_downCount.text = currentCommentData.downs.toString()
    }
}
class CommentViewHolder(val view: View): RecyclerView.ViewHolder(view){

}