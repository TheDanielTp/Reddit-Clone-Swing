package org.example.Menu;

import org.example.Subreddit;
import org.example.Post;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreatePostMenu extends JFrame
{
    protected JComboBox <String> subredditComboBox;

    protected JTextField titleField;
    protected JTextArea contentArea;

    public CreatePostMenu ()
    {
        setTitle ("Create Post");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (600, 400); //set window size
        setLocationRelativeTo (null); //center align the frame on the screen

        JPanel mainPanel = new JPanel (new BorderLayout ()); //create main panel

        JPanel optionsPanel = new JPanel (new GridBagLayout ()); //create options panel

        GridBagConstraints gridBagConstraints = new GridBagConstraints (); //create a grid bag for creating post
        gridBagConstraints.anchor = GridBagConstraints.CENTER; //center align the grid bag on window
        gridBagConstraints.insets = new Insets (5, 5, 5, 5); //set grid bag size

        JLabel subredditLabel = new JLabel ("Select Subreddit: "); //create a label for subreddit

        gridBagConstraints.gridx = 0; //sets the horizontal grid position for the component being added to the container
        gridBagConstraints.gridy = 0; //sets the vertical grid position for the component being added to the container

        //add the components to the panel
        optionsPanel.add (subredditLabel, gridBagConstraints);

        subredditComboBox = new JComboBox <> (); //create a combo box for subreddits
        createSubredditComboBox ();
        gridBagConstraints.gridx = 1; //place the component in the second column

        //add the components to the panel
        optionsPanel.add (subredditComboBox, gridBagConstraints);
        optionsPanel.add (subredditLabel);
        optionsPanel.add (subredditComboBox);

        JPanel textFieldsPanel = new JPanel (); //create text fields panel
        textFieldsPanel.setLayout (new BoxLayout (textFieldsPanel, BoxLayout.Y_AXIS)); //set the layout to box layout

        JLabel titleLabel = new JLabel ("Title:");
        titleLabel.setAlignmentX (Component.CENTER_ALIGNMENT); //center align the title label

        titleField = new JTextField (10); //set the number of columns to 10
        titleField.setMaximumSize (new Dimension (400, 25)); //set the maximum size to limit the width and height

        JLabel contentLabel = new JLabel ("Content:");
        contentLabel.setAlignmentX (Component.CENTER_ALIGNMENT); //center align the content label

        contentArea = new JTextArea ();
        contentArea.setAlignmentX (Component.CENTER_ALIGNMENT); //center align the content text area

        JScrollPane scrollPane = new JScrollPane (contentArea); //create a scroll pane for content text area

        //add the components to the panel
        textFieldsPanel.add (titleLabel);
        textFieldsPanel.add (titleField);
        textFieldsPanel.add (contentLabel);
        textFieldsPanel.add (scrollPane);

        JPanel buttonsPanel = getButtonsPanel (); //create buttons panel

        //add panels to the main panel
        mainPanel.add (optionsPanel, BorderLayout.NORTH);
        mainPanel.add (textFieldsPanel, BorderLayout.CENTER);
        mainPanel.add (buttonsPanel, BorderLayout.SOUTH);

        add (mainPanel);
        setVisible (true); //make the frame visible
    }

    private JPanel getButtonsPanel ()
    {
        JPanel buttonsPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create buttons panel

        JButton returnButton = new JButton ("Return to Front Page");
        returnButton.setBackground (new Color (0x0079d3)); //set button color to blue
        returnButton.setForeground (new Color (0xffffff)); //set text color to white
        returnButton.addActionListener (e -> //add action to the button
        {
            dispose (); //close the current frame
            new FrontPageMenu (); //open front page menu
        });

        JButton postButton = new JButton ("Post");
        postButton.setBackground (new Color (0xff4500)); //set button color to orange
        postButton.setForeground (new Color (0xffffff)); //set text color to white
        postButton.addActionListener (e -> //add action to the button
        {
            String selectedSubreddit = (String) subredditComboBox.getSelectedItem (); //get subreddit's title from combo box
            Subreddit subreddit = getSubredditByName (selectedSubreddit); //find subreddit by subreddit's title

            String title = titleField.getText (); //get post's title from title field
            String content = contentArea.getText (); //get post's content from content area

            if (subreddit == null) //if no subreddit is selected
            {
                JOptionPane.showMessageDialog (null, "Please select a Subreddit.");
            }
            else if (title.isEmpty ()) //if no title is entered
            {
                JOptionPane.showMessageDialog (null, "Please enter a Title.");
            }
            else if (content.isEmpty ()) //if no content is entered
            {
                JOptionPane.showMessageDialog (null, "Please write your Post.");
            }
            else
            {
                int answer = JOptionPane.showConfirmDialog (null, "Do you confirm?", "Create Post", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION)
                {
                    User user = User.getCurrentUser (); //get the current user

                    Post post = new Post (title, content, subreddit, user); //create new post
                    Post.reverseAllPosts ();
                    Post.addPost (post); //add post to all posts list
                    Post.reverseAllPosts ();

                    dispose (); //close the current frame
                    JOptionPane.showMessageDialog (null, "Post created successfully!");
                    new FrontPageMenu (); //open front page menu
                }

            }
        });

        //add buttons to the panel
        buttonsPanel.add (returnButton);
        buttonsPanel.add (postButton);

        return buttonsPanel;
    }

    private void createSubredditComboBox ()
    {
        subredditComboBox.removeAllItems (); //clear existing items

        ArrayList <Subreddit> allCommunities = Subreddit.getAllSubreddits (); //get list of subreddits
        for (Subreddit subreddit : allCommunities)
        {
            subredditComboBox.addItem (subreddit.getTitle ()); //add subreddit's title to combo box
        }
    }

    private Subreddit getSubredditByName (String name)
    {
        ArrayList <Subreddit> allCommunities = Subreddit.getAllSubreddits (); //get list of subreddits

        for (Subreddit subreddit : allCommunities)
        {
            if (subreddit.getTitle ().equals (name)) //return subreddit if entered title is equal to subreddit's title
            {
                return subreddit;
            }
        }
        return null;
    }

    //main function for testing
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater (CreatePostMenu :: new);
    }
}
