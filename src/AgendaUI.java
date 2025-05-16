import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class AgendaUI extends JFrame {
    private Agenda agenda;
    private JTextField nombreField, apellidoField, telefonoField;
    private JTextArea outputArea;

    public AgendaUI(Agenda agenda) {
        this.agenda = agenda;
        setTitle("Agenda de Contactos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Cambiar el icono de la ventana
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/img_generation.jpg")).getImage());
        // Ingresar datos, informaación
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Espaciado entre campos
        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        inputPanel.add(apellidoField);
        inputPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        inputPanel.add(telefonoField);
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton agregarBtn = new JButton("Agregar");
        JButton buscarBtn = new JButton("Buscar");
        JButton listarBtn = new JButton("Listar");
        JButton eliminarBtn = new JButton("Eliminar");
        JButton modificarBtn = new JButton("Modificar");
        JButton cargarExcelBtn = new JButton("Cargar Excel");
        Dimension btnSize = new Dimension(90, 25);
        agregarBtn.setPreferredSize(btnSize);
        buscarBtn.setPreferredSize(btnSize);
        listarBtn.setPreferredSize(btnSize);
        eliminarBtn.setPreferredSize(btnSize);
        modificarBtn.setPreferredSize(btnSize);
        cargarExcelBtn.setPreferredSize(btnSize);
        buttonPanel.add(agregarBtn);
        buttonPanel.add(buscarBtn);
        buttonPanel.add(listarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(modificarBtn);
        buttonPanel.add(cargarExcelBtn);
        //cambiar color del boton eliminar
        eliminarBtn.setBackground(Color.RED);
        eliminarBtn.setForeground(Color.WHITE);
        eliminarBtn.setFocusPainted(false);

        outputArea = new JTextArea(16, 48); // Más grande
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        // Añadir eventos a los botones con las respectivas funciones
        agregarBtn.addActionListener(e -> {
            agregarContacto();
            limpiarInputs();
        });
        buscarBtn.addActionListener(e -> {
            buscarContacto();
            limpiarInputs();
        });
        listarBtn.addActionListener(e -> {
            listarContactos();
            limpiarInputs();
        });
        eliminarBtn.addActionListener(e -> {
            eliminarContacto();
            limpiarInputs();
        });
        modificarBtn.addActionListener(e -> {
            modificarTelefono();
            limpiarInputs();
        });
        cargarExcelBtn.addActionListener(e -> cargarContactosDesdeExcel());
    }
    // Se crea función para limpiar datos luego de darle clic a los botones
    private void limpiarInputs() {
        nombreField.setText("");
        apellidoField.setText("");
        telefonoField.setText("");
    }

    private void agregarContacto() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String telefono = telefonoField.getText();
        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty()) {
            Contacto contacto = new Contacto(nombre, apellido, telefono);
            String mensaje = agenda.agregarContacto(contacto);
            outputArea.append(mensaje + "\n");
        } else {
            outputArea.append("Por favor, complete todos los campos.\n");
        }
    }
    // se llama funciones de agenta pero primero se validan datos
    private void buscarContacto() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        if (!nombre.isEmpty() && !apellido.isEmpty()) {
            String mensaje = agenda.buscarContactoMensaje(nombre, apellido);
            outputArea.append(mensaje + "\n");
        } else {
            outputArea.append("Ingrese nombre y apellido para buscar.\n");
        }
    }

    private void listarContactos() {
        outputArea.setText(""); // Limpiar antes de mostrar
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Contacto c : agenda.listarContactos()) {
            sb.append(i).append(". ")
                    .append(c.getNombre()).append(" ")
                    .append(c.getApellido()).append(" - ")
                    .append(c.getTelefono()).append("\n");
            i++;
        }
        if (i == 1) {
            sb.append("No hay contactos en la agenda.\n");
        }
        outputArea.append(sb.toString());
    }

    private void eliminarContacto() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        if (!nombre.isEmpty() && !apellido.isEmpty()) {
            String mensaje = agenda.eliminarContacto(nombre, apellido);
            outputArea.append(mensaje + "\n");
        } else {
            outputArea.append("Ingrese nombre y apellido para eliminar.\n");
        }
    }

    private void modificarTelefono() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String telefono = telefonoField.getText();
        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty()) {
            String mensaje = agenda.modificarTelefono(nombre, apellido, telefono);
            outputArea.append(mensaje + "\n");
        } else {
            outputArea.append("Complete todos los campos para modificar.\n");
        }
    }

    private void cargarContactosDesdeExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                List<String> errores = new ArrayList<>();
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = WorkbookFactory.create(fis);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // Saltar encabezado
                    String nombre = getCellStringValue(row.getCell(0));
                    String apellido = getCellStringValue(row.getCell(1));
                    String telefono = getCellStringValue(row.getCell(2));
                    if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty()) {
                        Contacto contacto = new Contacto(nombre, apellido, telefono);
                        String mensaje = agenda.agregarContacto(contacto);
                        if (!mensaje.toLowerCase().contains("exitosamente")) {
                            errores.add(nombre + " " + apellido + ": " + mensaje);
                        }
                    }
                }
                workbook.close();
                fis.close();
                outputArea.append("Contactos cargados desde Excel con éxito.\n");
                listarContactos();
                if (!errores.isEmpty()) {
                    outputArea.append("Errores al cargar:\n");
                    for (String err : errores) {
                        outputArea.append(err + "\n");
                    }
                }
            } catch (Exception ex) {
                outputArea.append("Error al leer el archivo Excel: " + ex.getMessage() + "\n");
            }
        }
    }
    // Método auxiliar para obtener el valor de una celda como String
    private String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long)cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
