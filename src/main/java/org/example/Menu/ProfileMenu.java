package org.example.Menu;

import org.example.DataManager;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Arrays;

public class ProfileMenu extends JFrame implements Serializable
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
            DataManager.saveData ();
            new FrontPageMenu (); //open front page menu
        });

        JLabel karmaLabel = new JLabel ("Karma: " + User.getCurrentUser ().getKarma ()); //create karma label

        //add the components to the panel
        topPanel.add (returnButton);
        topPanel.add (karmaLabel);

        JPanel bottomPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create top panel

        JButton logoutButton = new JButton ("Log Out"); //create a button for returning
        logoutButton.addActionListener (e -> //add action to the button
        {
            int answer = JOptionPane.showConfirmDialog (null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION)
            {
                dispose (); //close the current frame
                DataManager.saveData ();
                User.logout (); //log out the user
                new FrontPageGuestMenu (); //open front page menu
            }
        });
        bottomPanel.add (logoutButton); //add return button to top panel

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
            DataManager.saveData ();
        });
        changeUsernameButton.setPreferredSize (new Dimension (150, 30)); //set button size

        JPanel changeEmailPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JButton changeEmailButton = new JButton ("Change Email");
        changeEmailButton.addActionListener (e -> //add action to the button
        {
            new ChangeEmail (User.getCurrentUser ());
            dispose ();
            DataManager.saveData ();
        });
        changeEmailButton.setPreferredSize (new Dimension (150, 30)); //set button size

        JPanel changePasswordPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        JButton changePasswordButton = new JButton ("Change Password");
        changePasswordButton.addActionListener (e -> //add action to the button
        {
            new ChangePassword (User.getCurrentUser ());
            dispose ();
            DataManager.saveData ();
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
        getContentPane ().add (bottomPanel, BorderLayout.SOUTH);

        setVisible (true); //make the frame visible
    }

    /*
    AUTHORITY FUNCTIONS
    */

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
                    dispose (); //close the current frame
                    DataManager.saveData ();
                    new ProfileMenu (); //open profile menu
                }
            });

            setSize (400, 80); //set size of the frame
            setLocationRelativeTo (null);
            setResizable (false);

            JPanel mainPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create main panel

            textField = new JTextField (20);
            textField.setPreferredSize (new Dimension (200, 30)); //set text field size
            mainPanel.add (textField); //add text field to main panel

            JButton changeUsername = new JButton ("Change Username");
            changeUsername.addActionListener (e ->
            {
                String username = textField.getText ();

                if (User.validateUsername (username) == 2)
                {
                    JOptionPane.showMessageDialog (null, "Username already associated with an Account. Please enter a new Username or Sign In.");
                }
                else if (User.validateUsername (username) == 0)
                {
                    JOptionPane.showMessageDialog (null, "Invalid Username. Usernames must have at least 6 characters.");
                }
                else
                {
                    dispose (); //close the current frame
                    int answer = JOptionPane.showConfirmDialog (null, "Do you confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        user.changeUsername (username);
                        JOptionPane.showMessageDialog (null, "Username changed successfully!");
                    }
                    DataManager.saveData ();
                    new ProfileMenu (); //open front page menu
                }
            });

            mainPanel.add (changeUsername); //add button to main panel
            add (mainPanel); //add main panel to the frame

            setVisible (true); //make the frame visible
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
                    DataManager.saveData ();
                    new ProfileMenu ();
                }
            });

            setSize (400, 80); //set size of the frame
            setLocationRelativeTo (null);
            setResizable (false);

            JPanel mainPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create main panel

            textField = new JTextField (20);
            textField.setPreferredSize (new Dimension (200, 30)); //set text field size
            mainPanel.add (textField); //add text field to main panel

            JButton changeEmail = new JButton ("Change Email");
            changeEmail.addActionListener (e ->
            {
                String email = textField.getText ();

                if (User.validateEmail (email) == 2)
                {
                    JOptionPane.showMessageDialog (null, "Email already associated with an Account. Please enter a new Email or Sign In.");
                }
                else if (User.validateEmail (email) == 0)
                {
                    JOptionPane.showMessageDialog (null, "Invalid Email. Please enter a new Email.");
                }
                else
                {
                    dispose (); //close the current frame
                    int answer = JOptionPane.showConfirmDialog (null, "Do you confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        user.changeEmail (email);
                        JOptionPane.showMessageDialog (null, "Email changed successfully!");
                    }
                    DataManager.saveData ();
                    new ProfileMenu (); //open front page menu
                }
            });

            mainPanel.add (changeEmail); //add button to main panel
            add (mainPanel); //add main panel to the frame

            setVisible (true); //make the frame visible
        }
    }

    private static class ChangePassword extends JFrame
    {
        private final JPasswordField textField1;
        private final JPasswordField textField2;

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
                    DataManager.saveData ();
                    new ProfileMenu ();
                }
            });

            setSize (600, 110); //set size of the frame
            setLocationRelativeTo (null);
            setResizable (false);

            JPanel mainPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create main panel

            textField1 = new JPasswordField (20);
            textField1.setPreferredSize (new Dimension (200, 30)); //set text field size
            mainPanel.add (textField1); //add text field to main panel

            textField2 = new JPasswordField (20);
            textField2.setPreferredSize (new Dimension (200, 30)); //set text field size
            mainPanel.add (textField2); //add text field to main panel

            JButton changePassword = new JButton ("Change Password");
            changePassword.addActionListener (e ->
            {
                String password = Arrays.toString (textField1.getPassword ());
                String confirmPassword = Arrays.toString (textField2.getPassword ());

                if (User.validatePassword (password, confirmPassword) == 2)
                {
                    JOptionPane.showMessageDialog (null, "Passwords do not match. Please try again.");
                }
                else if (User.validatePassword (password, confirmPassword) == 0)
                {
                    JOptionPane.showMessageDialog (null, "Invalid Password. Passwords must have at least one number, one uppercase character and one special character.");
                }
                else
                {
                    dispose (); //close the current frame
                    int answer = JOptionPane.showConfirmDialog (null, "Do you confirm?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        user.changePassword (password);
                        JOptionPane.showMessageDialog (null, "Password changed successfully!");

                    }
                    DataManager.saveData ();
                    new ProfileMenu (); //open front page menu
                }
            });

            mainPanel.add (changePassword); //add button to main panel
            add (mainPanel); //add main panel to the frame

            setVisible (true); //make the frame visible
        }
    }
}