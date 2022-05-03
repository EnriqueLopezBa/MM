/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.PreguntaDAOImp;
import java.util.ArrayList;
import modelo.Pregunta;

/**
 *
 * @author Enrique
 */
public class ControladorPregunta {

    private static ControladorPregunta instancia;
    private ControladorPregunta() {
    }
    
    public static ControladorPregunta getInstancia(){
        if (instancia == null) {
            instancia = new ControladorPregunta();
        }
        return instancia;
    }
    
      public ArrayList<Pregunta> obtenerListaByCadenaAndIsEncuesta(String cadena, boolean encuesta){
          return PreguntaDAOImp.getInstancia().obtenerListaByCadenaAndIsEncuesta(cadena, encuesta);
      }
    
}
