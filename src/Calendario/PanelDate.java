package Calendario;


import controlador.ControladorEvento;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;
import modelo.Evento;

public class PanelDate extends JPanel {

    private int month;
    private int year;
    private ArrayList<Evento> evento;
    
    public PanelDate() {
        initComponents();
    }

    public void init(int month, int year) {
   
        this.month = month;
        this.year = year;
        lun.asTitle();
        mar.asTitle();
        mie.asTitle();
        jue.asTitle();
        vie.asTitle();
        sab.asTitle();
        dom.asTitle();
        evento = ControladorEvento.getInstancia().obtenerEventoByAnio(year);
      
        setDate();

    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  //  month jan as 0 so start from 0
        calendar.set(Calendar.DATE, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;  //  get day of week -1 to index
        calendar.add(Calendar.DATE, -startDay);
        ToDay toDay = getToDay();
         for (Component com : getComponents()){
              Cell cell = (Cell) com;
              if (!cell.isTitle()){
                  cell.setEvento(null);
                  cell.setAsToDay(false);
              }
         }
        for (Component com : getComponents()) {
            Cell cell = (Cell) com;
            if (!cell.isTitle()) {
                
                for(Evento eve : evento){
                    Calendar d1 = Calendar.getInstance();
                    Calendar d2 = Calendar.getInstance();
                    d1.setTime(eve.getFechaInicio());
                    d2.setTime(calendar.getTime());
                 
                    if (d1.get(Calendar.DATE) == d2.get(Calendar.DATE)) {
                        if (d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)) {
                            if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
                                 cell.setEvento(eve);
                            }
                        }
                    }
                  
                }
               
                cell.setText(calendar.get(Calendar.DATE) + "");
                cell.setDate(calendar.getTime());
                cell.currentMonth(calendar.get(Calendar.MONTH) == month - 1);
                if (toDay.isToDay(new ToDay(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)))) {
                    cell.setAsToDay(true);
                }
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    cell.setForeground(Color.red);
                }
                calendar.add(Calendar.DATE, 1); //  up 1 day
            }
        }
    }

    private ToDay getToDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new ToDay(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dom = new Calendario.Cell();
        lun = new Calendario.Cell();
        mar = new Calendario.Cell();
        mie = new Calendario.Cell();
        jue = new Calendario.Cell();
        vie = new Calendario.Cell();
        sab = new Calendario.Cell();
        cell50 = new Calendario.Cell();
        cell51 = new Calendario.Cell();
        cell52 = new Calendario.Cell();
        cell53 = new Calendario.Cell();
        cell54 = new Calendario.Cell();
        cell55 = new Calendario.Cell();
        cell56 = new Calendario.Cell();
        cell57 = new Calendario.Cell();
        cell58 = new Calendario.Cell();
        cell59 = new Calendario.Cell();
        cell60 = new Calendario.Cell();
        cell61 = new Calendario.Cell();
        cell62 = new Calendario.Cell();
        cell63 = new Calendario.Cell();
        cell64 = new Calendario.Cell();
        cell65 = new Calendario.Cell();
        cell66 = new Calendario.Cell();
        cell67 = new Calendario.Cell();
        cell68 = new Calendario.Cell();
        cell69 = new Calendario.Cell();
        cell70 = new Calendario.Cell();
        cell71 = new Calendario.Cell();
        cell72 = new Calendario.Cell();
        cell73 = new Calendario.Cell();
        cell74 = new Calendario.Cell();
        cell75 = new Calendario.Cell();
        cell76 = new Calendario.Cell();
        cell77 = new Calendario.Cell();
        cell78 = new Calendario.Cell();
        cell79 = new Calendario.Cell();
        cell80 = new Calendario.Cell();
        cell81 = new Calendario.Cell();
        cell82 = new Calendario.Cell();
        cell83 = new Calendario.Cell();
        cell84 = new Calendario.Cell();
        cell85 = new Calendario.Cell();
        cell86 = new Calendario.Cell();
        cell87 = new Calendario.Cell();
        cell88 = new Calendario.Cell();
        cell89 = new Calendario.Cell();
        cell90 = new Calendario.Cell();
        cell91 = new Calendario.Cell();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridLayout(7, 7));

        dom.setText("Dom");
        dom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(dom);

        lun.setText("Lun");
        lun.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(lun);

        mar.setText("Mar");
        mar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(mar);

        mie.setText("Mie");
        mie.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(mie);

        jue.setText("Jue");
        jue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(jue);

        vie.setText("Vie");
        vie.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(vie);

        sab.setText("Sab");
        sab.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(sab);

        cell50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell50);

        cell51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell51);

        cell52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell52);

        cell53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell53);

        cell54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell54);

        cell55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell55);

        cell56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell56);

        cell57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell57);

        cell58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell58);

        cell59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell59);

        cell60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell60);

        cell61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell61);

        cell62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell62);

        cell63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell63);

        cell64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell64);

        cell65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell65);

        cell66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell66);

        cell67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell67);

        cell68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell68);

        cell69.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell69);

        cell70.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell70);

        cell71.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell71);

        cell72.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell72);

        cell73.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell73);

        cell74.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell74);

        cell75.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell75);

        cell76.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell76);

        cell77.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell77);

        cell78.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell78);

        cell79.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell79);

        cell80.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell80);

        cell81.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell81);

        cell82.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell82);

        cell83.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell83);

        cell84.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell84);

        cell85.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell85);

        cell86.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell86);

        cell87.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell87);

        cell88.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell88);

        cell89.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell89);

        cell90.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell90);

        cell91.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add(cell91);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Calendario.Cell cell50;
    private Calendario.Cell cell51;
    private Calendario.Cell cell52;
    private Calendario.Cell cell53;
    private Calendario.Cell cell54;
    private Calendario.Cell cell55;
    private Calendario.Cell cell56;
    private Calendario.Cell cell57;
    private Calendario.Cell cell58;
    private Calendario.Cell cell59;
    private Calendario.Cell cell60;
    private Calendario.Cell cell61;
    private Calendario.Cell cell62;
    private Calendario.Cell cell63;
    private Calendario.Cell cell64;
    private Calendario.Cell cell65;
    private Calendario.Cell cell66;
    private Calendario.Cell cell67;
    private Calendario.Cell cell68;
    private Calendario.Cell cell69;
    private Calendario.Cell cell70;
    private Calendario.Cell cell71;
    private Calendario.Cell cell72;
    private Calendario.Cell cell73;
    private Calendario.Cell cell74;
    private Calendario.Cell cell75;
    private Calendario.Cell cell76;
    private Calendario.Cell cell77;
    private Calendario.Cell cell78;
    private Calendario.Cell cell79;
    private Calendario.Cell cell80;
    private Calendario.Cell cell81;
    private Calendario.Cell cell82;
    private Calendario.Cell cell83;
    private Calendario.Cell cell84;
    private Calendario.Cell cell85;
    private Calendario.Cell cell86;
    private Calendario.Cell cell87;
    private Calendario.Cell cell88;
    private Calendario.Cell cell89;
    private Calendario.Cell cell90;
    private Calendario.Cell cell91;
    private Calendario.Cell dom;
    private Calendario.Cell jue;
    private Calendario.Cell lun;
    private Calendario.Cell mar;
    private Calendario.Cell mie;
    private Calendario.Cell sab;
    private Calendario.Cell vie;
    // End of variables declaration//GEN-END:variables
}
