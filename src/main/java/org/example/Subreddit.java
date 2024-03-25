package org.example;

import java.util.ArrayList;

public class Subreddit
{
    protected static ArrayList <Subreddit> allSubreddits = new ArrayList <> ();

    protected String name;
    protected String description;

    protected ArrayList <Post> posts;
    protected ArrayList <User> members;
    protected ArrayList <User> admins;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Subreddit (String name, String description)
    {
        this.name        = name;
        this.description = description;
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
            if (subreddit.name.equalsIgnoreCase (name))
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

    public String getName ()
    {
        return name;
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
