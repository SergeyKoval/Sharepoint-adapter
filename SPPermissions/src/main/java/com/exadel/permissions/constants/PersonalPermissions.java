package com.exadel.permissions.constants;

public class PersonalPermissions {

    /**
     * Create, change, and delete personal views of lists.
     */
    public static final int ManagePersonalViews = 0x00000200;

    /**
     * Add or remove personal Web Parts on a Web Part Page.
     */
    public static final int AddDelPrivateWebParts = 0x10000000;

    /**
     * Update Web Parts to display personalized information.
     */
    public static final int UpdatePersonalWebParts = 0x20000000;

    /**
     * All features specified above
     */
    public static final int AllListFeautures;


    static {
        AllListFeautures = ManagePersonalViews | AddDelPrivateWebParts | UpdatePersonalWebParts;
    }
}
