package bookxml;

public class ExceptionManager extends Exception {
	private static final long serialVersionUID = 1L;

	//Constructor por defecto Se asigna mensaje gen�rico.
	public ExceptionManager() {
		super("Problemea en la conexi�n con la BD");
	}

	//pTexto Es la descripci�n de la excepci�n
	public ExceptionManager(String pTexto) {
		super(pTexto);
	}

}
