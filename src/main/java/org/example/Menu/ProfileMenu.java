package org.example.Menu;

import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProfileMenu extends JFrame
{
    public ProfileMenu ()
    {
        setTitle ("User Profile");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (400, 300); //set window size
        setLocationRelativeTo (null); //center align the frame on the screen

        /*
        CREATING TOP PANEL
        */

        JPanel topPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create top panel

        JButton returnButton = new JButton ("Return to Front Page"); //create a button for returning
        returnButton.addActionListener (e -> //add action to the button
        {
            dispose (); //close the current frame
            new FrontPageMenu (); //open front page menu
        });
        topPanel.add (returnButton); //add return button to top panel

        JPanel middlePanel = new JPanel (new BorderLayout ()); //create middle panel to contain left and right panels

        /*
        CREATING LEFT PANEL
        */

        JPanel leftPanel = new JPanel (new GridLayout (5, 1, 0, 0)); //create left panel

        JLabel empty = new JLabel (""); //create empty label for spacing
        JLabel usernameLabel = new JLabel ("   Username: " + User.getCurrentUser ().getUsername ());
        JLabel emailLabel = new JLabel ("   Email: " + User.getCurrentUser ().getEmail ());
        JLabel passwordLabel = new JLabel ("   Password: **********");

        //add labels to left panel
        leftPanel.add (empty);
        leftPanel.add (usernameLabel);
        leftPanel.add (emailLabel);
        leftPanel.add (passwordLabel);

        /*
        CREATING RIGHT PANEL
        */

        JPanel rightPanel = new JPanel (new GridLayout (5, 1, 0, 0)); //create right panel

        JPanel emptyPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create panel to contain empty label
        JLabel emptyButton = new JLabel (""); //create empty label for spacing

        JPanel changeUsernamePanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JButton changeUsernameButton = new JButton ("Change Username");
        changeUsernameButton.addActionListener (e -> //add action to the button
        {
            new ChangeUsername (User.getCurrentUser ());
            dispose ();
        });
        changeUsernameButton.setPreferredSize (new Dimension (150, 30)); //set button size

        JPanel changeEmailPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JButton changeEmailButton = new JButton ("Change Email");
        changeEmailButton.addActionListener (e -> //add action to the button
        {
            new ChangeEmail (User.getCurrentUser ());
            dispose ();
        });
        changeEmailButton.setPreferredSize (new Dimension (150, 30)); //set button size

        JPanel changePasswordPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JButton changePasswordButton = new JButton ("Change Password");
        changePasswordButton.addActionListener (e -> //add action to the button
        {
            new ChangePassword (User.getCurrentUser ());
            dispose ();
        });
        changePasswordButton.setPreferredSize (new Dimension (150, 30)); //set button size

        //add buttons to the panels
        emptyPanel.add (emptyButton);
        changeUsernamePanel.add (changeUsernameButton);
        changeEmailPanel.add (changeEmailButton);
        changePasswordPanel.add (changePasswordButton);

        //add panels to right panel
        rightPanel.add (emptyPanel);
        rightPanel.add (changeUsernamePanel);
        rightPanel.add (changeEmailPanel);
        rightPanel.add (changePasswordPanel);

        //add left and right panels to middle panel
        middlePanel.add (leftPanel, BorderLayout.WEST);
        middlePanel.add (rightPanel, BorderLayout.EAST);

        //add top and middle panels to the frame
        getContentPane ().add (topPanel, BorderLayout.NORTH);
        getContentPane ().add (middlePanel, BorderLayout.CENTER);

        setVisible (true); //make the frame visible
    }

    private static class ChangeUsername extends JFrame
    {
        private final JTextField textField;

        public ChangeUsername (User user)
        {
            setTitle ("Change Username");
            setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener (new WindowAdapter ()
            {
                @Override
                public void windowClosing (WindowEvent event)
                {
                    dispose ();
                    new ProfileMenu ();
                }
            });

            setSize (400, 100); // Reduced the height of the frame
            setLocationRelativeTo (null);
            setResizable (false);

            JPanel mainPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout

            textField = new JTextField (20);
            textField.setPreferredSize (new Dimension (200, 30)); // Set preferred size
            mainPanel.add (textField); // Add text field to main panel

            JButton changeUsername = new JButton ("Change Username");
            changeUsername.addActionListener (e ->
            {
                String username = textField.getText ();
                if (! username.isEmpty ())
                {
                    dispose ();
                    new ProfileMenu ();
                }
            });

            mainPanel.add (changeUsername); // Add button to main panel
            add (mainPanel);

            setVisible (true);
        }
    }

    private static class ChangeEmail extends JFrame
    {
        private final JTextField textField;

        public ChangeEmail (User user)
        {
            setTitle ("Change Email");
            setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener (new WindowAdapter ()
            {
                @Override
                public void windowClosing (WindowEvent event)
                {
                    dispose ();
                    new ProfileMenu ();
                }
            });

            setSize (400, 100); // Reduced the height of the frame
            setLocationRelativeTo (null);
            setResizable (false);

            JPanel mainPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout

            textField = new JTextField (20);
            textField.setPreferredSize (new Dimension (200, 30)); // Set preferred size
            mainPanel.add (textField); // Add text field to main panel

            JButton changeEmail = new JButton ("Change Email");
            changeEmail.addActionListener (e ->
            {
                String email = textField.getText ();
                if (! email.isEmpty ())
                {
                    dispose ();
                    new ProfileMenu ();
                }
            });

            mainPanel.add (changeEmail); // Add button to main panel
            add (mainPanel);

            setVisible (true);
        }
    }

    private static class ChangePassword extends JFrame
    {
        private final JTextField textField;

        public ChangePassword (User user)
        {
            setTitle ("Change Password");
            setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener (new WindowAdapter ()
            {
                @Override
                public void windowClosing (WindowEvent event)
                {
                    dispose ();
                    new ProfileMenu ();
                }
            });

            setSize (400, 100); // Reduced the height of the frame
            setLocationRelativeTo (null);
            setResizable (false);

            JPanel mainPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); // Use FlowLayout

            textField = new JTextField (20);
            textField.setPreferredSize (new Dimension (200, 30)); // Set preferred size
            mainPanel.add (textField); // Add text field to main panel

            JButton changePassword = new JButton ("Change Password");
            changePassword.addActionListener (e ->
            {
                String password = textField.getText ();
                if (! password.isEmpty ())
                {
                    dispose ();
                    new ProfileMenu ();
                }
            });

            mainPanel.add (changePassword); // Add button to main panel
            add (mainPanel);

            setVisible (true);
        }
    }

    public static void main (String[] args)
    {
        SwingUtilities.invokeLater (() ->
        {
            User user = new User ("prof.danial4@gmail.com", "TheDanielTp", "Tdtp3148_P");
            User.addUser (user);
            User.setCurrentUser (user);
            new ProfileMenu ();
        });
    }
}