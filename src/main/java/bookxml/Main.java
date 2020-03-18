package bookxml;

import java.io.File;

import org.xmldb.api.base.Collection;

public class Main {

	public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

	public static void main(String args[]) throws Exception {

		Collection col;
		BookXML libros = new BookXML();
		File archivo = new File("C:\\Temp\\Libros_Favoritos.xml");

		col=libros.conectarBD();
		libros.InsertarBD();
		libros.asignarRecursoBD(col, archivo);

	}

}
