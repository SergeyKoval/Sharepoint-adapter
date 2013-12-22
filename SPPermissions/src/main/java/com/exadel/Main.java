package com.exadel;

import com.exadel.permissions.operations.Operations;
import com.exadel.permissions.constants.*;

public class Main {

    private static String login = "eltegra\\skoval";
    private static String password = "Bhbyf25ngl11";


    public static void main(String[] args) throws Exception {
        Operations operations = Operations.getInstance(login, password);

            /* ATTENTION! Already have been added
            operations.addRole("SiteUsers", "Users that can create and manage own libraries",
                            ListsPermissions.AllFeautures |
                            SitePermissions.ViewPages |
                            SitePermissions.OpenWeb |
                            SitePermissions.BrowseUserInfo);
            */

        // ATTENTION! Already have been added

        operations.addUserToRole("SiteUsers", "Sergey Koval", "i:0#.w|eltegra\\skoval", "skoval@exadel.com", "SD");


            /*
            operations updateRole("SiteUsers", "SiteUsers", "Users that can create and manage own libraries",
                            ListsPermissions.AllFeautures |
                            SitePermissions.ViewPages |
                            SitePermissions.OpenWeb |
                            SitePermissions.BrowseUserInfo |
                            SitePermissions.ManagePermissions |
                            SitePermissions.BrowseDirectories |
                            0);
            */


        operations.addPermissions("KovalLibrary", "List", "i:0#.w|eltegra\\skoval", "user", ListsPermissions.AllFeautures);
    }
}



