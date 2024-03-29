package org.example.Menu;

import org.example.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class FrontPageGuestMenu extends JFrame implements Serializable
{
    public FrontPageGuestMenu ()
    {
        if (User.getCurrentUser () != null) //make sure no user is logged in
        {
            dispose (); //close the current frame
            DataManager.saveData ();
            new FrontPageMenu (); //open front page menu
        }

        setTitle ("Reddit");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (800, 600); //set window size
        setLocationRelativeTo (null); //center align the frame on the screen

        JPanel mainPanel = new JPanel (); //create main panel

        /*
        CREATING TOP PANEL
        */

        JPanel topPanel = new JPanel (new BorderLayout ()); //create top panel

        JButton returnButton = new JButton ("Return to Front Page"); //create a button for returning
        returnButton.addActionListener (e ->
        {
            dispose ();
            DataManager.saveData ();
            new FrontPageGuestMenu ();
        });

        JTextField searchBar = new JTextField (20); //create a text field for search bar

        JButton searchButton = new JButton ("Search"); //create a button for searching
        searchButton.addActionListener (e -> //add action to the button
        {
            String search = searchBar.getText (); //get content of the search bar

            ArrayList <Post> filteredPosts;
            if (search.startsWith ("r/")) //search for subreddits if search begin with r/
            {
                String subredditName = search.substring (2); //exclude r/ from search string
                filteredPosts = filterPostsBySubreddit (subredditName);
            }
            else if (search.startsWith ("u/")) //search for users if search begin with u/
            {
                String username = search.substring (2); //exclude u/ from search string
                filteredPosts = filterPostsByUsername (username);
            }
            else //search for posts if search is normal
            {
                filteredPosts = filterPostsByTitle (search);
            }

            displayFilteredPosts (filteredPosts, mainPanel); //display the filtered results
        });

        //add buttons to the top panel
        topPanel.add (returnButton, BorderLayout.WEST);
        topPanel.add (searchBar, BorderLayout.CENTER);
        topPanel.add (searchButton, BorderLayout.EAST);

        /*
        CREATING BOTTOM PANEL
        */

        JPanel bottomPanel = new JPanel (new GridLayout (1, 4)); //create bottom panel

        JButton createSubredditButton = new JButton ("Create Subreddit");
        createSubredditButton.addActionListener (e ->
                JOptionPane.showMessageDialog (null, "You need to be Logged in to Create a Subreddit."));

        JButton createPostButton = new JButton ("Create Post"); //create a button for creating posts
        createPostButton.setBackground (new Color (0x0079d3)); //set button color to blue
        createPostButton.setForeground (new Color (0xffffff)); //set text color to white
        createPostButton.addActionListener (e ->
                JOptionPane.showMessageDialog (null, "You need to be Logged in to Create a Post."));

        JButton viewNotificationsButton = new JButton ("View Notifications"); //create a button for viewing notifications
        viewNotificationsButton.setBackground (new Color (0xff4500)); //set button color to orange
        viewNotificationsButton.setForeground (new Color (0xffffff)); //set text color to white
        viewNotificationsButton.addActionListener (e ->
                JOptionPane.showMessageDialog (null, "You need to be Logged in to View Notifications."));

        JButton viewMyProfileButton = new JButton ("Sign In / Sign Up"); //create a button for viewing profile
        viewMyProfileButton.addActionListener (e ->
        {
            dispose (); //close the current frame
            DataManager.saveData ();
            new SignInSignUpMenu (); //open sign in / sign up menu
        });

        //add buttons to the bottom panel
        bottomPanel.add (createSubredditButton);
        bottomPanel.add (createPostButton);
        bottomPanel.add (viewNotificationsButton);
        bottomPanel.add (viewMyProfileButton);

        /*
        CREATING MAIN PANEL
        */

        mainPanel.setBackground (new Color (0xdae0e6)); //set background color to light gray
        mainPanel.setLayout (new BoxLayout (mainPanel, BoxLayout.Y_AXIS)); //set layout to box layout

        ArrayList <Post> allPosts = Post.getAllPosts (); //fetch posts from array list
        if (Main.reversed == null)
        {
            Collections.reverse (allPosts); //reverse order to display from latest to oldest
            Main.reversed = true;
        }

        for (Post post : allPosts)
        {
            JPanel postPanel = new JPanel (new BorderLayout ()); //create post panel

            JPanel contentPanel = createPostPanel (post);
            contentPanel.setBackground (new Color (0xffffff)); //set background color to white

            JPanel votePanel = createVotePanel (post); //create vote panel

            postPanel.add (votePanel, BorderLayout.WEST); //add vote panel to the left
            postPanel.add (contentPanel, BorderLayout.CENTER); //add content panel to the center

            mainPanel.add (postPanel); //add post panel to main panel
            mainPanel.add (Box.createVerticalStrut (20)); //add vertical spacing between post panels
        }

        JScrollPane scrollPane = new JScrollPane (mainPanel); //adding main panel to a scroll pane
        scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        SwingUtilities.invokeLater (() -> scrollPane.getVerticalScrollBar ().setValue (0)); //set scroll panel to start from top

        //add panels to the frame
        add (topPanel, BorderLayout.NORTH);
        add (scrollPane, BorderLayout.CENTER);
        add (bottomPanel, BorderLayout.SOUTH);

        setVisible (true); //make the frame visible
    }

    /*
    POST PANEL FUNCTIONS
    */

    private JPanel createPostPanel (Post post)
    {
        JPanel postPanel = new JPanel (new BorderLayout ());
        postPanel.setBackground (new Color (0xffffff)); //set background color to white

        JButton titleButton = createButton (post.getTitle ()); //create clickable button for title
        titleButton.setHorizontalAlignment (SwingConstants.CENTER); //center align the title button text
        titleButton.setFont (new Font ("Arial", Font.BOLD, 16)); //set title font

        //create clickable buttons for user and subreddit
        JButton userButton      = createButton ("u/" + post.getUser ().getUsername ());
        JButton subredditButton = createButton ("r/" + post.getSubreddit ().getTitle ());

        JTextArea contentArea = new JTextArea (post.getContent ()); //create text area for post content

        contentArea.setEditable (false); //post content can't be edited

        //post content wrap text automatically
        contentArea.setLineWrap (true);
        contentArea.setWrapStyleWord (true);

        contentArea.setBackground (new Color (0xffffff)); //set background color to white

        //add the components to post panel
        postPanel.add (titleButton, BorderLayout.NORTH);
        postPanel.add (userButton, BorderLayout.WEST);
        postPanel.add (subredditButton, BorderLayout.EAST);
        postPanel.add (contentArea, BorderLayout.CENTER);

        return postPanel;
    }

    private JButton createButton (String text)
    {
        JButton button = new JButton (text); //create a button for post components

        button.setOpaque (false); //make button transparent
        button.setBorderPainted (false); //remove button border
        button.setContentAreaFilled (false); //remove button background
        button.setFocusPainted (false); //remove button focus border

        button.setHorizontalAlignment (SwingConstants.LEFT); //align text to the left
        button.setCursor (new Cursor (Cursor.HAND_CURSOR)); //set cursor to hand

        return button;
    }

    /*
    VOTE PANEL FUNCTIONS
    */

    private JPanel createVotePanel (Post post)
    {
        JPanel votePanel = new JPanel (); //create vote panel

        votePanel.setLayout (new BoxLayout (votePanel, BoxLayout.Y_AXIS)); //stack buttons vertically

        JButton upvoteButton   = new JButton (" ↑ "); //create upvote button
        JButton downvoteButton = new JButton (" ↓ "); //create downvote button

        JPanel karmaPanel = new JPanel (); //create a panel for karma label
        JLabel karmaLabel = new JLabel (); //initialize karma label with initial value
        if (post.getKarma () < 1000)
        {
            karmaLabel.setText ("Karma: " + post.getKarma ()); //update karma label text
        }
        else if (post.getKarma () < 1000000)
        {
            double doubleKarma    = (double) post.getKarma () / 1000; //cast the int to double
            String formattedKarma = String.format ("%.1fk", doubleKarma); //show only one number after decimal point with k
            karmaLabel.setText ("Karma: " + formattedKarma); //update karma label text
        }
        else if (post.getKarma () < 1000000000)
        {
            double doubleKarma    = (double) post.getKarma () / 1000000; //cast the int to double
            String formattedKarma = String.format ("%.1fm", doubleKarma); //show only one number after decimal point with m
            karmaLabel.setText ("Karma: " + formattedKarma); //update karma label text
        }
        karmaPanel.add (karmaLabel); //add karma label to karma panel

        upvoteButton.setPreferredSize (new Dimension (30, 30)); //set button size
        upvoteButton.setFont (upvoteButton.getFont ().deriveFont (25.0f)); //set font size to make button text larger

        upvoteButton.setBorder (BorderFactory.createLineBorder (Color.BLACK)); //add border for visibility
        upvoteButton.setBackground (new Color (0xffffff)); //set background color to orange

        downvoteButton.setPreferredSize (new Dimension (30, 30)); //set button size
        downvoteButton.setFont (downvoteButton.getFont ().deriveFont (25.0f)); //set font size to make button text larger

        downvoteButton.setBorder (BorderFactory.createLineBorder (Color.BLACK)); //add border for visibility
        downvoteButton.setBackground (new Color (0xffffff)); //set background color to white

        upvoteButton.addActionListener (e -> //add action to button
                JOptionPane.showMessageDialog (null, "You need to be Logged in to Vote."));

        downvoteButton.addActionListener (e -> //add action to button
                JOptionPane.showMessageDialog (null, "You need to be Logged in to Vote."));

        //add the components to vote panel
        votePanel.add (upvoteButton);
        votePanel.add (Box.createVerticalStrut (5)); //add space between buttons
        votePanel.add (downvoteButton);
        votePanel.add (karmaPanel, BorderLayout.WEST);

        return votePanel;
    }

    /*
    SEARCH PANEL FUNCTIONS
    */

    private ArrayList <Post> filterPostsByTitle (String keyword)
    {
        return Post.getAllPosts ().stream () //create a stream with all posts as source
                //filter posts that contain the keyword in their title
                .filter (post -> post.getTitle ().toLowerCase ().contains (keyword.toLowerCase ()))
                .collect (Collectors.toCollection (ArrayList :: new)); //create an arraylist from the collector's results
    }

    private ArrayList <Post> filterPostsBySubreddit (String subredditName)
    {
        return Post.getAllPosts ().stream () //create a stream with all posts as source
                //filter posts that contain the keyword in their subreddit
                .filter (post -> post.getSubreddit ().getTitle ().toLowerCase ().contains (subredditName.toLowerCase ()))
                .collect (Collectors.toCollection (ArrayList :: new)); //create an arraylist from the collector's results
    }

    private ArrayList <Post> filterPostsByUsername (String username)
    {
        return Post.getAllPosts ().stream () //create a stream with all posts as source
                //filter posts that contain the keyword in their username
                .filter (post -> post.getUser ().getUsername ().toLowerCase ().contains (username.toLowerCase ()))
                .collect (Collectors.toCollection (ArrayList :: new)); //create an arraylist from the collector's results
    }

    private void displayFilteredPosts (ArrayList <Post> filteredPosts, JPanel mainPanel)
    {
        mainPanel.removeAll (); //clear every post from the main panel

        for (Post post : filteredPosts) //re-create the filtered posts
        {
            JPanel postPanel = new JPanel (new BorderLayout ()); //create post panel

            JPanel contentPanel = createPostPanel (post); //create content panel
            contentPanel.setBackground (new Color (0xffffff)); //set background color to white

            JPanel votePanel = createVotePanel (post); //create vote panel

            //add the panels to post panel
            postPanel.add (votePanel, BorderLayout.WEST);
            postPanel.add (contentPanel, BorderLayout.CENTER);

            mainPanel.add (postPanel); //add post panel to main panel
            mainPanel.add (Box.createVerticalStrut (20)); //add vertical spacing between post panels
        }

        mainPanel.revalidate (); //make the panel recalculate according to changes
        mainPanel.repaint (); //display the changes in the frame
    }
}