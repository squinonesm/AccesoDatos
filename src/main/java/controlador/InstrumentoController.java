package controlador;

import java.util.Scanner;
import modelo.InstrumentoRepository;
import vista.Vista;

/**
 *
 * @author Sergio
 */
public class InstrumentoController {

    public static Scanner sc = new Scanner(System.in);

    final String ERROR = "ERRORAZO";
    final String PREGUNTAR_RUTA_ORIGEN = "¿Cual es la ruta de origen?";
    final String PREGUNTAR_RUTA_DESTINO = "¿Cual es la ruta de destino?";

    boolean continuar = true;
    String rutaOrigen;
    String rutaDestino;

    Vista v;
    InstrumentoRepository ir;

    public InstrumentoController(Vista v, InstrumentoRepository ir) {
        this.v = v;
        this.ir = ir;
        procesarMenu();
    }

    public int menu() {
        String menu = """
                      1- LEER FICHERO TXT
                      2- ESCRIBIR FICHERO TXT
                      3- ESCRIBIR FICHERO BINARIO (DATOS)
                      4- ESCRIBIR FICHERO BINARIO (OBJETOS)
                      5- LEER FICHERO BINARIO (DATOS)
                      6- LEER FICHERO BINARIO (OBJETOS)
                      28- SALIR""";
        v.mostrarMenu(menu);
        int opcion = sc.nextInt();
        sc.nextLine();
        return opcion;
    }

    public void procesarMenu() {
        while (continuar) {
            switch (menu()) {
                case 1 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.vallezJesuitasComprobador(ir.leerFicheroTxt(rutaOrigen)));
                    break;
                }
                case 2 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.preguntarRuta(PREGUNTAR_RUTA_DESTINO);
                    rutaDestino = sc.nextLine();
                    ir.escribirFicheroTxt(rutaOrigen, rutaDestino);
                    break;
                }
                case 3 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.preguntarRuta(PREGUNTAR_RUTA_DESTINO);
                    rutaDestino = sc.nextLine();
                    ir.escribirFicheroBinarioDatos(rutaOrigen, rutaDestino);
                    break;
                }
                case 4 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.preguntarRuta(PREGUNTAR_RUTA_DESTINO);
                    rutaDestino = sc.nextLine();
                    ir.escribirFicheroBinarioObjetos(rutaOrigen, rutaDestino);
                    break;
                }
                case 5 ->{
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.vallezJesuitasComprobador(ir.leerFicheroBinarioDatos(rutaOrigen)));
                }
                case 6 ->{
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.vallezJesuitasComprobador(ir.leerFicheroBinarioObjetos(rutaOrigen)));
                }      
                case 28 -> {
                    continuar = false;
                    break;
                }
                default -> {
                    v.mostrarError(ERROR);
                }
            }
        }
    }
}
