package principal;

public class Comprador extends Usuarios{

	public Comprador(String nombre, String IDUsuario, String Password, String Email, double Saldo) {
		super(nombre, IDUsuario, Password, Email, Saldo);
	}
	@Override
    public String toString() {
        return "\nUsuario Comun:\n====================================\nNombre=" + Nombre + "\nIDUsuario=" + IDUsuario + "\nPassword=" + Password + "\nEmail=" + Email + "\nSaldo=" + Saldo+"\n====================================";
    }
}
