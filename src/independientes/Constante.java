/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package independientes;

import Componentes.Sweet_Alert.Message;
import Componentes.Sweet_Alert.Message.Tipo;
import javax.swing.JTable;
import vista.principales.Principal;

/**
 *
 * @author Enrique
 */
public class Constante {
    
    public static void mensaje(String texto, Tipo tipo){
        new Message(Principal.getInstancia(), true, texto, tipo).showAlert();
    }
    
    public static boolean filaSeleccionada(JTable tbl){
        if (tbl.getSelectedRow() !=-1) {
            return true;
        }
        mensaje("Seleccionada una fila", Tipo.ERROR);
        return false;
    }
    
}
