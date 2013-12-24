package com.exadel;

import com.exadel.operations.Operations;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    private static String login = "eltegra\\skoval";
    private static String password = "ENTER YOUR PASSWORD HERE";


    public static void main(String[] args) throws Exception {
        Operations operations = Operations.getInstance(login, password);

        String listName = "Test";
        List<String> columnNames = new ArrayList<String>(Arrays.asList("LinkFilename", "FileRef"));
        String rowLimit = "100";
        operations.displaySPList(listName, columnNames, rowLimit);

        operations.uploadSPFile("D:\\copy.wsdl", "http://sp2013:5108/Test/copy.wsdl");
        operations.downloadSPFile("http://localhost:12192/Test/copy.wsdl", "D:/copy1.wsdl");

        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", "http://localhost:12192/Test/lists.wsdl");

        operations.deleteListItem(listName, fields);
    }
}