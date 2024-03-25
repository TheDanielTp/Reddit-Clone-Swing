package org.example.Menu;

import org.example.Subreddit;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreateSubredditMenu extends JFrame
{
    protected JTextField titleField;
    protected JTextArea descriptionArea;
    protected JCheckBox over18Checkbox;

    public CreateSubredditMenu ()
    {
        setTitle ("Create Subreddit");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //exit the program when window is closed

        setSize (600, 400); //set window size
        setLocationRelativeTo (null); //center the frame on the screen

        JPanel mainPanel = new JPanel (new BorderLayout ()); //create main panel

        JPanel optionsPanel = new JPanel (new GridBagLayout ()); //create options panel

        GridBagConstraints gridBagConstraints = new GridBagConstraints (); //create a grid bag for creating subreddit
        gridBagConstraints.anchor = GridBagConstraints.CENTER; //center the grid bag on window
        gridBagConstraints.insets = new Insets (5, 5, 5, 5); //set grid bag size

        gridBagConstraints.gridx = 0; //sets the horizontal grid position for the component being added to the container
        gridBagConstraints.gridy = 0; //sets the vertical grid position for the component being added to the container

        JPanel textFieldsPanel = new JPanel (); //create text fields panel
        textFieldsPanel.setLayout (new BoxLayout (textFieldsPanel, BoxLayout.Y_AXIS)); //set the layout to box layout

        JLabel titleLabel = new JLabel ("Title:");
        titleLabel.setAlignmentX (Component.CENTER_ALIGNMENT); //center the title label

        titleField = new JTextField (10); //set the number of columns to 10
        titleField.setMaximumSize (new Dimension (400, 25)); //set the maximum size to limit the width and height

        JLabel descriptionLabel = new JLabel ("Description:");
        descriptionLabel.setAlignmentX (Component.CENTER_ALIGNMENT); //center the description label

        descriptionArea = new JTextArea ();
        descriptionArea.setAlignmentX (Component.CENTER_ALIGNMENT); //center the description text area

        JScrollPane scrollPane = new JScrollPane (descriptionArea); //create a scroll pane for description text area

        over18Checkbox = new JCheckBox("18+ year old community");
        over18Checkbox.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add the components to the panel
        textFieldsPanel.add (titleLabel);
        textFieldsPanel.add (titleField);
        textFieldsPanel.add (descriptionLabel);
        textFieldsPanel.add (scrollPane);
        textFieldsPanel.add (over18Checkbox);

        JPanel buttonsPanel = getjPanel (); //create buttons panel

        //add panels to the main panel
        mainPanel.add (optionsPanel, BorderLayout.NORTH);
        mainPanel.add (textFieldsPanel, BorderLayout.CENTER);
        mainPanel.add (buttonsPanel, BorderLayout.SOUTH);

        add (mainPanel);
        setVisible (true); //make the frame visible
    }

    private JPanel getjPanel ()
    {
        JPanel buttonsPanel = new JPanel (new FlowLayout (FlowLayout.CENTER)); //create buttons panel

        JButton returnButton = new JButton ("Return to Front Page");
        returnButton.setBackground (new Color (0x0079d3)); //set button color to blue
        returnButton.setForeground (new Color (0xffffff)); //set text color to white
        returnButton.addActionListener (e ->
        {
            dispose (); //close the current frame
            new FrontPageMenu (); //open front page menu
        });

        JButton subredditButton = new JButton ("Create Subreddit");
        subredditButton.setBackground (new Color (0xff4500)); //set button color to orange
        subredditButton.setForeground (new Color (0xffffff)); //set text color to white
        subredditButton.addActionListener (e ->
        {
            String title = titleField.getText (); //get title from title field
            String description = descriptionArea.getText (); //get description from description area
            boolean nsfw = over18Checkbox.isSelected ();

            if (title.isEmpty ()) //if no title is entered
            {
                JOptionPane.showMessageDialog (null, "Please enter a Title.");
            }
            else if (description.isEmpty ()) //if no description is entered
            {
                JOptionPane.showMessageDialog (null, "Please write your Subreddit's description.");
            }
            else
            {
                int answer = JOptionPane.showConfirmDialog (null, "Do you confirm?", "Title", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION)
                {
                    User user = User.getCurrentUser (); //get the current user

                    Subreddit subreddit = new Subreddit (title, description, user, nsfw);
                    Subreddit.addSubreddit (subreddit);

                    dispose (); //close the current frame
                    JOptionPane.showMessageDialog (null, "Subreddit created successfully!");
                    new FrontPageMenu (); //open front page menu
                }

            }
        });

        //add buttons to the panel
        buttonsPanel.add (returnButton);
        buttonsPanel.add (subredditButton);

        return buttonsPanel;
    }

    //main function for testing
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater (CreateSubredditMenu :: new);
    }
}
