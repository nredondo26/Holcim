package nredondo26.com.holcim.proyect.Model;

public class Atributos_centrosmedicos {

    String nombre;
    String latitud;
    String longitud;


    public Atributos_centrosmedicos(String nombre, String latitud, String longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
