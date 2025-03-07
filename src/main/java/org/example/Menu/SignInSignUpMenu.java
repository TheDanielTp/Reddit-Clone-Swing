package org.example.Menu;

import org.example.DataManager;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class SignInSignUpMenu extends JFrame implements Serializable
{
    protected JTextField     signUpEmailField;
    protected JTextField     signUpUsernameField;
    protected JPasswordField signUpPasswordField;
    protected JPasswordField signUpConfirmPasswordField;

    protected JTextField     signInUsernameField;
    protected JPasswordField signInPasswordField;

    public SignInSignUpMenu ()
    {
        setTitle ("Sign In / Sign Up");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (700, 400); //set window size
        setLocationRelativeTo (null); //center align the frame on the screen

        JPanel mainPanel = new JPanel (new BorderLayout ()); //create main panel

        JPanel signUpSignInPanel = new JPanel (new GridLayout (1, 2, 20, 20)); //create sign up / sign in panel and add padding

        /*
        CREATING SIGN-UP PANEL
        */

        GridBagConstraints gridBagConstraints = new GridBagConstraints (); //create a grid bag for sign-up
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER; //center align the grid bag on the screen

        JPanel signUpPanel = new JPanel (new GridBagLayout ()); //create sign up panel
        signUpPanel.setBorder (BorderFactory.createEmptyBorder (20, 20, 20, 20));//add padding

        signUpEmailField = new JTextField (20); //set text field size
        signUpPanel.add (new JLabel ("Email:"), gridBagConstraints); //add email label to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpPanel.add (signUpEmailField, gridBagConstraints); //add email text field to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpUsernameField = new JTextField (20); //set text field size
        signUpPanel.add (new JLabel ("Username:"), gridBagConstraints); //add username label to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpPanel.add (signUpUsernameField, gridBagConstraints); //add username text field to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpPasswordField = new JPasswordField (20); //set text field size
        signUpPanel.add (new JLabel ("Password:"), gridBagConstraints); //add password label to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpPanel.add (signUpPasswordField, gridBagConstraints); //add password text field to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpConfirmPasswordField = new JPasswordField (20); //set text field size
        signUpPanel.add (new JLabel ("Confirm Password:"), gridBagConstraints); //add confirm password label to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpPanel.add (signUpConfirmPasswordField, gridBagConstraints); //add confirm password text field to panel
        gridBagConstraints.gridy++; //increment the row index

        signUpPanel.add (new JPanel (), gridBagConstraints); //empty row to add space
        gridBagConstraints.gridy++; //increment the row index

        JButton signUpButton = new JButton ("Sign Up");
        signUpButton.addActionListener (e -> //add action to button
        {
            signUp (); //call the sign-up function
        });

        signUpPanel.add (signUpButton, gridBagConstraints); //add button to sign up panel

        /*
        CREATING SIGN-IN PANEL
        */

        JPanel signInPanel = new JPanel (new GridBagLayout ()); //create sign in panel
        signInPanel.setBorder (BorderFactory.createEmptyBorder (20, 20, 20, 20)); //added padding

        gridBagConstraints        = new GridBagConstraints (); //create a grid bag for sign-in
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER; //center align the grid bag on the screen

        signInUsernameField = new JTextField (20); //set text field size
        signInPanel.add (new JLabel ("Username:"), gridBagConstraints); //add username label to panel
        gridBagConstraints.gridy++; //increment the row index

        signInPanel.add (signInUsernameField, gridBagConstraints);
        gridBagConstraints.gridy++; //increment the row index

        signInPasswordField = new JPasswordField (20); //set password field size
        signInPanel.add (new JLabel ("Password:"), gridBagConstraints); //add password label to panel
        gridBagConstraints.gridy++; //increment the row index

        signInPanel.add (signInPasswordField, gridBagConstraints);
        gridBagConstraints.gridy++; //increment the row index

        signInPanel.add (new JPanel (), gridBagConstraints); //empty row to add space
        gridBagConstraints.gridy++; //increment the row index

        gridBagConstraints.weighty = 0; //reset weight for the button

        JButton signInButton = new JButton ("Sign In");
        signInButton.addActionListener (e -> //add action to button
        {
            signIn (); //call the sign-in function
        });

        signInPanel.add (signInButton, gridBagConstraints); //add button to sign in panel

        signUpSignInPanel.add (signUpPanel); //add sign up panel to panel
        signUpSignInPanel.add (signInPanel); //add sign in panel to panel

        mainPanel.add (signUpSignInPanel, BorderLayout.CENTER); //add nested panel to main panel

        JButton returnButton = new JButton ("Return to Front Page"); //create a button for returning to front page
        returnButton.addActionListener (e -> //add action to button
        {
            dispose (); //close the current frame
            DataManager.saveData ();
            new FrontPageGuestMenu (); //open front page menu
        });

        JPanel bottomPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create a panel for return button

        bottomPanel.add (returnButton); //add return button to bottom panel

        mainPanel.add (bottomPanel, BorderLayout.SOUTH); //add bottom panel to main panel

        setContentPane (mainPanel); //set the content pane property to main panel
        setVisible (true); //make the frame visible
    }

    private void signUp ()
    {
        String email = signUpEmailField.getText (); //get email from email text field
        String username = signUpUsernameField.getText (); //get username from username text field
        String password = new String (signUpPasswordField.getPassword ()); //get password from password text field
        String confirmPassword = new String (signUpConfirmPasswordField.getPassword ()); //get confirm password from confirm password text field

        if (User.validateEmail (email) == 2)
        {
            JOptionPane.showMessageDialog (null, "Email already associated with an Account. Please enter a new Email or Sign In.");
        }
        else if (User.validateEmail (email) == 0)
        {
            JOptionPane.showMessageDialog (null, "Invalid Email. Please enter a new Email.");
        }
        else if (User.validateUsername (username) == 2)
        {
            JOptionPane.showMessageDialog (null, "Username already associated with an Account. Please enter a new Username or Sign In.");
        }
        else if (User.validateUsername (username) == 0)
        {
            JOptionPane.showMessageDialog (null, "Invalid Username. Usernames must have at least 6 characters.");
        }
        else if (User.validatePassword (password, confirmPassword) == 2)
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

            User user = new User (email, username, password); //create a new user
            User.addUser (user); //add user to all users list

            JOptionPane.showMessageDialog (null, "Account successfully created.");
            User.setCurrentUser (user); //set the current user to user

            DataManager.saveData ();
            new FrontPageMenu (); //open front page menu
        }
    }

    private void signIn ()
    {
        String username = signInUsernameField.getText (); //get username from username text field
        String password = new String (signInPasswordField.getPassword ()); //get password from password text field

        if (User.validateUsername (username) == 1)
        {
            JOptionPane.showMessageDialog (null, "Username not found. Please enter a new Username or Sign Up.");
        }
        else if (User.validateUsername (username) == 0)
        {
            JOptionPane.showMessageDialog (null, "Invalid Username. Usernames must have at least 6 characters.");
        }
        else
        {
            User user = User.findUserByUsername (username); //find the user to validate password

            assert user != null;
            if (! user.checkPassword (password, user))
            {
                JOptionPane.showMessageDialog (null, "Wrong Password. Please try again.");
            }
            else
            {
                dispose (); //close the current frame

                User.setCurrentUser (user); //set the current user to user
                JOptionPane.showMessageDialog (null, "Login successful.");

                DataManager.saveData ();
                new FrontPageMenu (); //open front page menu
            }
        }
    }
}
