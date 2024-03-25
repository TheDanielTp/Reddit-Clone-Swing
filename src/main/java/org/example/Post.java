package org.example;

import java.util.ArrayList;

public class Post
{
    protected static ArrayList <Post> allPosts = new ArrayList <> ();

    protected Subreddit subreddit;
    protected User user;

    protected String    title;
    protected String    content;

    protected int karma;

    protected ArrayList<String> comments;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Post (String title, String content, Subreddit subreddit, User user)
    {
        this.title     = title;
        this.content  = content;
        this.subreddit = subreddit;
        this.user = user;
    }

    public static void addPost (Post post)
    {
        allPosts.add (post);
    }

    /*
    POST FUNCTIONS
    */

    public void upVote ()
    {
        karma++;
    }

    public void downVote ()
    {
        karma--;
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
        System.out.println (post.content);
    }

    /*
    GET-INFO FUNCTIONS
    */

    public static ArrayList <Post> getAllPosts ()
    {
        return allPosts;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getContent ()
    {
        return content;
    }

    public int getKarma ()
    {
        return karma;
    }

    public Subreddit getSubreddit ()
    {
        return subreddit;
    }

    public User getUser ()
    {
        return user;
    }

    public ArrayList<String> getComments ()
    {
        return comments;
    }
}
