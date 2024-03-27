package org.example.Menu;

import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class SignInSignUpMenu extends JFrame
{
    public static Scanner scanner = new Scanner (System.in);

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

        GridBagConstraints gridBagConstraints = new GridBagConstraints (); //create a grid bag for sign-in / sign-up
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        /*
        CREATING SIGN-UP PANEL
        */

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
        signUpButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                signUp (); //call the sign-up function
            }
        });
        signUpPanel.add (signUpButton, gridBagConstraints);

        /*
        CREATING SIGN-IN PANEL
        */

        JPanel signInPanel = new JPanel (new GridBagLayout ()); //create sign in panel
        signInPanel.setBorder (BorderFactory.createEmptyBorder (20, 20, 20, 20)); // Added padding
        gridBagConstraints        = new GridBagConstraints ();
        gridBagConstraints.gridx  = 0;
        gridBagConstraints.gridy  = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        signInUsernameField       = new JTextField (20); // Set preferred width
        signInPanel.add (new JLabel ("Username:"), gridBagConstraints);
        gridBagConstraints.gridy++;
        signInPanel.add (signInUsernameField, gridBagConstraints);
        gridBagConstraints.gridy++;
        signInPasswordField = new JPasswordField (20); // Set preferred width
        signInPanel.add (new JLabel ("Password:"), gridBagConstraints);
        gridBagConstraints.gridy++;
        signInPanel.add (signInPasswordField, gridBagConstraints);
        gridBagConstraints.gridy++;
        signInPanel.add (new JPanel (), gridBagConstraints); // Empty row for spacing
        gridBagConstraints.gridy++;
        gridBagConstraints.weighty = 0; // Reset weight for the button
        JButton signInButton = new JButton ("Sign In");
        signInButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                signIn ();
            }
        });
        signInPanel.add (signInButton, gridBagConstraints);

        signUpSignInPanel.add (signUpPanel); // Add sign up panel to nested panel
        signUpSignInPanel.add (signInPanel); // Add sign in panel to nested panel

        mainPanel.add (signUpSignInPanel, BorderLayout.CENTER); // Add nested panel to main panel

        // Return to Front Page Button
        JButton returnButton = new JButton ("Return to Front Page");
        returnButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                dispose ();
                new FrontPageGuestMenu ();
            }
        });

        // Add return button to the bottom
        JPanel bottomPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));
        bottomPanel.add (returnButton);
        mainPanel.add (bottomPanel, BorderLayout.SOUTH);

        setContentPane (mainPanel);
        setVisible (true);
    }

    private void signUp ()
    {
        String email = signUpEmailField.getText ();
        String username = signUpUsernameField.getText ();
        String password = new String (signUpPasswordField.getPassword ());
        String confirmPassword = new String (signUpConfirmPasswordField.getPassword ());

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
            dispose ();

            User user = new User (email, username, password);
            User.addUser (user);

            JOptionPane.showMessageDialog (null, "Account successfully created.");
            User.setCurrentUser (user);

            new FrontPageMenu ();
        }
    }

    private void signIn ()
    {
        String username = signInUsernameField.getText ();
        String password = new String (signInPasswordField.getPassword ());

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
            User user = User.findUserByUsername (username);
            assert user != null;

            if (! user.checkPassword (password, user))
            {
                JOptionPane.showMessageDialog (null, "Wrong Password. Please try again.");
            }
            else
            {
                dispose ();

                User.setCurrentUser (user);
                JOptionPane.showMessageDialog (null, "Login successful.");

                new FrontPageMenu ();
            }
        }
    }

    /*
    MAIN FUNCTION
    */

    public static void main (String[] args)
    {
        User user = new User ("prof.danial4@gmail.com", "TheDanielTp", "Tdtp3148_P");
        User.addUser (user);
        SwingUtilities.invokeLater (SignInSignUpMenu :: new);
    }
}
