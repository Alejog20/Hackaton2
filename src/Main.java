public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        AgendaUI ui = new AgendaUI(agenda);
        ui.setVisible(true);
    }
}