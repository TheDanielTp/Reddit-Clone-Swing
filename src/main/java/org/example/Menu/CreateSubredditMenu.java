package org.example.Menu;

import org.example.Subreddit;
import org.example.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class CreateSubredditMenu extends JFrame
{
    protected JTextField titleField;
    protected JTextArea  descriptionArea;
    protected JCheckBox  over18Checkbox;
    protected JLabel     characterCountLabelTitle;

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

        JPanel textFieldsPanel = new JPanel (); //create text fields panel
        textFieldsPanel.setLayout (new BoxLayout (textFieldsPanel, BoxLayout.Y_AXIS)); //set the layout to box layout

        JLabel titleLabel = new JLabel ("Title:");
        titleLabel.setAlignmentX (Component.CENTER_ALIGNMENT); //center the title label

        JPanel titlePanel = new JPanel (); //create a panel for title and current length
        titlePanel.setLayout (new BoxLayout (titlePanel, BoxLayout.X_AXIS)); //set the layout to box layout

        titleField = new JTextField (10);
        titleField.setMaximumSize (new Dimension (400, 25));
        setMaxLength (titleField); // Limit title field to 32 characters

        characterCountLabelTitle = new JLabel (" 0/32"); //add a label to show input limit
        characterCountLabelTitle.setAlignmentX (Component.CENTER_ALIGNMENT); //center the label

        titleField.getDocument ().addDocumentListener (new DocumentListener ()
        {
            @Override
            public void insertUpdate (DocumentEvent e)
            {
                updateCharacterCount (); //update characters count when a character is entered
            }

            @Override
            public void removeUpdate (DocumentEvent e)
            {
                updateCharacterCount (); //update characters count when a character is removed
            }

            @Override
            public void changedUpdate (DocumentEvent e)
            {
                updateCharacterCount (); //update characters count when a character is changed
            }
        });

        //add the components to title panel
        titlePanel.add (titleField);
        titlePanel.add (characterCountLabelTitle);

        JLabel descriptionLabel = new JLabel ("Description:");
        descriptionLabel.setAlignmentX (Component.CENTER_ALIGNMENT); //center the description label

        descriptionArea = new JTextArea ();
        descriptionArea.setAlignmentX (Component.CENTER_ALIGNMENT); //center the description text area

        JScrollPane scrollPane = new JScrollPane (descriptionArea); //create a scroll pane for description text area

        over18Checkbox = new JCheckBox ("18+ year old community"); //create checkbox for nsfw community
        over18Checkbox.setAlignmentX (Component.CENTER_ALIGNMENT); //center the checkbox

        //add the components to text fields panel
        textFieldsPanel.add (titleLabel);
        textFieldsPanel.add (titlePanel);
        textFieldsPanel.add (descriptionLabel);
        textFieldsPanel.add (scrollPane);
        textFieldsPanel.add (over18Checkbox);

        JPanel buttonsPanel = getButtonsPanel (); //create buttons panel

        //add panels to the main panel
        mainPanel.add (optionsPanel, BorderLayout.NORTH);
        mainPanel.add (textFieldsPanel, BorderLayout.CENTER);
        mainPanel.add (buttonsPanel, BorderLayout.SOUTH);

        add (mainPanel);
        setVisible (true); //make the frame visible
    }

    private void setMaxLength (JTextField textField)
    {
        textField.setDocument (new PlainDocument ()
        {
            @Override //controls how text is inserted
            public void insertString (int offset, String string, AttributeSet attributeSet)
                    throws BadLocationException //throws exception if offset is invalid
            {
                if (string == null)
                {
                    return; //return if title is currently empty
                }

                if ((getLength () + string.length ()) <= 32) //checks if title and input will have a total of less than 32 characters
                {
                    super.insertString (offset, string, attributeSet); //allows the string to be inserted
                }
            }
        });
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

        JButton subredditButton = new JButton ("Create Subreddit");
        subredditButton.setBackground (new Color (0xff4500)); //set button color to orange
        subredditButton.setForeground (new Color (0xffffff)); //set text color to white
        subredditButton.addActionListener (e -> //add action to the button
        {
            String title = titleField.getText (); //gets subreddit's title from title field
            String description = descriptionArea.getText (); //gets subreddit's description from description field
            boolean nsfw = over18Checkbox.isSelected (); //gets nsfw boolean from the checkbox

            if (title.isEmpty ()) //if no title is entered
            {
                JOptionPane.showMessageDialog (null, "Please enter a Title.");
            }
            else if (! validateTitle (title))
            {
                JOptionPane.showMessageDialog (null, "This Title is already taken. Please enter a new Title.");
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

                    Subreddit subreddit = new Subreddit (title, description, user, nsfw); //create new subreddit
                    Subreddit.addSubreddit (subreddit); //add subreddit to all subreddits list

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

    private boolean validateTitle (String title)
    {
        for (Subreddit subreddit : Subreddit.getAllSubreddits ())
        {
            if (subreddit.getTitle ().equalsIgnoreCase (title))
            {
                return false;
            }
        }
        return true;
    }

    private void updateCharacterCount ()
    {
        int currentLength = titleField.getText ().length ();
        if (currentLength < 10)
        {
            characterCountLabelTitle.setText ("  " + currentLength + "/32"); //print two spaces if length is less than 10
        }
        else
        {
            characterCountLabelTitle.setText (" " + currentLength + "/32"); //print one space if length is more than 10
        }
    }

    //main function for testing
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater (CreateSubredditMenu :: new);
    }
}
