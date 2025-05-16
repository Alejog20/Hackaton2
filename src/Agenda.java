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

    public void agregarContacto(Contacto contacto){if (!contactos.add(contacto)) {
        System.out.println("El contacto ya existe.");
    } else {
        System.out.println("contacto agregado exitosamente");
    }

    }

    public Set<Contacto> listarContactos(){
        System.out.println("Listado de contactos:");
        for (Contacto contacto : contactos) {
            System.out.println(contacto.getNombre() + " " + contacto.getApellido() + " - " + contacto.getTelefono());
        }
        return contactos;
    }


    public Contacto buscarContacto(String nombre, String apellido){
        for(Contacto contacto : contactos){
            if(contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido)){
                System.out.println("Telefono: " + contacto.getTelefono());
                return contacto;
            }
        }

        System.out.println("No se encontro el contacto!");
        return null;
    }

    public void eliminarContacto(String nombre, String apellido){
        Contacto encontrado = null;
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido)) {
                encontrado = contacto;
                break;
            }
        }

        if (encontrado != null) {
            contactos.remove(encontrado);
            System.out.println("Contacto eliminado!");
        } else {
            System.out.println("Contacto no existe!");
        }
    }


    public void modificarTelefono(String nombre, String apellido, String telefonoActualizado){
        for(Contacto contacto : contactos){
            if(contacto.getNombre().equalsIgnoreCase(nombre) && contacto.getApellido().equalsIgnoreCase(apellido)){
                contacto.setTelefono(telefonoActualizado);
                System.out.println("El teléfono ha sido actualizado.");
                return;
            }
        }
        System.out.println("No se encontró el contacto!");
    }

}
