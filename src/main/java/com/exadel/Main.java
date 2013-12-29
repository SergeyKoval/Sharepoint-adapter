package com.exadel;

import com.exadel.entities.LibraryDescription;
import com.exadel.entities.LibraryItem;
import com.exadel.operations.Operations;
import com.exadel.wsdl.WsdlDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    private static String login = "eltegra/skoval";
    private static String password = "Bhbyf25ngl11";

    private static String localFilePath = "D:/lists.wsdl";
    private static String uploadedFileName = "lists.wsdl";

    private static String newLocalFilePath = "D:/lists12.wsdl";

    private static String siteUrl = "http://localhost:18961";


    public static void main(String[] args) throws Exception {
        WsdlDownloader wsdlDownloader = new WsdlDownloader();
        wsdlDownloader.openConnection(siteUrl, login, password);
        wsdlDownloader.getWsdl("copy", "src/main/resources/copy.wsdl");
        wsdlDownloader.getWsdl("lists", "src/main/resources/lists.wsdl");
        wsdlDownloader.closeConnection();

        Operations operations = Operations.getInstance(login, password);

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

        // Creating a library
        String libraryName = "TestLibrary5";
        // operations.addDocumentLibrary(libraryName, "Description");


        // Upload file into library
        operations.uploadSPFile(localFilePath, siteUrl + "/" + libraryName + "/" + uploadedFileName);


        // Display !library! items
        String rowLimit = "150";
        List<LibraryItem> libraryItems = operations.getLibraryItems(libraryName, rowLimit);

        System.out.println("\n" + libraryName + " ITEMS");

        for (LibraryItem item : libraryItems) {
            System.out.println("***********************************");
            System.out.println("Item name: " + item.getItemName());
            System.out.println("Item type: " + item.getType().toString());
        }

        System.out.println("***********************************");



        // Download file from library
        operations.downloadSPFile(siteUrl + "/" + libraryName + "/" + uploadedFileName, newLocalFilePath);

        // Delete file from library
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", siteUrl + "/" + libraryName + "/" + uploadedFileName);

        operations.deleteLibraryItem(libraryName, fields);



        // Remove document library (including all containing files)
        operations.deleteLibrary(libraryName);



        // Creating a library again.. (to show folders operations)
        operations.addDocumentLibrary(libraryName, "Description");


        // All operations for subfolders listed below can be used for folders

        // Make a folder
        fields.clear();
        fields.put("ID", "New");
        fields.put("FSObjType", "1");
        fields.put("BaseName", "TestFolder");

        operations.createNewFolder(libraryName, fields);

        // Make a subfolder
        fields.put("BaseName", "TestFolder/TestSubfolder");
        operations.createNewFolder(libraryName, fields);

        // Make a subsubfolder:)
        fields.put("BaseName", "TestFolder/TestSubfolder/TestSubsubfolder");
        operations.createNewFolder(libraryName, fields);

        // Upload file into subfolder
        operations.uploadSPFile(localFilePath,
                siteUrl + "/" + libraryName + "/TestFolder/TestSubfolder/" + uploadedFileName);

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

        // Download file from subfolder
        operations.downloadSPFile(siteUrl + "/" + libraryName + "/TestFolder/TestSubfolder/" + uploadedFileName,
                newLocalFilePath);

        // Remove file from subfolder
        fields.clear();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", siteUrl + "/" + libraryName + "/TestFolder/TestSubfolder/" + uploadedFileName);

        operations.deleteLibraryItem(libraryName, fields);

        // Remove folder (including subfoler and all containing files)
        fields.put("FileRef", siteUrl + "/" + libraryName + "/TestFolder");

        operations.deleteLibraryItem(libraryName, fields);
    }
}