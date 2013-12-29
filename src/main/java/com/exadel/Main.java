package com.exadel;

import com.exadel.entities.DocumentLibrary;
import com.exadel.operations.Operations;
import com.exadel.wsdl.WsdlDownloader;

import java.util.List;

public class Main {

    private static String login = "eltegra/skoval";
    private static String password = "Bhbyf25ngl11";

    private static String localFilePath = "D:/lists.wsdl";
    private static String uploadedFileName = "lists.wsdl";

    private static String newLocalFilePath = "D:/lists1.wsdl";

    private static String siteUrl = "http://localhost:18961";


    public static void main(String[] args) throws Exception {
        WsdlDownloader wsdlDownloader = new WsdlDownloader();
        wsdlDownloader.openConnection(siteUrl, login, password);
        wsdlDownloader.getWsdl("copy", "src/main/resources/copy.wsdl");
        wsdlDownloader.getWsdl("lists", "src/main/resources/lists.wsdl");
        wsdlDownloader.closeConnection();

        Operations operations = Operations.getInstance(login, password);

        // Get DocumentLibrary list, that contains description for all libraries, available for current user
        List<DocumentLibrary> list = operations.getAvailableDLibraries();

        System.out.println("\nLIBRARY LIST");

        for (DocumentLibrary lib : list) {
            System.out.println("***********************************");
            System.out.println("Document library title: " + lib.getTitle());
            System.out.println("Document library description: " + lib.getDescription());
            System.out.println("Document library items count: " + lib.getItemsCount());
        }

        System.out.println("***********************************");

        /*
        // Creating a library
        String listName = "Test1";
        operations.addDocumentLibrary(listName, "Description");

        // Upload file into library
        operations.uploadSPFile(localFilePath, siteUrl + "/" + listName + "/" + uploadedFileName);

        // Download file from library
        operations.downloadSPFile(siteUrl + "/" + listName + "/" + uploadedFileName, newLocalFilePath);

        // Delete file from library
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", siteUrl + "/" + listName + "/" + uploadedFileName);

        operations.deleteListItem(listName, fields);

        // Remove document library (including all containing files)
        operations.deleteDocumentLibrary(listName);

        // Creating a library again.. (to show folders operations)
        operations.addDocumentLibrary(listName, "Description");

        // All operations for subfolders listed below can be used for folders

        // Make a folder
        fields.clear();
        fields.put("ID", "New");
        fields.put("FSObjType", "1");
        fields.put("BaseName", "TestFolder");

        operations.createNewFolder(listName, fields);

        // Make a subfolder
        fields.put("BaseName", "TestFolder/TestSubfolder");
        operations.createNewFolder(listName, fields);

        // Upload file into subfolder
        operations.uploadSPFile(localFilePath,
                siteUrl + "/" + listName + "/TestFolder/TestSubfolder/" + uploadedFileName);

        // Show subfolder's contents
        List<String> columnNames = new ArrayList<String>(Arrays.asList("LinkFilename", "FileRef"));
        String rowLimit = "100";

        operations.displaySPFolder(listName, siteUrl + "/" +
                listName + "/TestFolder/TestSubfolder", columnNames, rowLimit);

        // Download file from subfolder
        operations.downloadSPFile(siteUrl + "/" + listName + "/TestFolder/TestSubfolder/" + uploadedFileName,
                newLocalFilePath);

        // Remove file from subfolder
        fields.clear();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", siteUrl + "/" + listName + "/TestFolder/TestSubfolder/" + uploadedFileName);

        operations.deleteListItem(listName, fields);

        // Remove folder (including subfoler and all containing files)
        fields.put("FileRef", siteUrl + "/" + listName + "/TestFolder");

        operations.deleteListItem(listName, fields); */
    }
}