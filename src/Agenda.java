import java.util.HashSet;
import java.util.Set;

public class Agenda {
    private Set<Contacto> contactos = new HashSet<>();
    private int maximoContactos = 10;

    public Agenda() {
    }

    public Agenda(Set<Contacto> contactos, int maximoContactos) {
        this.contactos = contactos;
        this.maximoContactos = maximoContactos;
    }

    public boolean agendaLlena(){
        return contactos.size() >= maximoContactos;
    }

    public int espacioLibres(){
        return maximoContactos - contactos.size();
    }

    public String agregarContacto(Contacto contacto){
        if (agendaLlena()) {
            return "La agenda está llena. No se puede agregar más contactos.";
        }
        if (!contactos.add(contacto)) {
            return "El contacto ya existe.";
        } else {
            return "Contacto agregado exitosamente.";
        }
    }

    public Set<Contacto> listarContactos(){
        return contactos;
    }

    public String listarContactosMensaje(){
        if (contactos.isEmpty()) return "No hay contactos en la agenda.\n";
        StringBuilder sb = new StringBuilder("Listado de contactos:\n");
        for (Contacto contacto : contactos) {
            sb.append(contacto.getNombre()).append(" ")
                    .append(contacto.getApellido()).append(" - ")
                    .append(contacto.getTelefono()).append("\n");
        }
        return sb.toString();
    }

    public Contacto buscarContacto(String nombre, String apellido){
        for(Contacto contacto : contactos){
            if(contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido)){
                return contacto;
            }
        }
        return null;
    }

    public String buscarContactoMensaje(String nombre, String apellido){
        Contacto c = buscarContacto(nombre, apellido);
        if (c != null) {
            return "Teléfono: " + c.getTelefono();
        } else {
            return "No se encontró el contacto!";
        }
    }

    public String eliminarContacto(String nombre, String apellido){
        Contacto encontrado = null;
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido)) {
                encontrado = contacto;
                break;
            }
        }

        if (encontrado != null) {
            contactos.remove(encontrado);
            return "Contacto eliminado!";
        } else {
            return "Contacto no existe!";
        }
    }

    public String modificarTelefono(String nombre, String apellido, String telefonoActualizado){
        for(Contacto contacto : contactos){
            if(contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido)){
                contacto.setTelefono(telefonoActualizado);
                return "El teléfono ha sido actualizado.";
            }
        }
        return "No se encontró el contacto!";
    }
}
