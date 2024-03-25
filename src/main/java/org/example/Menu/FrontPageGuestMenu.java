package org.example.Menu;

import org.example.Subreddit;
import org.example.Post;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class FrontPageGuestMenu extends JFrame
{
    public FrontPageGuestMenu ()
    {
        setTitle ("Reddit");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (800, 600); //set window size
        setLocationRelativeTo (null); //center the frame on the screen

        /*
        CREATING TOP PANEL
        */

        JPanel topPanel = new JPanel (new BorderLayout ()); //create top panel

        JButton returnButton = new JButton ("Return to Front Page"); //create a button for returning
        returnButton.addActionListener (e ->
        {

        });

        JTextField searchBar = new JTextField (20); //create a text field for search bar

        JButton searchButton = new JButton ("Search"); //create a button for searching
        searchButton.addActionListener (e ->
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
        createSubredditButton.addActionListener (e ->
        {
            JOptionPane.showMessageDialog (null, "You need to be Logged in to Create a Subreddit");
        });

        JButton createPostButton = new JButton ("Create Post"); //create a button for creating posts
        createPostButton.setBackground (new Color (0x0079d3)); //set button color to blue
        createPostButton.setForeground (new Color (0xffffff)); //set text color to white
        createPostButton.addActionListener (e ->
        {
            JOptionPane.showMessageDialog (null, "You need to be Logged in to Create a Post");
        });

        JButton viewNotificationsButton = new JButton ("View Notifications"); //create a button for viewing notifications
        viewNotificationsButton.setBackground (new Color (0xff4500)); //set button color to orange
        viewNotificationsButton.setForeground (new Color (0xffffff)); //set text color to white
        viewNotificationsButton.addActionListener (e ->
        {
            JOptionPane.showMessageDialog (null, "You need to be Logged in to View Notifications");
        });

        JButton viewMyProfileButton = new JButton ("Sign In / Sign Up"); //create a button for viewing profile
        viewMyProfileButton.addActionListener (e ->
        {
            dispose (); //close the current frame
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

        JPanel mainPanel = new JPanel ();
        mainPanel.setBackground (new Color (0xdae0e6)); //set background color to light gray
        mainPanel.setLayout (new BoxLayout (mainPanel, BoxLayout.Y_AXIS)); //set layout to box layout

        ArrayList <Post> allPosts = Post.getAllPosts (); //fetch posts from array list
        Collections.reverse (allPosts); //reverse order to display from latest to oldest

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

    private JPanel createPostPanel (Post post)
    {
        JPanel postPanel = new JPanel (new BorderLayout ());
        postPanel.setBackground (new Color (0xffffff)); //set background color to white

        JButton titleButton = createButton (post.getTitle ()); //create clickable button for title
        titleButton.setHorizontalAlignment (SwingConstants.CENTER); //center the title button text

        //create clickable buttons for user and subreddit
        JButton userButton = createButton ("u/" + post.getUser ().getUsername ());
        JButton subredditButton = createButton ("r/" + post.getSubreddit ().getName ());

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

        button.addActionListener (e ->
        {
            System.out.println ("Button clicked: " + text);
        });

        return button;
    }

    private JPanel createVotePanel (Post post)
    {
        JPanel votePanel = new JPanel (); //create vote panel

        votePanel.setLayout (new BoxLayout (votePanel, BoxLayout.Y_AXIS)); // Stack buttons vertically

        JButton upvoteButton = new JButton ("    "); //create upvote button

        upvoteButton.setPreferredSize (new Dimension (30, 30)); //set button size
        upvoteButton.setFont (upvoteButton.getFont ().deriveFont (25.0f)); //set font size to make button text larger

        upvoteButton.setBorder (BorderFactory.createLineBorder (Color.BLACK)); //add border for visibility
        upvoteButton.setBackground (new Color (0xff4500)); //set background color to orange

        JButton downvoteButton = new JButton ("    "); //create downvote button

        downvoteButton.setPreferredSize (new Dimension (30, 30)); //set button size
        downvoteButton.setFont (downvoteButton.getFont ().deriveFont (25.0f)); //set font size to make button text larger

        downvoteButton.setBorder (BorderFactory.createLineBorder (Color.BLACK)); //add border for visibility
        downvoteButton.setBackground (new Color (0x7193ff)); //set background color to light blue

        //add buttons to vote panel
        votePanel.add (upvoteButton);
        votePanel.add (Box.createVerticalStrut (5)); //add space between buttons
        votePanel.add (downvoteButton);

        return votePanel;
    }

    //main function for testing
    public static void main (String[] args)
    {
        User user = new User ("prof.danial4@gmail.com", "TheDanielTp", "Tdtp3148_P");
        User.addUser (user);

        SwingUtilities.invokeLater (FrontPageGuestMenu :: new);

        Subreddit subreddit1 = new Subreddit ("Questions", "");
        Subreddit.addSubreddit (subreddit1);
        User user1 = new User ("", "MathematicianNo", "");
        Post post1 = new Post ("Are a lot of parents not allowing sleepovers anymore?",
                """
                        I’m 38 and have no kids but have taught middle school for 16 years. My friend who has a 10 year old just asked me my opinion on sleepovers and said many parents don’t allow them anymore and it’s a big debate among parents because of dangers of potential abuse, social media, neighbors, guns.
                                                
                        Most of those things would never even come to my mind if I had a hypothetical kid, and I wouldn't let my kid go somewhere where I don’t know the family well… but the whole thing kind of blew me away.
                                                
                        Is this actually a common concern among parents?
                                                
                        For a bit of context, we’re of course in the USA with all of the crazy gun violence, and my friend is a lot more conservative and conspiracy theorist than liberal ol’ me. My biggest and probably only concern from that list would be the guns.
                        """,
                subreddit1, user1);
        Post.addPost (post1);

        Subreddit subreddit2 = new Subreddit ("Confess", "");
        Subreddit.addSubreddit (subreddit2);
        User user2 = new User ("", "Anonymous-Dog1", "");
        Post post2 = new Post ("I'm in love with my friends ex",
                """
                        My friend m15 broke up with his gf of 2 years f15 and during their breakup she would always text me m16 about their problems and I was always there to comfort her and over time we've grown closer but to me, I've caught feelings but I'm pretty sure she just sees me as a good friend. 
                        
                        Many problems with trying to talk talk to her like the fact that they are freshly broken up and that I couldn't do that to my friend but she is honestly the most beautiful and funny girls I've ever met and out energies match so well. 
                        
                        I've been stressing over it for a while now and I think it's time I seek advice. any one got some?
                        """,
                subreddit2, user2);
        Post.addPost (post2);

        Subreddit subreddit3 = new Subreddit ("ShortStory", "");
        Subreddit.addSubreddit (subreddit3);
        User user3 = new User ("", "ARedemptionSong", "");
        Post post3 = new Post ("What do you consider the greatest short story of all time?",
                """
                        For me, it is The Most Dangerous Game (1924) in which a castaway is hunted down on an island by a mad Russian aristocrat and his henchman.
                                                
                        It is probably the most significant short story. Not only did it inspire paintball of all things, but it was a pre-war visitation of the sort of stories you would get in the 50’s (James Bond etc) of exotic locations, fearsome underlings and a battle of wits.
                        """,
                subreddit3, user3);
        Post.addPost (post3);

        Post.addPost (post1);
        Post.addPost (post2);
        Post.addPost (post3);
    }
}