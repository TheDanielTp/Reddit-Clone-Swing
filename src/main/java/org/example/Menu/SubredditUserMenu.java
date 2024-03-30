package org.example.Menu;

import org.example.DataManager;
import org.example.Subreddit;
import org.example.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SubredditUserMenu extends JFrame
{
    private JPanel    userListPanel;
    private JPanel    buttonPanel;
    private Subreddit subreddit;

    public SubredditUserMenu (Subreddit subreddit)
    {
        this.subreddit = subreddit;
        setTitle ("Subreddit Users");
        setSize (400, 300); //set window size

        setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE); //do nothing when window is closed
        addWindowListener (new WindowAdapter () //do the following on window action
        {
            @Override
            public void windowClosing (WindowEvent event)
            {
                dispose (); //close the current frame
                DataManager.saveData ();
                new SubredditAdminMenu (subreddit); //open profile menu
            }
        });

        ArrayList <User> users = subreddit.getMembers (); //get subreddit's users list
        users.removeIf (user -> subreddit.getCreator ().equals (user)); //remove creator from user's list

        userListPanel = new JPanel (new BoxLayout (userListPanel, BoxLayout.Y_AXIS)); //set users panel layout to vertical box layout
        userListPanel.setBorder (new EmptyBorder (10, 10, 10, 10)); //add empty border for spacing

        JScrollPane scrollPane = new JScrollPane (userListPanel); //create a scroll pane for users panel
        scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //scroll pane is vertical and always visible

        getContentPane ().add (scrollPane, BorderLayout.CENTER);

        updateUserList (users);
        updateButtonPanel (users);

        setVisible (true); //make the frame visible
    }

    private void updateUserList (ArrayList <User> users)
    {
        userListPanel.removeAll (); //clear users panel

        for (User user : users)
        {
            JLabel userLabel = new JLabel (user.getUsername ());
            userListPanel.add (userLabel); //add user label to panel
            userListPanel.add (Box.createRigidArea (new Dimension (0, 5))); //add space between user labels
        }

        revalidate (); //make the panel recalculate according to changes
        repaint (); //display the changes in the frame
    }

    private void updateButtonPanel (ArrayList <User> users)
    {
        if (buttonPanel != null)
        {
            getContentPane ().remove (buttonPanel); //clear buttons panel
        }

        buttonPanel = new JPanel (new BoxLayout (buttonPanel, BoxLayout.Y_AXIS)); //set buttons panel layout to vertical box layout

        for (User user : users) //create a button for each user
        {
            JButton kickButton = new JButton ("Kick " + user.getUsername ());
            kickButton.setPreferredSize (new Dimension (100, 20)); //set button size
            kickButton.addActionListener (e -> //add action to the button
            {
                int answer = JOptionPane.showConfirmDialog (null, "Are you sure?");
                if (answer == JOptionPane.YES_OPTION)
                {
                    dispose (); //close the current frame

                    subreddit.removeUser (user); //remove user from subreddit's users list
                    user.leaveSubreddit (subreddit); //remove subreddit from user's subreddits list
                    JOptionPane.showMessageDialog (null, "User successfully kicked.");

                    DataManager.saveData ();
                    new SubredditUserMenu (subreddit); //open subreddit user menu
                }
            });
            buttonPanel.add (kickButton); //add button to the panel
        }

        getContentPane ().add (buttonPanel, BorderLayout.EAST);
        revalidate (); //make the panel recalculate according to changes
        repaint (); //display the changes in the frame
    }
}
