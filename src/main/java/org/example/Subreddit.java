package org.example;

import java.util.ArrayList;

public class Subreddit
{
    protected static ArrayList <Subreddit> allSubreddits = new ArrayList <> ();

    protected String title;
    protected String description;

    protected User creator;

    protected ArrayList <Post> posts;
    protected ArrayList <User> members;
    protected ArrayList <User> admins;

    protected boolean nsfw;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Subreddit (String title, String description, User creator, boolean nsfw)
    {
        this.title       = title;
        this.description = description;
        this.creator     = creator;

        posts   = new ArrayList <> ();
        members = new ArrayList <> ();
        admins  = new ArrayList <> ();

        members.add (creator);
        admins.add (creator);

        this.nsfw = nsfw;
    }

    public static void addSubreddit (Subreddit subreddit)
    {
        allSubreddits.add (subreddit);
    }

    /*
    SUBREDDIT FUNCTIONS
    */

    public static Subreddit findSubreddit (String name)
    {
        for (Subreddit subreddit : allSubreddits)
        {
            if (subreddit.title.equalsIgnoreCase (name))
            {
                return subreddit;
            }
        }
        return null;
    }

    public void addPost (Post post)
    {
        posts.add (post);
    }

    public void addMember (User user)
    {
        members.add (user);
    }

    public void addAdmin (User user)
    {
        admins.add (user);
    }

    /*
    GET-INFO FUNCTIONS
    */

    public static ArrayList <Subreddit> getAllSubreddits ()
    {
        return allSubreddits;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getDescription ()
    {
        return description;
    }

    public ArrayList <Post> getPosts ()
    {
        return posts;
    }

    public ArrayList <User> getMembers ()
    {
        return members;
    }

    public ArrayList <User> getAdmins ()
    {
        return admins;
    }
}
