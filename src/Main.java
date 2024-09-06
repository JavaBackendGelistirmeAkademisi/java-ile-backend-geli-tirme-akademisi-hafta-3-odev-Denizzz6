import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Instagram'a Hoş geldiniz.");


        User user1 = new User("Deniz");
        User user2 = new User("Yunus");
        User user3=new User("nadir");


        user1.follow(user2);

        user2.createPost("Vlogum için takipte kalınnnnn");

        user1.addCommentToPost(user2, 0, "Kesinlikle orada olacağımm");

        user1.addToPostFavorites(user2, 0);

        user1.showFeed();

        user1.showPosts();

        user3.createPost("Vlog için konuk olacağım. Takipte kalınnnn");

        user2.addCommentToPost(user1,0,"Geleceğimmm");

        user2.showFeed();

        user2.showPosts();
    }

    static class User {
        private String name;
        private LinkedHashMap<Integer, Post> posts; // Kullanıcının gönderileri
        private HashSet<User> following; // Takip edilen kullanıcılar
        private TreeSet<Post> favorites; // Beğenilen Gönderiler
        private static int postCounter = 0; // Gönderi Sayacı

        public User(String name) {
            this.name = name;
            this.posts = new LinkedHashMap<>();
            this.following = new HashSet<>();
            this.favorites = new TreeSet<>();
        }

        public String getName() {
            return name;
        }

        public void follow(User user) {
            following.add(user);
            System.out.println(name + ", " + user.getName() + " kullanıcısını takip ediyor.");
        }

        public void createPost(String content) {
            Post newPost = new Post(postCounter++, this, content);
            posts.put(newPost.getId(), newPost);
            System.out.println(name + " yeni bir gönderi yayınladı: " + content);
        }

        public void addCommentToPost(User user, int postId, String comment) {
            Post post = user.getPost(postId);
            if (post != null) {
                post.addComment(new Comment(this, comment));
                System.out.println(name + ", " + user.getName() + "'in gönderisine yorum yaptı: " + comment);
            }
        }

        public void addToPostFavorites(User user, int postId) {
            Post post = user.getPost(postId);
            if (post != null) {
                favorites.add(post);
                System.out.println(name + ", " + user.getName() + "'in gönderisini beğendi: " + post.getContent());
            }
        }

        public Post getPost(int postId) {
            return posts.get(postId);
        }

        public void showPosts() {
            if (posts.isEmpty()) {
                System.out.println(name + " henüz bir gönderi paylaşmadı.");
            } else {
                System.out.println("\n" + name + "'in Gönderileri:");
                for (Post post : posts.values()) {
                    System.out.println("Gönderi #" + post.getId() + ": " + post.getContent());
                    for (Comment comment : post.getComments()) {
                        System.out.println("Yorum: " + comment.getAuthor().getName() + " - " + comment.getContent());
                    }
                    System.out.println("-----------------------------");
                }
            }
        }

        public void showFeed() {
            if (following.isEmpty()) {
                System.out.println(name + " kimseyi takip etmiyor.");
            } else {
                System.out.println("\n" + name + "'in Ana Sayfası:");
                for (User user : following) {
                    user.showPosts(); // Takip edilen kişinin gönderilerini göster
                }
            }
        }

        static class Post implements Comparable<Post> {
            private int id;
            private User author;
            private String content;
            private ArrayList<Comment> comments;

            public Post(int id, User author, String content) {
                this.id = id;
                this.author = author;
                this.content = content;
                this.comments = new ArrayList<>();
            }

            public int getId() {
                return id;
            }

            public String getContent() {
                return content;
            }

            public ArrayList<Comment> getComments() {
                return comments;
            }

            public void addComment(Comment comment) {
                comments.add(comment);
            }

            @Override
            public int compareTo(Post otherPost) {
                return Integer.compare(this.id, otherPost.id);
            }
        }

        static class Comment {
            private User author;
            private String content;

            public Comment(User author, String content) {
                this.author = author;
                this.content = content;
            }

            public User getAuthor() {
                return author;
            }

            public String getContent() {
                return content;
            }
        }
    }
}
