/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ies.comercio.examenaccesodatos;

import controlador.InstrumentoController;
import modelo.InstrumentoRepository;
import vista.Vista;

/**
 *
 * @author Sergio
 */
public class ExamenAccesoDatos {

    public static void main(String[] args) {
        Vista v = new Vista();
        InstrumentoRepository ir = new InstrumentoRepository();
        
        InstrumentoController ic = new InstrumentoController(v, ir);
    }
}
