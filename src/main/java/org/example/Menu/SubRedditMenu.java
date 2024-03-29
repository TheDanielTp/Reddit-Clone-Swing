package org.example.Menu;

import org.example.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SubRedditMenu extends JFrame implements Serializable
{
    protected static boolean reversed = false;

    public SubRedditMenu (Subreddit subreddit)
    {
        setTitle (subreddit.getTitle ());
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (800, 600); //set window size
        setLocationRelativeTo (null); //center align the frame on the screen

        /*
        CREATING TOP PANEL
        */

        JPanel topPanel = new JPanel (new BorderLayout ()); //create top panel

        JButton returnButton = new JButton ("Return to Front Page"); //create a button for returning
        returnButton.addActionListener (e -> //add action to the button
        {
            dispose ();
            DataManager.saveData ();
            new FrontPageMenu ();
        });

        JTextField searchBar = new JTextField (20); //create a text field for search bar

        JButton searchButton = new JButton ("Search"); //create a button for searching
        searchButton.addActionListener (e -> //add action to the button
        {
            String search = searchBar.getText ();
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
        createSubredditButton.addActionListener (e -> //add action to the button
        {
            dispose (); //close the current frame
            DataManager.saveData ();
            new CreateSubredditMenu (); //open create subreddit menu
        });

        JButton createPostButton = new JButton ("Create Post"); //create a button for creating posts
        createPostButton.setBackground (new Color (0x0079d3)); //set button color to blue
        createPostButton.setForeground (new Color (0xffffff)); //set text color to white
        createPostButton.addActionListener (e -> //add action to the button
        {
            dispose (); //close the current frame
            DataManager.saveData ();
            new CreatePostMenu (); //open create post menu
        });

        JButton viewNotificationsButton = new JButton ("View Notifications"); //create a button for viewing notifications
        viewNotificationsButton.setBackground (new Color (0xff4500)); //set button color to orange
        viewNotificationsButton.setForeground (new Color (0xffffff)); //set text color to white
        viewNotificationsButton.addActionListener (e -> //add action to the button
        {

        });

        JButton viewMyProfileButton = new JButton ("View My Profile"); //create a button for viewing profile
        viewMyProfileButton.addActionListener (e -> //add action to the button
        {

        });

        //add buttons to the bottom panel
        bottomPanel.add (createSubredditButton);
        bottomPanel.add (createPostButton);
        bottomPanel.add (viewNotificationsButton);
        bottomPanel.add (viewMyProfileButton);

        /*
        CREATING MAIN PANEL
        */

        JPanel mainPanel = new JPanel ();
        mainPanel.setBackground (new Color (0xdae0e6)); //set background color to light gray
        mainPanel.setLayout (new BoxLayout (mainPanel, BoxLayout.Y_AXIS)); //set layout to box layout

        ArrayList <Post> allPosts = Post.getAllPosts (); //fetch posts from array list
        if (! reversed)
        {
            Collections.reverse (allPosts); //reverse order to display from latest to oldest
            reversed = true;
        }

        for (Post post : allPosts)
        {
            if (post.getSubreddit ().equals (subreddit))
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
    POST PANEL
    */

    private JPanel createPostPanel (Post post)
    {
        JPanel postPanel = new JPanel (new BorderLayout ());
        postPanel.setBackground (new Color (0xffffff)); //set background color to white

        JButton titleButton = createButton (post.getTitle ()); //create clickable button for title
        titleButton.addActionListener (e ->
        {
            dispose ();
            DataManager.saveData ();
            new PostMenu (post);
        });

        titleButton.setFont (new Font ("Arial", Font.BOLD, 16));
        titleButton.setHorizontalAlignment (SwingConstants.CENTER); //center align the title button text

        //create clickable buttons for user and subreddit
        JButton userButton = createButton ("u/" + post.getUser ().getUsername ());
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
    VOTE PANEL
    */

    private JPanel createVotePanel (Post post)
    {
        JPanel votePanel = new JPanel (); //create vote panel

        votePanel.setLayout (new BoxLayout (votePanel, BoxLayout.Y_AXIS)); //stack buttons vertically

        JButton upvoteButton = new JButton (" ↑ "); //create upvote button
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

        //initialize buttons' colors
        final String[] upVoteButtonColor = {"White"};
        final String[] downVoteButtonColor = {"White"};

        upvoteButton.setPreferredSize (new Dimension (30, 30)); //set button size
        upvoteButton.setFont (upvoteButton.getFont ().deriveFont (25.0f)); //set font size to make button text larger

        upvoteButton.setBorder (BorderFactory.createLineBorder (Color.BLACK)); //add border for visibility

        downvoteButton.setPreferredSize (new Dimension (30, 30)); //set button size
        downvoteButton.setFont (downvoteButton.getFont ().deriveFont (25.0f)); //set font size to make button text larger

        downvoteButton.setBorder (BorderFactory.createLineBorder (Color.BLACK)); //add border for visibility

        if (post.getDownVotedUsers ().contains (User.getCurrentUser ())) //if user already used downvote
        {
            downvoteButton.setBackground (new Color (0x7193ff)); //set background color to blue
            downVoteButtonColor[0] = "Blue";
            downvoteButton.setForeground (new Color (0xffffff)); //set text color to white
        }
        else if (post.getUpVotedUsers ().contains (User.getCurrentUser ())) //if user already used upvote
        {
            upvoteButton.setBackground (new Color (0xff4500)); //set background color to orange
            upVoteButtonColor[0] = "Orange";
            upvoteButton.setForeground (new Color (0xffffff)); //set text color to white
        }
        else
        {
            downvoteButton.setBackground (new Color (0xffffff)); //set background color to white
            downVoteButtonColor[0] = "White";
            downvoteButton.setForeground (new Color (0x000000)); //set text color to black

            upvoteButton.setBackground (new Color (0xffffff)); //set background color to white
            upVoteButtonColor[0] = "White";
            upvoteButton.setForeground (new Color (0x000000)); //set text color to black
        }

        upvoteButton.addActionListener (e -> //add action to button
        {
            post.upVote (User.getCurrentUser ()); //call the upvote function

            //change button color according to the current color
            if (downVoteButtonColor[0].equals ("Blue"))
            {
                downvoteButton.setBackground (new Color (0xffffff)); //set background color to white
                downVoteButtonColor[0] = "White";
                downvoteButton.setForeground (new Color (0x000000)); //set text color to black
            }
            if (upVoteButtonColor[0].equals ("White"))
            {
                upvoteButton.setBackground (new Color (0xff4500)); //set background color to orange
                upVoteButtonColor[0] = "Orange";
                upvoteButton.setForeground (new Color (0xffffff)); //set text color to white
            }
            else if (upVoteButtonColor[0].equals ("Orange"))
            {
                upvoteButton.setBackground (new Color (0xffffff)); //set background color to white
                upVoteButtonColor[0] = "White";
                upvoteButton.setForeground (new Color (0x000000)); //set text color to black
            }

            updateKarma (post.getKarma (), karmaLabel);
        });

        downvoteButton.addActionListener (e -> //add action to button
        {
            post.downVote (User.getCurrentUser ()); //call the downvote function

            //change button color according to the current color
            if (upVoteButtonColor[0].equals ("Orange"))
            {
                upvoteButton.setBackground (new Color (0xffffff)); //set background color to white
                upVoteButtonColor[0] = "White";
                upvoteButton.setForeground (new Color (0x000000)); //set text color to black
            }
            if (downVoteButtonColor[0].equals ("White"))
            {
                downvoteButton.setBackground (new Color (0x7193ff)); //set background color to blue
                downVoteButtonColor[0] = "Blue";
                downvoteButton.setForeground (new Color (0xffffff)); //set text color to white
            }
            else if (downVoteButtonColor[0].equals ("Blue"))
            {
                downvoteButton.setBackground (new Color (0xffffff)); //set background color to white
                downVoteButtonColor[0] = "White";
                downvoteButton.setForeground (new Color (0x000000)); //set text color to black
            }

            updateKarma (post.getKarma (), karmaLabel);
        });

        //add the components to vote panel
        votePanel.add (upvoteButton);
        votePanel.add (Box.createVerticalStrut (5)); //add space between buttons
        votePanel.add (downvoteButton);
        votePanel.add (karmaPanel);

        return votePanel;
    }

    public void updateKarma (int karmaCount, JLabel karmaLabel)
    {
        karmaLabel.setText ("Karma: " + karmaCount); //update karma label text
    }
}