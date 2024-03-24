package org.example;

import java.util.ArrayList;

public class Comment
{
    protected static ArrayList<Comment> allComments = new ArrayList<> ();

    protected Subreddit subreddit;

    protected User   user;
    protected String contents;

    protected int karma;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Comment (User user, Subreddit subreddit, String contents)
    {
        this.user = user;
        this.subreddit = subreddit;
        this.contents = contents;
    }

    public static void addComment (Comment comment)
    {
        allComments.add (comment);
    }

    /*
    GET-INFO FUNCTIONS
    */

    public static ArrayList<Comment> getAllComments ()
    {
        return allComments;
    }

    public Subreddit getSubreddit ()
    {
        return subreddit;
    }

    public User getUser ()
    {
        return user;
    }

    public String getContents ()
    {
        return contents;
    }

    public int getKarma ()
    {
        return karma;
    }
}
