public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;

    public Contacto(String nombre, String apellido, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }

        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.telefono = telefono;
    }

    public void mostrarInfo() {
        System.out.println(this.toString());
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String nuevoTelefono) {
        this.telefono = nuevoTelefono;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;
        Contacto otro = (Contacto) obj;

        return this.nombre.equalsIgnoreCase(otro.nombre) &&
                this.apellido.equalsIgnoreCase(otro.apellido);
    }

    @Override
    public int hashCode() {
        return (nombre.toLowerCase() + apellido.toLowerCase()).hashCode();
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - Tel: " + telefono;
    }
}
