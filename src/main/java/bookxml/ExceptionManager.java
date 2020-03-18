package bookxml;

public class ExceptionManager extends Exception {
	private static final long serialVersionUID = 1L;

	//Constructor por defecto Se asigna mensaje genérico.
	public ExceptionManager() {
		super("Problemea en la conexión con la BD");
	}

	//pTexto Es la descripción de la excepción
	public ExceptionManager(String pTexto) {
		super(pTexto);
	}

}
