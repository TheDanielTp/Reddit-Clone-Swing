package org.example;

import org.example.Menu.FrontPageGuestMenu;

public class Main
{
    public static Boolean reversed;

    public static void main (String[] args)
    {
        DataManager.loadData ();
        new FrontPageGuestMenu ();
    }
}