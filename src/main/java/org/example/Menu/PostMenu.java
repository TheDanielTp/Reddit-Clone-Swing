package org.example.Menu;

import org.example.Comment;
import org.example.Post;
import org.example.Subreddit;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;


public class PostMenu extends JFrame
{
    public PostMenu (Post post)
    {
        setTitle ("Reddit - " + post.getTitle ());
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (800, 600); //set window size
        setLocationRelativeTo (null); //center align the frame on the screen

        /*
        CREATING TOP PANEL
        */

        JPanel topPanel = new JPanel (new BorderLayout ()); //create top panel

        JButton returnButton = new JButton ("Return to Front Page"); //create a button for returning
        returnButton.addActionListener (e -> //add action to button
        {
            dispose (); //close the current frame
            new FrontPageMenu (); //open front page menu
        });

        JTextField searchBar = new JTextField (20); //create a text field for search bar

        JButton searchButton = new JButton ("Search"); //create a button for searching
        searchButton.addActionListener (e -> //add action to button
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
        createSubredditButton.addActionListener (e -> //add action to button
        {
            dispose ();
            new CreateSubredditMenu ();
        });

        JButton createPostButton = new JButton ("Create Post"); //create a button for creating posts
        createPostButton.setBackground (new Color (0x0079d3)); //set button color to blue
        createPostButton.setForeground (new Color (0xffffff)); //set text color to white
        createPostButton.addActionListener (e -> //add action to button
        {
            dispose (); //close the current frame
            new CreatePostMenu (); //open create post menu
        });

        JButton viewNotificationsButton = new JButton ("View Notifications"); //create a button for viewing notifications
        viewNotificationsButton.setBackground (new Color (0xff4500)); //set button color to orange
        viewNotificationsButton.setForeground (new Color (0xffffff)); //set text color to white
        viewNotificationsButton.addActionListener (e -> //add action to button
        {

        });

        JButton viewMyProfileButton = new JButton ("View My Profile"); //create a button for viewing profile
        viewMyProfileButton.addActionListener (e -> //add action to button
        {

        });

        //add buttons to the bottom panel
        bottomPanel.add (createSubredditButton);
        bottomPanel.add (createPostButton);
        bottomPanel.add (viewNotificationsButton);
        bottomPanel.add (viewMyProfileButton);

        JPanel mainPanel = new JPanel ();
        mainPanel.setLayout (new BoxLayout (mainPanel, BoxLayout.Y_AXIS));

        JPanel postPanel = new JPanel (new BorderLayout ()); //create post panel

        JPanel contentPanel = createPostPanel (post);
        contentPanel.setBackground (new Color (0xffffff)); //set background color to white

        JPanel votePanel = createVotePanel (post); //create vote panel

        postPanel.add (votePanel, BorderLayout.WEST); //add vote panel to the left
        postPanel.add (contentPanel, BorderLayout.CENTER); //add content panel to the center

        mainPanel.add (postPanel); //add post panel to main panel
        mainPanel.add (Box.createVerticalStrut (20)); //add vertical spacing between post panels

        ArrayList <Comment> comments = post.getComments (); //display comments
        for (Comment comment : comments)
        {
            JPanel commentPanel = createCommentPanel (comment); //create a panel for each comment
            mainPanel.add (commentPanel); //add comment panel to main panel
        }

        JButton addCommentButton = new JButton ("Add Comment"); //create a button for adding comments
        addCommentButton.addActionListener (e ->
        {
            dispose (); //close the current frame
            new AddCommentMenu (post); //open add comment menu
        });

        mainPanel.add (addCommentButton, BorderLayout.CENTER); //add add comment button to main panel

        JScrollPane scrollPane = new JScrollPane (mainPanel); //create a scroll pane
        scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //the scroll pane always appears

        //add contents to the frame
        add (topPanel, BorderLayout.NORTH);
        add (scrollPane, BorderLayout.CENTER);
        add (bottomPanel, BorderLayout.SOUTH);
        add (scrollPane);

        setVisible (true); //make the frame visible
    }

    private JPanel createPostPanel (Post post)
    {
        JPanel postPanel = new JPanel (new BorderLayout ());
        postPanel.setBackground (new Color (0xffffff)); //set background color to white

        JButton titleButton = createButton (post.getTitle ()); //create clickable button for title
        titleButton.setFont (new Font ("Arial", Font.BOLD, 16));
        titleButton.setHorizontalAlignment (SwingConstants.CENTER); //center the title button text

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

    private JPanel createCommentPanel (Comment comment)
    {
        JPanel commentPanel = new JPanel (new BorderLayout ()); //create a panel for comment

        commentPanel.setBorder (BorderFactory.createLineBorder (Color.GRAY)); //set border color to gray
        commentPanel.setBackground (new Color (0xf0f0f0)); //set background color to light gray

        JPanel commentVotePanel = createVotePanel (comment); //create upvote and downvote panel for comments

        JLabel userLabel = new JLabel ("User: " + comment.getUser ().getUsername ()); //create a label for user
        userLabel.setFont (new Font ("Arial", Font.BOLD, 12)); //set up label's font
        userLabel.setHorizontalAlignment (SwingConstants.CENTER); //center align the user label

        JTextArea contentArea = new JTextArea (comment.getContents ()); //create a text area for comments content

        contentArea.setEditable (false); //comment content can't be edited

        //comment content wrap text automatically
        contentArea.setLineWrap (true);
        contentArea.setWrapStyleWord (true);

        contentArea.setBorder (new EmptyBorder (10, 10, 10, 10)); //add space around content

        //add components to comment panel
        commentPanel.add (commentVotePanel, BorderLayout.WEST);
        commentPanel.add (userLabel, BorderLayout.NORTH);
        commentPanel.add (contentArea, BorderLayout.CENTER);

        return commentPanel;
    }

    private JPanel createVotePanel (Post post)
    {
        JPanel votePanel = new JPanel (); //create vote panel

        votePanel.setLayout (new BoxLayout (votePanel, BoxLayout.Y_AXIS)); //stack buttons vertically

        JButton upvoteButton = new JButton (" ↑ "); //create upvote button
        JButton downvoteButton = new JButton (" ↓ "); //create downvote button

        JPanel karmaPanel = new JPanel (); //create a panel for karma label
        JLabel karmaLabel = new JLabel ("Karma: " + post.getKarma ()); //initialize karma label with initial value
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

        if (post.getDownVotedUsers ().contains (User.getCurrentUser ()))
        {
            downvoteButton.setBackground (new Color (0x7193ff)); //set background color to blue
            downVoteButtonColor[0] = "Blue";
            downvoteButton.setForeground (new Color (0xffffff)); //set text color to white
        }
        else if (post.getUpVotedUsers ().contains (User.getCurrentUser ()))
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

    private JPanel createVotePanel (Comment comment)
    {
        JPanel votePanel = new JPanel (); //create vote panel

        votePanel.setLayout (new BoxLayout (votePanel, BoxLayout.Y_AXIS)); //stack buttons vertically

        JButton upvoteButton = new JButton (" ↑ "); //create upvote button
        JButton downvoteButton = new JButton (" ↓ "); //create downvote button

        JPanel karmaPanel = new JPanel (); //create a panel for karma label
        JLabel karmaLabel = new JLabel ("Karma: " + comment.getKarma ()); //initialize karma label with initial value
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

        if (comment.getDownVotedUsers ().contains (User.getCurrentUser ()))
        {
            downvoteButton.setBackground (new Color (0x7193ff)); //set background color to blue
            downVoteButtonColor[0] = "Blue";
            downvoteButton.setForeground (new Color (0xffffff)); //set text color to white
        }
        else if (comment.getUpVotedUsers ().contains (User.getCurrentUser ()))
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
            comment.upVote (User.getCurrentUser ()); //call the upvote function

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

            updateKarma (comment.getKarma (), karmaLabel);

        });

        downvoteButton.addActionListener (e -> //add action to button
        {
            comment.downVote (User.getCurrentUser ()); //call the downvote function

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

            updateKarma (comment.getKarma (), karmaLabel);
        });

        //add buttons to vote panel
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

    private static class AddCommentMenu extends JFrame
    {
        private final JTextArea commentTextArea; //initialize a text area for comment contents

        public AddCommentMenu (Post post)
        {
            setTitle ("Add Comment");
            setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE); //prevents the program from getting closed on exit
            addWindowListener (new WindowAdapter ()
            {
                @Override
                public void windowClosing (WindowEvent event)
                {
                    dispose (); //close the current frame
                    new PostMenu (post); //open post menu
                }
            });

            setSize (400, 200); //set window size
            setLocationRelativeTo (null); //center align the frame on the screen

            JPanel mainPanel = new JPanel (new BorderLayout ()); //create main panel

            commentTextArea = new JTextArea (); //create a text area for comment contents
            mainPanel.add (new JScrollPane (commentTextArea), BorderLayout.CENTER); //add scroll pane to main panel

            JButton postCommentButton = new JButton ("Post Comment");
            postCommentButton.addActionListener (e -> //add action to button
            {
                String commentContent = commentTextArea.getText ();
                if (! commentContent.isEmpty ())
                {
                    Comment newComment = new Comment (User.getCurrentUser (), post, commentContent); //create a new comment
                    post.addComment (newComment); //add comment to comments list

                    dispose (); //close the current frame
                    new PostMenu (post); //open the post menu
                }
            });

            mainPanel.add (postCommentButton, BorderLayout.SOUTH); //add button to main panel
            add (mainPanel); //add main panel to the frame

            setVisible (true); //make the frame visible
        }
    }

    /*
    MAIN FUNCTION
    */

    public static void main (String[] args)
    {
        User user = new User ("prof.danial4@gmail.com", "TheDanielTp", "Tdtp3148_P");
        User.addUser (user);
        User.setCurrentUser (user);

        Subreddit subreddit1 = new Subreddit ("Questions", "", user, false);
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

        new PostMenu (post1);
    }
}
