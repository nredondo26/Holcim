package nredondo26.com.holcim.proyect.Model;

public class Contact {

    private String Nombre;
    private String Telefono;

    public Contact() {
    }

    public Contact(String nombre, String telefono) {
        Nombre = nombre;
        Telefono = telefono;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
