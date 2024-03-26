package org.example;

import java.util.ArrayList;

public class Comment
{
    protected static ArrayList<Comment> allComments = new ArrayList<> ();

    protected Subreddit subreddit;

    protected User   user;
    protected String contents;
    protected Post post;

    protected ArrayList <User> upVotedUsers;
    protected ArrayList <User> downVotedUsers;

    protected int karma;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public Comment (User user, Post post, String contents)
    {
        this.user = user;
        this.contents = contents;

        upVotedUsers = new ArrayList<> ();
        downVotedUsers = new ArrayList<> ();
    }

    public static void addComment (Comment comment)
    {
        allComments.add (comment);
    }

    /*
    COMMENT FUNCTIONS
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
