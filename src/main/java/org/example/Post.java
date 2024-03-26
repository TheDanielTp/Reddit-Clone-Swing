package org.example;

import java.util.ArrayList;

public class Post
{
    protected static ArrayList <Post> allPosts = new ArrayList <> ();

    protected Subreddit subreddit;
    protected User      user;

    protected String title;
    protected String content;

    protected int karma;

    protected ArrayList <Comment> comments;

    protected ArrayList <User> upVotedUsers;
    protected ArrayList <User> downVotedUsers;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Post (String title, String content, Subreddit subreddit, User user)
    {
        this.title     = title;
        this.content   = content;
        this.subreddit = subreddit;
        this.user      = user;

        comments = new ArrayList<> ();
        upVotedUsers = new ArrayList <> ();
        downVotedUsers = new ArrayList<> ();
    }

    public static void addPost (Post post)
    {
        allPosts.add (post);
    }

    /*
    POST FUNCTIONS
    */

    public void upVote (User user)
    {
        if (downVotedUsers != null)
        {
            if (downVotedUsers.remove (user))
            {
                karma++;
            }
        }

        if (upVotedUsers != null)
        {
            if (upVotedUsers.contains (user))
            {
                upVotedUsers.remove (user);
                karma--;
            }
        }
        else
        {
            upVotedUsers.add (user);
            karma++;
        }
    }

    public void downVote (User user)
    {
        if (upVotedUsers != null)
        {
            if (upVotedUsers.remove (user))
            {
                karma--;
            }
        }

        if (downVotedUsers != null)
        {
            if (downVotedUsers.contains (user))
            {
                downVotedUsers.remove (user);
                karma++;
            }
        }
        else
        {
            downVotedUsers.add (user);
            karma--;
        }
    }

    public void addComment (Comment comment)
    {
        comments.add (comment);
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

    public ArrayList <Comment> getComments ()
    {
        return comments;
    }
}
