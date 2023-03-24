package etu2033.framework.servlet;

import etu2033.framework.Mapping;
import etu2033.annotation.url;
import etu2033.framework.Mapping;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;

import java.lang.reflect.*;

public class FrontServlet extends HttpServlet{ 
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            // mappingUrls = new HashMap<String, Mapping>();
            // // String packageName = "etu2033.model";
            // String packageName = getServletContext().getInitParameter("packageName");
            // out.println("<strong>PackageName????: </strong>"+ packageName + "<br>");
            // URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
            // out.println("<strong>Root:</strong> "+root);

            // out.println("<br>");
            /* TODO output your page here. You may use following sample code. */
            out.println("<strong>URL</strong> = " + request.getRequestURI());
            out.println("<br>");
            out.println("<strong>Method</strong> = " + request.getMethod().toString());
            out.println("<br>");
            String nom = request.getQueryString();
            if(!nom.equals("")){
                out.println(nom);
            }
            for(String key : mappingUrls.keySet()){
                Mapping mapping = mappingUrls.get(key);
                out.println("Cle: " + key + ", ClassName: "+ mapping.getClassName() + ", Mapping: " + mapping.getMethod());
                out.println("<br>");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


}