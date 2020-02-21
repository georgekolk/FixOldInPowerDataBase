public class Item {

    private String blogName;
    private String date;
    private String postId;
    private String tags;
    private String filenames;


    public Item(String blogName, String date, String postId, String tags, String filenames){
        this.blogName = blogName;
        this.date = date;
        this.postId = postId;
        this.tags = tags;
        this.filenames = filenames;
    }


    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFilenames() {
        return filenames;
    }

    public void setFilenames(String filenames) {
        this.filenames = filenames;
    }


    @Override
    public String toString() {
        return "blogName: ".concat(blogName).concat(", date: ").concat(date).concat(", postId: ").concat(postId).concat(", tags: ").concat(tags).concat(", filenames: ").concat(filenames);
    }

}
