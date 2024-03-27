package org.example.Menu;

import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileMenu extends JFrame
{

    public ProfileMenu() {
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton("Return to Front Page");
        returnButton.addActionListener(e -> {
            dispose();
        });
        topPanel.add(returnButton);

        JPanel middlePanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel usernameValueLabel = new JLabel(User.getCurrentUser().getUsername());
        JLabel emailLabel = new JLabel("Email:");
        JLabel emailValueLabel = new JLabel(User.getCurrentUser().getEmail());
        JLabel passwordLabel = new JLabel("Password: **********");
        leftPanel.add(usernameLabel);
        leftPanel.add(emailLabel);
        leftPanel.add(passwordLabel);

        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        JButton changeUsernameButton = new JButton("Change Username");
        JButton changeEmailButton = new JButton("Change Email");
        JButton changePasswordButton = new JButton("Change Password");
        changeUsernameButton.addActionListener(e -> {
        });
        changeEmailButton.addActionListener(e -> {
        });
        changePasswordButton.addActionListener(e -> {
        });
        rightPanel.add(changeUsernameButton);
        rightPanel.add(changeEmailButton);
        rightPanel.add(changePasswordButton);

        middlePanel.add(leftPanel, BorderLayout.WEST);
        middlePanel.add(rightPanel, BorderLayout.EAST);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(middlePanel, BorderLayout.CENTER);

        setVisible(true);
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