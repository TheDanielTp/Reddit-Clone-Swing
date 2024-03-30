package org.example;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable
{
    private static ArrayList <User> allUsers = new ArrayList <> ();

    private static ArrayList <String> allEmails    = new ArrayList <> ();
    private static ArrayList <String> allUsernames = new ArrayList <> ();
    private static ArrayList <String> allPasswords = new ArrayList <> ();

    private String email;
    private String username;
    private String password;

    private int    karma;
    private byte[] salt;

    private ArrayList <Subreddit> subreddits;
    private ArrayList <Post>      posts;
    private ArrayList <Comment>   comments;

    private static User currentUser;

    /*
    CONSTRUCTOR FUNCTIONS
    */

    public User (String email, String username, String password)
    {
        salt = generateSalt ();

        this.email = email;
        allEmails.add (email);

        this.username = username;
        allUsernames.add (username);

        password      = hashPassword (password, salt);
        this.password = password;
        allPasswords.add (password);

        subreddits = new ArrayList <> ();
        posts      = new ArrayList <> ();
        comments   = new ArrayList <> ();
    }

    public static void addUser (User user)
    {
        allUsers.add (user);
    }

    public static void setCurrentUser (User user)
    {
        currentUser = user;
    }

    public static void setAllUsers (ArrayList<User> users)
    {
        allUsers = users;
    }

    public static void setAllEmails (ArrayList<String> emails)
    {
        allEmails = emails;
    }

    public static void setAllUsernames (ArrayList<String> usernames)
    {
        allUsernames = usernames;
    }

    public static void setAllPasswords (ArrayList<String> passwords)
    {
        allPasswords = passwords;
    }

    /*
    USER FUNCTIONS
    */

    public static User findUserByUsername (String username)
    {
        for (User user : allUsers)
        {
            if (user.username.equals (username))
            {
                return user;
            }
        }
        return null;
    }

    public static void logout ()
    {
        currentUser = null;
    }

    public void joinSubreddit (Subreddit subreddit)
    {
        subreddits.add (subreddit);
    }

    public void leaveSubreddit (Subreddit subreddit)
    {
        subreddits.remove (subreddit);
    }

    /*
    ACCOUNT FUNCTIONS
    */

    public void changeUsername (String username)
    {
        allUsernames.remove (this.username);
        this.username = username;
        allUsernames.add (username);
    }

    public void changeEmail (String email)
    {
        allEmails.remove (this.email);
        this.email = email;
        allEmails.add (email);
    }

    public void changePassword (String password)
    {
        allPasswords.remove (this.password);
        password      = hashPassword (password, salt);
        this.password = password;
        allPasswords.add (password);
    }

    public void calculateKarma ()
    {
        this.karma = 0;

        for (Post post : Post.getAllPosts ())
        {
            if (post.getUser ().getUsername ().equalsIgnoreCase (this.username))
            {
                this.karma += post.getKarma ();
            }
        }

        for (Comment comment : Comment.getAllComments ())
        {
            if (comment.getPost ().getUser ().getUsername ().equalsIgnoreCase (this.username))
            {
                karma += comment.getKarma ();
            }
        }
    }

    /*
    AUTHORITY FUNCTIONS
    */

    public static int validateEmail (String email)
    {
        String regex = "^[a-zA-Z0-9_+.&*-]+(?:\\.[a-zA-Z0-9_+.&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (email);

        if (! matcher.find ())
        {
            return 0; //invalid email pattern
        }

        if (User.allEmails.isEmpty ())
        {
            return 1; //valid email
        }
        else
        {
            String[] allOwlmailsArray = new String[User.allEmails.size ()];
            User.allEmails.toArray (allOwlmailsArray);

            for (String s : allOwlmailsArray)
            {
                if (email.equalsIgnoreCase (s))
                {
                    return 2; //email's assigned to an account
                }
            }
        }
        return 1; //valid email
    }

    public static int validateUsername (String username)
    {
        if (username.length () < 6)
        {
            return 0; //username has less than six characters
        }

        if (User.allUsernames.isEmpty ())
        {
            return 1; //valid username
        }

        String[] allUserNamesArray = new String[User.allUsernames.size ()];
        User.allUsernames.toArray (allUserNamesArray);

        for (String s : allUserNamesArray)
        {
            if (username.equalsIgnoreCase (s))
            {
                return 2; //username's assigned to an account
            }
        }
        return 1; //valid username
    }

    public static int validatePassword (String firstPassword, String secondPassword)
    {
        if (! firstPassword.equals (secondPassword))
        {
            return 2; //passwords do not match
        }

        String regex = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$";

        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (firstPassword);

        if (matcher.find ())
        {
            return 0; //invalid password pattern
        }
        return 1; //valid password
    }

    public boolean checkPassword (String password, User user)
    {
        String[] allPasswordsArray = new String[User.allPasswords.size ()];
        User.allPasswords.toArray (allPasswordsArray);

        password = hashPassword (password, salt);

        assert password != null;
        return password.equals (user.password);
    }

    /*
    ENCRYPTION FUNCTIONS
    */

    public static byte[] generateSalt ()
    {
        byte[]       salt   = new byte[16];
        SecureRandom random = new SecureRandom ();
        random.nextBytes (salt);
        return salt;
    }

    public static String hashPassword (String password, byte[] salt)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance ("SHA-256");
            messageDigest.reset ();
            messageDigest.update (salt);
            byte[] hashedBytes = messageDigest.digest (password.getBytes ());

            StringBuilder stringBuilder = new StringBuilder ();
            for (byte b : hashedBytes)
            {
                stringBuilder.append (String.format ("%02x", b));
            }
            return stringBuilder.toString ();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace ();
            return null;
        }
    }

    /*
    GET-INFO FUNCTIONS
    */

    public static ArrayList <User> getAllUsers ()
    {
        return allUsers;
    }

    public static User getCurrentUser ()
    {
        return currentUser;
    }

    public static ArrayList <String> getAllEmails ()
    {
        return allEmails;
    }

    public static ArrayList <String> getAllUsernames ()
    {
        return allUsernames;
    }

    public static ArrayList <String> getAllPasswords ()
    {
        return allPasswords;
    }

    public String getEmail ()
    {
        return email;
    }

    public String getUsername ()
    {
        return username;
    }

    public String getPassword ()
    {
        return password;
    }

    public ArrayList <Subreddit> getSubreddits ()
    {
        return subreddits;
    }

    public ArrayList <Post> getPosts ()
    {
        return posts;
    }

    public ArrayList <Comment> getComments ()
    {
        return comments;
    }

    public int getKarma ()
    {
        calculateKarma ();
        return karma;
    }
}
