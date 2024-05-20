package principal;

public class Vendedor extends Usuarios{
	Productos Producto;

	public Vendedor(String nombre, String IDUsuario, String Password, String Email, double Saldo, Productos productoComprador) {
		super(nombre, IDUsuario, Password, Email, Saldo);
		this.Producto = productoComprador;

	}
	public Productos getProducto() {
        return Producto;
    }
	public void setProducto(Productos Producto) {
        this.Producto = Producto;
    }
	@Override
    public String toString() {
        return "\nVendedor:\n==================================== \nNombre=" + Nombre + "\nIDUsuario=" + IDUsuario + "\nPassword=" + Password + "\nEmail=" + Email + "\nSaldo=" + Saldo+"\nProducto= " + Producto+"\n====================================" ;
    }
	
}
