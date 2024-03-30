package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Post implements Serializable
{
    private static ArrayList <Post> allPosts = new ArrayList <> ();

    private Subreddit subreddit;
    private User      user;

    private String title;
    private String content;

    private int karma;

    private ArrayList <Comment> comments;

    private ArrayList <User> upVotedUsers;
    private ArrayList <User> downVotedUsers;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Post (String title, String content, Subreddit subreddit, User user)
    {
        this.title     = title;
        this.content   = content;
        this.subreddit = subreddit;
        this.user      = user;

        comments       = new ArrayList <> ();
        upVotedUsers   = new ArrayList <> ();
        downVotedUsers = new ArrayList <> ();
    }

    public static void addPost (Post post)
    {
        allPosts.add (post);
    }

    public static void setAllPosts (ArrayList<Post> posts)
    {
        allPosts = posts;
    }

    /*
    POST FUNCTIONS
    */

    public void upVote (User user)
    {
        if (upVotedUsers == null)
        {
            upVotedUsers = new ArrayList <> ();
        }
        if (! upVotedUsers.contains (user))
        {
            upVotedUsers.add (user);
            karma++;

            if (downVotedUsers != null && downVotedUsers.contains (user))
            {
                downVotedUsers.remove (user);
                karma++;
            }
        }
        else
        {
            upVotedUsers.remove (user);
            karma--;
        }
    }

    public void downVote (User user)
    {
        if (downVotedUsers == null)
        {
            downVotedUsers = new ArrayList <> ();
        }
        if (! downVotedUsers.contains (user))
        {
            downVotedUsers.add (user);
            karma--;

            if (upVotedUsers != null && upVotedUsers.contains (user))
            {
                upVotedUsers.remove (user);
                karma--;
            }
        }
        else
        {
            downVotedUsers.remove (user);
            karma++;
        }
    }

    public void addComment (Comment comment)
    {
        comments.add (comment);
    }

    public static void reverseAllPosts ()
    {
        Collections.reverse (allPosts);
    }

    public void setKarma (int karma)
    {
        this.karma = karma;
    }

    public static void removePost (Post post)
    {
        allPosts.remove (post);
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

    public ArrayList<User> getUpVotedUsers ()
    {
        return upVotedUsers;
    }

    public ArrayList<User> getDownVotedUsers ()
    {
        return downVotedUsers;
    }
}
