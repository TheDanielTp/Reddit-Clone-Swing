package org.example;

import java.io.*;
import java.util.ArrayList;

public class DataManager
{
    public static void saveData ()
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream (new FileOutputStream ("Data")))
        {
            outputStream.writeObject (User.getCurrentUser ()); //save current user in the file

            outputStream.writeBoolean (Main.reversed); //save reversed boolean in the file

            outputStream.writeObject (User.getAllUsers ()); //save all users list in the file

            outputStream.writeObject (User.getAllEmails ()); //save all emails list in the file

            outputStream.writeObject (Post.getAllPosts ()); //save all posts list in the file

            outputStream.writeObject (User.getAllUsernames ()); //save all usernames list in the file

            outputStream.writeObject (Comment.getAllComments ()); //save comments users list in the file

            outputStream.writeObject (User.getAllPasswords ()); //save all passwords list in the file

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
            User.setCurrentUser ((User) inputStream.readObject ()); //load current user from the file

            Main.reversed = inputStream.readBoolean (); //load reversed boolean from the file

            //load all users list from the file
            ArrayList <User> loadedUsers = (ArrayList <User>) inputStream.readObject ();
            User.setAllUsers (loadedUsers);

            //load all emails list from the file
            ArrayList <String> loadedEmails = (ArrayList <String>) inputStream.readObject ();
            User.setAllEmails (loadedEmails);

            //load all posts list from the file
            ArrayList <Post> loadedPosts = (ArrayList <Post>) inputStream.readObject ();
            Post.setAllPosts (loadedPosts);

            //load all usernames list from the file
            ArrayList <String> loadedUsernames = (ArrayList <String>) inputStream.readObject ();
            User.setAllUsernames (loadedUsernames);

            //load all comments list from the file
            ArrayList <Comment> loadedComments = (ArrayList <Comment>) inputStream.readObject ();
            Comment.setAllComments (loadedComments);

            //load all passwords list from the file
            ArrayList <String> loadedPasswords = (ArrayList <String>) inputStream.readObject ();
            User.setAllPasswords (loadedPasswords);

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
