package org.example;

import java.io.Serializable;
import java.util.ArrayList;

public class Subreddit implements Serializable
{
    private static ArrayList <Subreddit> allSubreddits = new ArrayList <> ();

    private String title;
    private String description;

    private User creator;

    private ArrayList <Post> posts;
    private ArrayList <User> members;
    private ArrayList <User> admins;

    private boolean nsfw;

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

    public static void setAllSubreddits (ArrayList<Subreddit> subreddits)
    {
        allSubreddits = subreddits;
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
