package com.fst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListEmailServlet
 */
public class ListEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String file_path = "";
	String temp_file_path = "";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			String emailspath = config.getInitParameter("filelocation");
			//String tempemailspath = config.getInitParameter("tempfilelocation");
			
			this.file_path = emailspath;
			//this.temp_file_path = tempemailspath;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// TODO CREATE the subscribe method.
	public void subscribe(String email) throws IOException {
		try {
			Writer output;
			output = new BufferedWriter(new FileWriter(file_path, true));  
			output.append(email+"\n");
			output.close();
			
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		
		
	}
	
	
		
		public void unsubscribe(String email) throws IOException{
			ArrayList<String> emails = new ArrayList<String>();
			File myObj = new File(file_path);
		    Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        emails.add(data);
		      }
		    int pos = emails.indexOf(email);
		    emails.remove(pos);
		    
		    
		    // RESET the file
		    FileWriter myWriter = new FileWriter(file_path);
		    myWriter.write("");
		    myWriter.close();
		    
		    
		    // Replace the File content with the table
		    Writer output;
		    output = new BufferedWriter(new FileWriter(file_path, true));
		    for (int i = 0; i < emails.size(); i++) {
		    	output.append(emails.get(i)+"\n");
		    }
		    
		    output.close();
		    
		    
		    
			
			
		}
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		String email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		
		out.println();
		out.println("<html>");
		out.println("<head><title>EmailManger</title></head>");
		out.println("<body>");
		out.println("<form method=\"post\" action=ListEmailServlet >");
		out.println("<h1>Members: </h1><br><hr>");
		out.println("<ul>");
		File myObj = new File(file_path);
	    Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	    	out.println("<li><p>"+data+"</p></li>");
	      }
	      out.println("</ul>");
	      out.print("<p>Entrer votre addresse email : <input type=text name=email></p>");
	      out.print("<input type=\"submit\"  name=\"action\"value=\"subscribe\">");
	      out.print("<input type=\"submit\" name=\"action\" value=\"unsubscribe\">");
	      out.println("</form></body></html>");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if (action.contentEquals("subscribe")) {
			if (request.getParameter("email")=="") {
				//response.sendRedirect("Erreur.jsp");
				response.sendError(404, "Addresse mail vide!");
			}
			else {
				
				PrintWriter out = response.getWriter();
				try {
					response.setContentType("text/html");
					subscribe(request.getParameter("email"));
					out.println("<html>");
					out.println("<head><title>EmailAjoute</title></head>");
					out.println("<body>");
					out.println("<h4>Addresse "+request.getParameter("email")+" inscrite.</h4>");
					out.println("<hr>");
					out.println("<a href=\"ListEmailServlet\"><h4>Afficher la liste</h4></a>");
					out.println("</form></body></html>");
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		else if (action.contentEquals("unsubscribe")){
			if (request.getParameter("email")=="") {
				response.sendError(404, "Addresse mail vide!");
			}
			else {
				
				PrintWriter out = response.getWriter();
				try {
					response.setContentType("text/html");
					try {
						unsubscribe(request.getParameter("email"));
					} catch (IOException e) {
						response.sendError(404, "Addresse mail introuvable");
						e.printStackTrace();
					}
					out.println("<html>");
					out.println("<head><title>EmailAjoute</title></head>");
					out.println("<body>");
					out.println("<h4>Addresse "+request.getParameter("email")+" supprimeé.</h4>");
					out.println("<hr>");
					out.println("<a href=\"ListEmailServlet\"><h4>Afficher la liste</h4></a>");
					out.println("</form></body></html>");
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
			
	}	

}
