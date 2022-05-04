/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.QuizDAOImp;
import independientes.Mensaje;
import java.util.ArrayList;
import modelo.Quiz;

/**
 *
 * @author Enrique
 */
public class ControladorQuiz {

    private static ControladorQuiz instancia;

    private ControladorQuiz() {
    }

    public static ControladorQuiz getInstancia() {
        if (instancia == null) {
            instancia = new ControladorQuiz();
        }
        return instancia;
    }

    public Quiz obtenerByIdPreguntaAndIdEvento(int idPregunta, int idEvento) {
        return QuizDAOImp.getInstancia().obtenerByIdPreguntaAndIdEvento(idPregunta, idEvento);
    }

    public Mensaje registrar(Quiz t) {
        return QuizDAOImp.getInstancia().registrar(t);
    }

    public ArrayList<Quiz> obtenerListaByIdCliente(int idCliente) {
        return QuizDAOImp.getInstancia().obtenerListaByIdCliente(idCliente);
    }
}
