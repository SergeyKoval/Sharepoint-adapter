package com.exadel.permissions.constants;

public final class ListsPermissions {

    private ListsPermissions() {

    }

    /**
     * Create and delete lists, add or remove columns in a list,
     * and add or remove public views of a list.
     */
    public static final int ManageLists =  0x00000800;

    /**
     * Check in a document without saving the current changes.
     */
    public static final int CancelCheckout = 0x00000100;

    /**
     * Add items to lists, add documents to document libraries,
     * and add Web discussion comments
     */
    public static final int AddListItems = 0x00000002;

    /**
     * Edit items in lists, edit documents in document libraries,
     * edit Web discussion comments in documents, and customize
     * Web Part Pages in document libraries.
     */
    public static final int EditListItems = 0x00000004;

    /**
     * Delete items from a list, documents from a document library,
     * and Web discussion comments in documents.
     */
    public static final int DeleteListItems = 0x00000008;

    /**
     * View items in lists, documents in document libraries,
     * and view Web discussion comments.
     */
    public static final int ViewListItems = 0x00000001;

    /**
     * Approve a minor version of a list item or document.
     */
    public static final int ApproveListItems = 0x00000010;

    /**
     * View the source of documents with server-side file handlers.
     */
    public static final int OpenListItems = 0x00000020;

    /**
     * View past versions of a list item or document.
     */
    public static final int ViewListItemsVersions = 0x00000040;

    /**
     * Delete past versions of a list item or document.
     */
    public static final int DeleteListItemsVersions = 0x00000080;

    /**
     * View forms, views, and application pages. Enumerate lists.
     */
    public static final int ViewFormPages = 0x00001000;

    /**
     * All features specified above
     */
    public static final int AllFeautures;


    static {
        AllFeautures = ManageLists | CancelCheckout | AddListItems |
                EditListItems | DeleteListItems | ViewListItems | ApproveListItems |
                OpenListItems | ViewListItemsVersions | DeleteListItemsVersions | ViewFormPages;
    }
}
