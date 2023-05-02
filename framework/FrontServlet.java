package etu2033.framework.servlet;

import etu2033.framework.Mapping;
import etu2033.fonctions.Fonction;
import etu2033.annotation.url;
import etu2033.framework.Mapping;
import etu2033.process.ModelView;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;

import java.lang.reflect.*;

public class FrontServlet extends HttpServlet{ 
    HashMap<String,Mapping> mappingUrls;

    public void init() throws ServletException {
        try {
        mappingUrls = new HashMap<String, Mapping>();
        // String packageName = "etu2033.model";
        String packageName = getServletContext().getInitParameter("packageName");
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
            for (File file : new File(root.getFile().replaceAll("%20", " ")).listFiles()) {
                if (file.getName().contains(".class")) {
                    String className = file.getName().replaceAll(".class$", "");
                    Class<?> cls = Class.forName(packageName + "." + className);
                    for (Method method : cls.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(url.class)) {
                            mappingUrls.put(method.getAnnotation(url.class).value(), new Mapping(cls.getName(), method.getName()));
                        }
                    }
                }
            }
            } catch (Exception e) {
                throw new ServletException(e);
            }
    } 

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        

            String packageName = getServletContext().getInitParameter("packageName");
            URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "//")); 
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<strong>MappingUrls(size): </strong> = " + mappingUrls.size() +" ,<br>");
            out.println("<strong>Root</strong> = " + root +",<br>");

            String stringUri = request.getRequestURI();
            String[] arrayPath = stringUri.split("/");
            String cle = arrayPath[arrayPath.length - 1];
            
            out.println("<strong>URL</strong> = " + stringUri);
            out.println("<br>");
            out.println("<strong>Method</strong> = " + request.getMethod().toString());
            out.println("<br>");

            out.println("<strong>Cle: </strong>"+cle+"<br>");
            for(String key : mappingUrls.keySet()){
                Mapping mapping = mappingUrls.get(key);
                out.println("<strong>Cle:</strong> " + key + ", <strong>ClassName:</strong> "+ mapping.getClassName() + ", <strong>Mapping:</strong> " + mapping.getMethod());
                out.println("<br>");
            }
            

            String nomMethode = cle;
            try {
            
            String nomDeClasse = (String) mappingUrls.get(nomMethode).getClassName();
            java.lang.Class cl = java.lang.Class.forName(nomDeClasse);
            Object object = cl.newInstance();
            String method = (String) mappingUrls.get(nomMethode).getMethod();
            Method methode = object.getClass().getDeclaredMethod(method);

            Object retour = new Object();

            ///Maka donnees avy am formulaire
            Enumeration<String> paramNames = request.getParameterNames();
            if (paramNames.hasMoreElements()) {
                HashMap<String,String> formulaireDonnees = new HashMap<>();
                while (paramNames.hasMoreElements()) {
                    String name = paramNames.nextElement();
                    String[] paramValues = request.getParameterValues(name);

                    for(String paramValue : paramValues){
                        formulaireDonnees.put(name,paramValue);
                    }

                }
                object = Fonction.buildObject(cl, formulaireDonnees);
                
                
            }
                retour = (ModelView) methode.invoke(object);
            

            
    ///Model view
            ModelView mv_retour = (ModelView) retour;
            out.println(mv_retour.getView());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"+mv_retour.getView());
            out.println("<br><strong>Page: </strong>"+mv_retour.getView());

    ///HashMap
            HashMap<String,Object> data = mv_retour.getData();
            for(Map.Entry<String, Object> entry: data.entrySet()){
                request.setAttribute(entry.getKey(), entry.getValue());
            }

            

            requestDispatcher.forward(request,response);
            } catch (Exception e) {
                //TODO: handle exception
                out.println("<br>Error message: "+e.getMessage());
                e.printStackTrace(out);
                
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