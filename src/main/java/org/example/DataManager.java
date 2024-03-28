package org.example;

import java.io.*;
import java.util.ArrayList;

public class DataManager
{
    // Method to save all data to a file
    public static void saveData (String fileName)
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream (new FileOutputStream (fileName)))
        {
            // Save allUsers
            outputStream.writeObject (User.getAllUsers ());
            // Save allPosts
            outputStream.writeObject (Post.getAllPosts ());
            // Save allComments
            outputStream.writeObject (Comment.getAllComments ());
            // Save allSubreddits
            outputStream.writeObject (Subreddit.getAllSubreddits ());
            // Save other data if needed
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    // Method to load all data from a file
    public static void loadData (String fileName)
    {
        try (ObjectInputStream inputStream = new ObjectInputStream (new FileInputStream (fileName)))
        {
            // Load allUsers
            User.setAllUsers ((ArrayList <User>) inputStream.readObject ());
            // Load allPosts
            Post.setAllPosts ((ArrayList <Post>) inputStream.readObject ());
            // Load allComments
            Comment.setAllComments ((ArrayList <Comment>) inputStream.readObject ());
            // Load allSubreddits
            Subreddit.setAllSubreddits ((ArrayList <Subreddit>) inputStream.readObject ());
            // Load other data if needed
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace ();
        }
    }
}
