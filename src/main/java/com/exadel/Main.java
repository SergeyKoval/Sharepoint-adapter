package com.exadel;

import com.exadel.entities.LibraryDescription;
import com.exadel.entities.LibraryItem;
import com.exadel.operations.Operations;
import com.exadel.wsdl.WsdlDownloader;

import java.util.List;

public class Main {

    private static String login = "eltegra/skoval";
    private static String password = "";

    private static String localFilePath = "D:/wss.doc";
    private static String uploadedFileName = "wss.doc";

    private static String newLocalFilePath = "D:/wss1.doc";

    private static String siteUrl = "http://sp2013:5108";


    public static void main(String[] args) throws Exception {

        // Downloading wsdl for specified site (specify it above)
        WsdlDownloader wsdlDownloader = new WsdlDownloader();
        wsdlDownloader.openConnection(siteUrl, login, password);
        wsdlDownloader.getWsdl("copy", "src/main/resources/copy.wsdl");
        wsdlDownloader.getWsdl("lists", "src/main/resources/lists.wsdl");
        wsdlDownloader.closeConnection();
        // ----------------------------------------------------------------------------------------------------


        // Initializing soap services
        Operations operations = Operations.getInstance(login, password, siteUrl);
        // ----------------------------------------------------------------------------------------------------


        // Get LibraryDescription list, that contains description for all libraries, available for current user
        List<LibraryDescription> descriptions = operations.getDescrForAvailableDocLibs();

        System.out.println("\nLIST OF LIBRARIES");

        for (LibraryDescription lib : descriptions) {
            System.out.println("***********************************");
            System.out.println("Document library title: " + lib.getTitle());
            System.out.println("Document library description: " + lib.getDescription());
            System.out.println("Document library items count: " + lib.getItemsCount());
        }

        System.out.println("***********************************\n");
        // ----------------------------------------------------------------------------------------------------


        // Creating a library
        String libraryName = "TestLibrary3";  // If library already exist on site, exception will be thrown
        operations.addDocumentLibrary(libraryName, "Description");
        // ----------------------------------------------------------------------------------------------------


        // Add library to site's quick launch bar
        operations.addLibraryToQuickLaunch(libraryName);
        // ----------------------------------------------------------------------------------------------------


        // Upload file into library
        operations.uploadFile(localFilePath, siteUrl + "/" + libraryName + "/" + uploadedFileName);
        // ----------------------------------------------------------------------------------------------------


        // Display specified library items
        String rowLimit = "150";
        List<LibraryItem> libraryItems = operations.getLibraryItems(libraryName, rowLimit);

        System.out.println("\n" + libraryName + " ITEMS");

        for (LibraryItem item : libraryItems) {
            System.out.println("***********************************");
            System.out.println("Item name: " + item.getItemName());
            System.out.println("Item type: " + item.getType().toString());
        }

        System.out.println("***********************************");
        // ----------------------------------------------------------------------------------------------------


        // Download file from library
        operations.downloadFile(siteUrl + "/" + libraryName + "/" + uploadedFileName, newLocalFilePath);
        // ----------------------------------------------------------------------------------------------------


        // Delete file from library
        operations.deleteLibraryFile(libraryName, uploadedFileName);
        // ----------------------------------------------------------------------------------------------------


        // Remove document library (including all containing files)
        operations.deleteLibrary(libraryName);
        // ----------------------------------------------------------------------------------------------------


        // Creating a library again.. (to show folders operations)
        operations.addDocumentLibrary(libraryName, "Description");
        // ----------------------------------------------------------------------------------------------------


        // All operations for subfolders listed below can be used for folders


        // Make a folder
        operations.createNewFolder(libraryName, "TestFolder");
        // ----------------------------------------------------------------------------------------------------


        // Make a subfolder
        operations.createNewFolder(libraryName, "TestFolder/TestSubfolder");
        // ----------------------------------------------------------------------------------------------------


        // Make a subsubfolder:)
        operations.createNewFolder(libraryName, "TestFolder/TestSubfolder/TestSubsubfolder");
        // ----------------------------------------------------------------------------------------------------


        // Upload file into subfolder
        operations.uploadFile(localFilePath,
                siteUrl + "/" + libraryName + "/TestFolder/TestSubfolder/" + uploadedFileName);
        // ----------------------------------------------------------------------------------------------------


        // Show subfolder's contents
        List<LibraryItem> folderItems = operations.getFolderItems(libraryName, siteUrl + "/" +
                libraryName + "/TestFolder/TestSubfolder", rowLimit);

        System.out.println("\n" + "/TestFolder/TestSubfolder" + " ITEMS");

        for (LibraryItem item : folderItems) {
            System.out.println("***********************************");
            System.out.println("Item name: " + item.getItemName());
            System.out.println("Item type: " + item.getType().toString());
        }

        System.out.println("***********************************");
        // ----------------------------------------------------------------------------------------------------


        // Download file from subfolder
        operations.downloadFile(siteUrl + "/" + libraryName + "/TestFolder/TestSubfolder/" + uploadedFileName,
                newLocalFilePath);
        // ----------------------------------------------------------------------------------------------------


        // Remove file from subfolder
        operations.deleteLibraryFile(libraryName, "/TestFolder/TestSubfolder/" + uploadedFileName);
        // ----------------------------------------------------------------------------------------------------


        // Remove folder (including subfoler and all containing files)
        operations.deleteLibraryFolder(libraryName, "TestFolder");
        // ----------------------------------------------------------------------------------------------------
    }
}