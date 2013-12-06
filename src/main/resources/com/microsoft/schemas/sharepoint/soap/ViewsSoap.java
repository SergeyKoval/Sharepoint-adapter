
package com.microsoft.schemas.sharepoint.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ViewsSoap", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ViewsSoap {


    /**
     * 
     * @param listName
     * @param viewName
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.GetViewResponse.GetViewResult
     */
    @WebMethod(operationName = "GetView", action = "http://schemas.microsoft.com/sharepoint/soap/GetView")
    @WebResult(name = "GetViewResult", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "GetView", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.GetView")
    @ResponseWrapper(localName = "GetViewResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.GetViewResponse")
    public com.microsoft.schemas.sharepoint.soap.GetViewResponse.GetViewResult getView(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName);

    /**
     * 
     * @param listName
     * @param viewName
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.GetViewHtmlResponse.GetViewHtmlResult
     */
    @WebMethod(operationName = "GetViewHtml", action = "http://schemas.microsoft.com/sharepoint/soap/GetViewHtml")
    @WebResult(name = "GetViewHtmlResult", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "GetViewHtml", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.GetViewHtml")
    @ResponseWrapper(localName = "GetViewHtmlResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.GetViewHtmlResponse")
    public com.microsoft.schemas.sharepoint.soap.GetViewHtmlResponse.GetViewHtmlResult getViewHtml(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName);

    /**
     * 
     * @param listName
     * @param viewName
     */
    @WebMethod(operationName = "DeleteView", action = "http://schemas.microsoft.com/sharepoint/soap/DeleteView")
    @RequestWrapper(localName = "DeleteView", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.DeleteView")
    @ResponseWrapper(localName = "DeleteViewResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.DeleteViewResponse")
    public void deleteView(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName);

    /**
     * 
     * @param listName
     * @param viewName
     * @param query
     * @param viewFields
     * @param makeViewDefault
     * @param type
     * @param rowLimit
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.AddViewResponse.AddViewResult
     */
    @WebMethod(operationName = "AddView", action = "http://schemas.microsoft.com/sharepoint/soap/AddView")
    @WebResult(name = "AddViewResult", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "AddView", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.AddView")
    @ResponseWrapper(localName = "AddViewResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.AddViewResponse")
    public com.microsoft.schemas.sharepoint.soap.AddViewResponse.AddViewResult addView(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName,
        @WebParam(name = "viewFields", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.AddView.ViewFields viewFields,
        @WebParam(name = "query", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.AddView.Query query,
        @WebParam(name = "rowLimit", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.AddView.RowLimit rowLimit,
        @WebParam(name = "type", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String type,
        @WebParam(name = "makeViewDefault", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        boolean makeViewDefault);

    /**
     * 
     * @param listName
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.GetViewCollectionResponse.GetViewCollectionResult
     */
    @WebMethod(operationName = "GetViewCollection", action = "http://schemas.microsoft.com/sharepoint/soap/GetViewCollection")
    @WebResult(name = "GetViewCollectionResult", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "GetViewCollection", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.GetViewCollection")
    @ResponseWrapper(localName = "GetViewCollectionResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.GetViewCollectionResponse")
    public com.microsoft.schemas.sharepoint.soap.GetViewCollectionResponse.GetViewCollectionResult getViewCollection(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName);

    /**
     * 
     * @param listName
     * @param viewName
     * @param query
     * @param viewFields
     * @param aggregations
     * @param viewProperties
     * @param rowLimit
     * @param formats
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.UpdateViewResponse.UpdateViewResult
     */
    @WebMethod(operationName = "UpdateView", action = "http://schemas.microsoft.com/sharepoint/soap/UpdateView")
    @WebResult(name = "UpdateViewResult", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "UpdateView", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.UpdateView")
    @ResponseWrapper(localName = "UpdateViewResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.UpdateViewResponse")
    public com.microsoft.schemas.sharepoint.soap.UpdateViewResponse.UpdateViewResult updateView(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName,
        @WebParam(name = "viewProperties", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateView.ViewProperties viewProperties,
        @WebParam(name = "query", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateView.Query query,
        @WebParam(name = "viewFields", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateView.ViewFields viewFields,
        @WebParam(name = "aggregations", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateView.Aggregations aggregations,
        @WebParam(name = "formats", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateView.Formats formats,
        @WebParam(name = "rowLimit", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateView.RowLimit rowLimit);

    /**
     * 
     * @param listName
     * @param rowLimitExceeded
     * @param toolbar
     * @param query
     * @param viewFields
     * @param viewHeader
     * @param viewProperties
     * @param aggregations
     * @param viewFooter
     * @param formats
     * @param viewEmpty
     * @param viewName
     * @param viewBody
     * @param rowLimit
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.UpdateViewHtmlResponse.UpdateViewHtmlResult
     */
    @WebMethod(operationName = "UpdateViewHtml", action = "http://schemas.microsoft.com/sharepoint/soap/UpdateViewHtml")
    @WebResult(name = "UpdateViewHtmlResult", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "UpdateViewHtml", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.UpdateViewHtml")
    @ResponseWrapper(localName = "UpdateViewHtmlResponse", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.UpdateViewHtmlResponse")
    public com.microsoft.schemas.sharepoint.soap.UpdateViewHtmlResponse.UpdateViewHtmlResult updateViewHtml(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName,
        @WebParam(name = "viewProperties", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.ViewProperties viewProperties,
        @WebParam(name = "toolbar", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.Toolbar toolbar,
        @WebParam(name = "viewHeader", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.ViewHeader viewHeader,
        @WebParam(name = "viewBody", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.ViewBody viewBody,
        @WebParam(name = "viewFooter", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.ViewFooter viewFooter,
        @WebParam(name = "viewEmpty", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.ViewEmpty viewEmpty,
        @WebParam(name = "rowLimitExceeded", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.RowLimitExceeded rowLimitExceeded,
        @WebParam(name = "query", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.Query query,
        @WebParam(name = "viewFields", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.ViewFields viewFields,
        @WebParam(name = "aggregations", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.Aggregations aggregations,
        @WebParam(name = "formats", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.Formats formats,
        @WebParam(name = "rowLimit", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml.RowLimit rowLimit);

    /**
     * 
     * @param listName
     * @param rowLimitExceeded
     * @param toolbar
     * @param query
     * @param viewFields
     * @param viewHeader
     * @param viewProperties
     * @param aggregations
     * @param viewFooter
     * @param formats
     * @param viewEmpty
     * @param openApplicationExtension
     * @param viewName
     * @param viewBody
     * @param rowLimit
     * @return
     *     returns com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2Response.UpdateViewHtml2Result
     */
    @WebMethod(operationName = "UpdateViewHtml2", action = "http://schemas.microsoft.com/sharepoint/soap/UpdateViewHtml2")
    @WebResult(name = "UpdateViewHtml2Result", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
    @RequestWrapper(localName = "UpdateViewHtml2", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2")
    @ResponseWrapper(localName = "UpdateViewHtml2Response", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/", className = "com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2Response")
    public com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2Response.UpdateViewHtml2Result updateViewHtml2(
        @WebParam(name = "listName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String listName,
        @WebParam(name = "viewName", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String viewName,
        @WebParam(name = "viewProperties", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .ViewProperties viewProperties,
        @WebParam(name = "toolbar", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .Toolbar toolbar,
        @WebParam(name = "viewHeader", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .ViewHeader viewHeader,
        @WebParam(name = "viewBody", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .ViewBody viewBody,
        @WebParam(name = "viewFooter", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .ViewFooter viewFooter,
        @WebParam(name = "viewEmpty", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .ViewEmpty viewEmpty,
        @WebParam(name = "rowLimitExceeded", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .RowLimitExceeded rowLimitExceeded,
        @WebParam(name = "query", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .Query query,
        @WebParam(name = "viewFields", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .ViewFields viewFields,
        @WebParam(name = "aggregations", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .Aggregations aggregations,
        @WebParam(name = "formats", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .Formats formats,
        @WebParam(name = "rowLimit", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        com.microsoft.schemas.sharepoint.soap.UpdateViewHtml2 .RowLimit rowLimit,
        @WebParam(name = "openApplicationExtension", targetNamespace = "http://schemas.microsoft.com/sharepoint/soap/")
        String openApplicationExtension);

}
