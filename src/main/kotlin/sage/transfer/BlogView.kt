package sage.transfer

import sage.entity.Blog
import sage.entity.BlogStat
import java.sql.Timestamp

class BlogView {
  var id: Long = 0
  var author: UserLabel? = null
  var title: String = ""
  var content: String = ""
  var whenCreated: Timestamp? = null
  var whenModified: Timestamp? = null
  var tags: List<TagLabel> = arrayListOf()

  var likes: Int = 0
  var views: Int = 0

  internal constructor() {
  }

  constructor(blog: Blog) {
    id = blog.id
    author = UserLabel(blog.author)

    title = blog.title
    content = blog.content
    whenCreated = blog.whenCreated
    whenModified = actualWhenModified(blog.whenCreated, blog.whenModified)

    tags = blog.tags.map { TagLabel(it) }

    val stat = blog.stat()
    likes = stat.likes
    views = stat.views
  }
}
