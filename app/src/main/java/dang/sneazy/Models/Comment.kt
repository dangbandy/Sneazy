package dang.sneazy.Models

import java.io.Serializable

class Comment(val id: String, val comment: String, val ups: Int, val downs: Int): Serializable {
}