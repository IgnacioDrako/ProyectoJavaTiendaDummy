package principal;

public class Usuarios {
	protected String Nombre;
	protected String IDUsuario;
	protected String Password;
	protected String Email;
	protected double Saldo;
	public Usuarios( String nombre, String IDUsuario, String Password, String Email,double Saldo ) {
		this.Nombre = nombre;
		this.IDUsuario = IDUsuario;
		this.Password = Password;
		this.Email = Email;
		this.Saldo = Saldo;
	}
	public String getNombre() {
        return Nombre;
    }
	public void setNombre(String nombre) {
        Nombre = nombre;
    }
	public String getIDUsuario() {
        return IDUsuario;
    }
	public void setIDUsuario(String IDUsuario) {
        this.IDUsuario = IDUsuario;
    }
	public String getPassword() {
        return Password;
    }
	public void setPassword(String password) {
        Password = password;
    }
	public String getEmail() {
        return Email;
    }
	public void setEmail(String email) {
        Email = email;
    }
	public double getSaldo() {
        return Saldo;
    }
	public void setSaldo(double saldo) {
        Saldo = saldo;
    }
	@Override
    public String toString() {
        return "Usuarios [Nombre=" + Nombre + ", \nIDUsuario=" + IDUsuario + ", \nPassword=" + Password + ", \nEmail=" + Email + ", \n Saldo=" + Saldo+"]";
    }
}
