package com.exadel;

import com.exadel.operations.Operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    private static String login = "eltegra\\skoval";
    private static String password = "Bhbyf25ngl11";

    public static void main(String[] args) throws Exception {
        Operations operations = Operations.getInstance(login, password);

        String listName = "Test";
        List<String> columnNames = new ArrayList<String>(Arrays.asList("LinkFilename", "FileRef"));
        String rowLimit = "100";
        operations.displaySPList(listName, columnNames, rowLimit);

        operations.uploadSPFile("D:\\copy.wsdl", "http://sp2013:5108/Test/copy.wsdl");
        operations.downloadSPFile("http://sp2013:5108/Test/copy.wsdl", "D:/copy1.wsdl");

        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("ID", "1"); // It isn't used (We can put here any id)
        fields.put("FileRef", "http://sp2013:5108/Test/Test.doc");

        operations.deleteListItem(listName, fields);
    }
}
