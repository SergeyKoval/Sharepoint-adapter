package com.exadel.permissions.operations;

import com.microsoft.schemas.sharepoint.soap.Permissions;
import com.microsoft.schemas.sharepoint.soap.PermissionsSoap;
import com.microsoft.schemas.sharepoint.soap.UserGroup;
import com.microsoft.schemas.sharepoint.soap.UserGroupSoap;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import java.io.StringWriter;

public class Operations {

    private PermissionsSoap permissionsSoap;
    private UserGroupSoap userGroupSoap;

    private static Operations operations;


    private Operations(String login, String password) throws Exception {
        permissionsSoapAuthentication(login, password);
        userGroupSoapAuthentication(login, password);
    }



    private void permissionsSoapAuthentication(String login, String password) throws Exception {
        Permissions service = new Permissions();
        permissionsSoap = service.getPermissionsSoap();

        serviceAuthentication(permissionsSoap, login, password);
    }


    private void userGroupSoapAuthentication(String login, String password) throws Exception {
        UserGroup service = new UserGroup();
        userGroupSoap = service.getUserGroupSoap();

        serviceAuthentication(userGroupSoap, login, password);
    }


    private void serviceAuthentication(Object soap, String login, String password) throws Exception {
        if ((login != null) && (password != null)) {
            ((BindingProvider) soap).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, login);
            ((BindingProvider) soap).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
        } else {
            throw new Exception("Invalid credentials was given");
        }
    }


    public String xmlToString(Document docToString) {
        StringBuilder result = new StringBuilder("@@@@@@ BEGIN @@@@@@\n");

        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter strWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(strWriter);
            DOMSource source = new DOMSource(docToString);
            transformer.transform(source, streamResult);
            String xmlString = strWriter.toString();

            result.append(xmlString);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }

        result.append("@@@@@@ END @@@@@@\n");
        return result.toString();
    }


    public void addPermissions(String objectName, String objectType, String permissionIdentifier,
                               String permissionType, int permissionMask) {
        permissionsSoap.addPermission(objectName, objectType, permissionIdentifier, permissionType, permissionMask);
    }


    /**
     * Adds the user to the specified user group.
     *
     * @param groupName
     * @param userName
     * @param userLoginName
     * @param userEmail
     * @param userNotes
     */
    public void addUserToGroup(String groupName, String userName,
                                      String userLoginName, String userEmail, String userNotes) {
        userGroupSoap.addUserToGroup(groupName, userName, userLoginName, userEmail, userNotes);
    }


    /**
     * Adds a role definition to the current site collection.
     *
     * @param roleName
     * @param description
     * @param permissionMask A 32-bit integer that represents a com.exadel.permissions.constants.PermissionMasks value (or combination)
     */
    public void addRole(String roleName, String description, int permissionMask) {
        userGroupSoap.addRole(roleName, description, permissionMask);
    }


    /**
     * Update a role definition.
     *
     * @param previousRoleName
     * @param newRoleName
     * @param newDescription
     * @param permissionMask A 32-bit integer that represents a com.exadel.permissions.constants.PermissionMasks value (or combination)
     */
    public void updateRole(String previousRoleName, String newRoleName,
                                  String newDescription, int permissionMask) {
        userGroupSoap.updateRoleInfo(previousRoleName, newRoleName, newDescription, permissionMask);
    }


    /**
     * Adds the user to the specified role definition.
     *
     * @param roleName
     * @param userName
     * @param userLoginName
     * @param userEmail
     * @param userNotes
     */
    public void addUserToRole(String roleName, String userName,
                                     String userLoginName, String userEmail, String userNotes) {
        userGroupSoap.addUserToRole(roleName, userName, userLoginName, userEmail, userNotes);
    }


    public static Operations getInstance(String login, String password) throws Exception {
        if (operations == null) {
            operations = new Operations(login, password);
        }

        return operations;
    }
}
