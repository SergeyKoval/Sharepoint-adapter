package com.exadel.permissions.constants;

public class SitePermissions {

    private SitePermissions() {

    }

    /**
     * Create and change permission levels on the Web site and assign permissions to users and groups.
     */
    public static final int ManagePermissions = 0x02000000;

    /**
     * View reports on Web site usage.
     */
    public static final int ViewUsageData =  0x00200000;

    /**
     * Create subsites such as team sites, Meeting Workspace sites,
     * and Document Workspace sites.
     */
    public static final int ManageSubwebs =  0x00800000;

    /**
     * Manage a site, including the ability to perform all
     * administration tasks for the site and manage contents and permissions.
     */
    public static final int ManageWeb = 0x40000000;

    /**
     * Add, change, or delete HTML pages or Web Part Pages,
     * and edit the Web site using a
     * Windows SharePoint Services–compatible editor.
     */
    public static final int AddAndCustomizePages = 0x00040000;

    /**
     * Apply a theme or borders to the entire Web site.
     */
    public static final int ApplyThemeAndBorder = 0x00080000;

    /**
     * Apply a style sheet (.css file) to the Web site.
     */
    public static final int ApplyStyleSheets = 0x00100000;

    /**
     * Create a group of users that can be used anywhere within the site collection.
     */
    public static final int CreatePersonalGroups = 0x01000000;

    /**
     *  Enumerate files and folders in a Web site by using
     *  Microsoft Office SharePoint Designer 2007 and Web DAV interfaces.
     */
    public static final int BrowseDirectories = 0x04000000;

    /**
     * Create a Web site using Self-Service Site Creation.
     */
    public static final int CreateSSCSite = 0x00400000;

    /**
     * View pages in a Web site.
     */
    public static final int ViewPages = 0x00020000;

    /**
     * View information about users of the web site.
     */
    public static final int BrowseUserInfo = 0x08000000;

    /**
     * Allows users to open a Web site, list, or folder in order to access items inside that container.
     */
    public static final int OpenWeb = 0x00010000;

    /**
     * All features specified above
     */
    public static final int AllListFeautures;


    static {
        AllListFeautures = ManagePermissions | ViewUsageData | ManageSubwebs | ManageWeb |
                AddAndCustomizePages | ApplyThemeAndBorder | ApplyStyleSheets | CreatePersonalGroups |
                BrowseDirectories | CreateSSCSite | ViewPages | BrowseUserInfo | OpenWeb;
    }
}
