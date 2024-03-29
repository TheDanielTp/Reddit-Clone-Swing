package org.example;

import java.io.*;
import java.util.ArrayList;

public class DataManager
{
    public static void saveData ()
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream (new FileOutputStream ("Data")))
        {
            outputStream.writeBoolean (Main.reversed); //save reversed boolean in the file
            outputStream.writeObject (User.getAllUsers ()); //save all users list in the file
            outputStream.writeObject (Post.getAllPosts ()); //save all posts list in the file
            outputStream.writeObject (Comment.getAllComments ()); //save comments users list in the file
            outputStream.writeObject (Subreddit.getAllSubreddits ()); //save all subreddits list in the file
        }
        catch (IOException e) //throw exception
        {
            e.printStackTrace ();
        }
    }

    public static void loadData ()
    {
        try (ObjectInputStream inputStream = new ObjectInputStream (new FileInputStream ("Data")))
        {
            Main.reversed = inputStream.readBoolean (); //load reversed boolean from the file

            //load all users list from the file
            ArrayList <User> loadedUsers = (ArrayList <User>) inputStream.readObject ();
            User.setAllUsers (loadedUsers);

            //load all posts list from the file
            ArrayList <Post> loadedPosts = (ArrayList <Post>) inputStream.readObject ();
            Post.setAllPosts (loadedPosts);

            //load all comments list from the file
            ArrayList <Comment> loadedComments = (ArrayList <Comment>) inputStream.readObject ();
            Comment.setAllComments (loadedComments);

            //load all subreddits list from the file
            ArrayList <Subreddit> loadedSubreddits = (ArrayList <Subreddit>) inputStream.readObject ();
            Subreddit.setAllSubreddits (loadedSubreddits);
        }
        catch (IOException | ClassNotFoundException e) //throw exception
        {
            e.printStackTrace ();
        }
    }
}
