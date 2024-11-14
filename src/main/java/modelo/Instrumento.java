package modelo;

import java.io.Serializable;

/**
 *
 * @author Sergio
 */
public class Instrumento implements Serializable{

    private String nombre, tipo, origen, material;
    private int precio;

    public Instrumento(String nombre, String tipo, String origen, String material, int precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.origen = origen;
        this.material = material;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Instrumento { Nombre: " + nombre + ", Tipo: " + tipo + ", Origen: " + origen + ", Material: " + material + ", Precio: " + precio + " }";
    }

}
