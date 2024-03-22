package org.example.Post;

import java.util.ArrayList;

public class Post
{
    protected static ArrayList<Post> allPosts = new ArrayList <> ();

    protected String title;
    protected String postText;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Post (String title, String postText)
    {
        this.title = title;
        this.postText = postText;
    }

    public static void addPost (Post post)
    {
        allPosts.add (post);
    }

    /*
    DISPLAY FUNCTIONS
    */

    public static void displayAllPosts ()
    {
        for (Post post : allPosts)
        {
            displayPost (post);
        }
    }

    public static void displayPost (Post post)
    {
        System.out.println (post.title);
        System.out.println (post.postText);
    }

    /*
    GET-INFO FUNCTIONS
    */

    public String getTitle ()
    {
        return title;
    }

    public String getPostText ()
    {
        return postText;
    }

    public static ArrayList<Post> getAllPosts ()
    {
        return allPosts;
    }
}
