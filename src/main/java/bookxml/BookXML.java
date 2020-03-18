package bookxml;

import java.io.File;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

public class BookXML {
	protected static String driver = "org.exist.xmldb.DatabaseImpl";
	public static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
	private Database database;
	private String usuario;
	private String usuarioPwd;
	private String collection;
	private String nombre_recurso;
	private Collection col;


	public BookXML() throws ExceptionManager {
		this.database = null;
		this.usuario = "admin";
		this.usuarioPwd = "1234";
		collection = "/db/test";
		nombre_recurso = "Libros_Ingles.xml";
	}


	//Método que conecta a una colección de la base de datos de eXist y devuelve un recurso (XML) guardado
	public Collection conectarBD() throws ExceptionManager {
		try {
			System.out.println("Intentando conectar....");
			Class cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);
			System.out.print("Ahora obtiene la colección " + URI + collection);
			col=DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
			if (col.getResourceCount()==0) {
				// si la collección no tiene recursos no podrá devolver ninguno
				System.out.println("La colección no tiene recursos...No puede devolver ninguno [FIN]");
				return null;
			}else {
				System.out.println("...La colección no es nula...");
				Resource res = null;
				res = (Resource) col.getResource(nombre_recurso);
				System.out.println("De la colección saca el recurso que tiene la variable nombre_recurso:" + nombre_recurso);
				XMLResource xmlres = (XMLResource) res;
				System.out.println("La salida es: \n" + xmlres.getContent());
				System.out.println("El tipo es:" + xmlres.getResourceType());
				return col;
			}

		} catch (ClassNotFoundException e) {
			throw new ExceptionManager("No se encuentra la clase del driver");
		} catch (InstantiationException e) {
			throw new ExceptionManager("Error instanciando el driver");
		} catch (IllegalAccessException e) {
			throw new ExceptionManager("Se ha producido una IllegalAccessException");
		} catch (XMLDBException e) {
			throw new ExceptionManager("error XMLDB :" + e.getMessage());
		}
	}

	public void asignarRecursoBD(Collection contexto, File archivo) throws ExceptionManager{
		try {
			Resource nuevoRecurso = contexto.createResource(archivo.getName(), "XMLResource");
			nuevoRecurso.setContent(archivo);
			contexto.storeResource(nuevoRecurso);

		} catch (XMLDBException e) {
			throw new ExceptionManager("error XMLDB :" + e.getMessage());
		}		
	}
	public void InsertarBD() throws ExceptionManager{

		Collection col;
		ResourceSet result = null;
		String consulta = "update insert <segundo_autor>Marc Stroll </segundo_autor> into /Libros/Libro";
		System.out.print("Intento Conectar...");
		// Se supone que el database ya ha sido abierto (con conectarBD)
		// DatabaseManager.registerDatabase(database);
		try {
			col = DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
			System.out.println("Conectado!");
			XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
			service.setProperty(OutputKeys.INDENT, "yes");
			service.setProperty(OutputKeys.ENCODING, "UTF-8");
			CompiledExpression compiled = service.compile(consulta);
			result = service.execute(compiled);
			System.out.println("Insertado!");
			// podria ser: result = service.query( consulta );
		} catch (XMLDBException e) {
			throw new ExceptionManager("Error ejecutando query: " + e.getMessage());
		}
	}



}
