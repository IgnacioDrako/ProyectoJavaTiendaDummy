package principal;

public class Ventas {
	Vendedor Vendedores;
	Comprador Compradores;
	Productos Producto;
	public Ventas (Vendedor Vendedores, Productos Producto, Comprador Compradores) {
		super();
		this.Vendedores = Vendedores;
		this.Producto = Producto;
		this.Compradores = Compradores;
	}
	public Vendedor getVendedores() {
        return Vendedores;
    }
	public void setVendedores(Vendedor Vendedores) {
        this.Vendedores = Vendedores;
    }
	public Comprador getCompradores() {
		return Compradores;
	}
	public void setCompradores(Comprador Compradores) {
        this.Compradores = Compradores;
    }
	public Productos getProducto() {
        return Producto;
    }
	public Productos setProductos(Productos Producto) {
		this.Producto = Producto;
        return Producto;
	}
    public String toString() {
        return "\nVenta:\n====================================\nVendedor: "+Vendedores+"\nComprador: "+Compradores+"\nProductos: "+Producto+"\n====================================";
    }
}
