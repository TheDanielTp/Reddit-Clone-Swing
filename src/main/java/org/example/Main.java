package org.example;

import org.example.Menu.FrontPageGuestMenu;
import org.example.Menu.FrontPageMenu;

public class Main
{
    public static Boolean reversed;

    public static void main (String[] args)
    {
        DataManager.loadData ();
        if (User.getCurrentUser () == null)
        {
            new FrontPageGuestMenu ();
        }
        else
        {
            new FrontPageMenu ();
        }
    }
}