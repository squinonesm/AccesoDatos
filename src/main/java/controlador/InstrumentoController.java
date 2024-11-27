package controlador;

import java.util.Scanner;
import modelo.Instrumento;
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
    String nombre;

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
                      7- ESCRIBIR FICHERO ALEATORIO
                      8- LEER FICHERO ALEATORIO
                      9- AÑADIR INSTRUMENTO
                      10- BORRAR INSTRUMENTO
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
                case 5 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.vallezJesuitasComprobador(ir.leerFicheroBinarioDatos(rutaOrigen)));
                    break;
                }
                case 6 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.vallezJesuitasComprobador(ir.leerFicheroBinarioObjetos(rutaOrigen)));
                    break;
                }
                case 7 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.preguntarRuta(PREGUNTAR_RUTA_DESTINO);
                    rutaDestino = sc.nextLine();
                    ir.escribirFicheroAleatorio(rutaOrigen, rutaDestino);
                    break;
                }
                case 8 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.vallezJesuitasComprobador(ir.leerFicheroAleatorio(rutaOrigen)));
                    break;
                }
                case 9 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.comprobador(ir.añadirInstrumento(rutaOrigen, crearInstrumento()));
                    break;
                }
                case 10 -> {
                    v.preguntarRuta(PREGUNTAR_RUTA_ORIGEN);
                    rutaOrigen = sc.nextLine();
                    v.preguntarDatos("Nombre del instrumento a borrar");
                    nombre = sc.nextLine();
                    v.comprobador(ir.borrarInstrumentoFicheroAleatorio(rutaOrigen, nombre));
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

    //He creado un metodo para simplemente crear instrumentos de forma rapida (en caso de necesitarlo mas veces
    //no duplicamos codigo)
    public Instrumento crearInstrumento() {
        v.preguntarDatos("Introduzca el nombre del instrumento");
        nombre = sc.nextLine();
        v.preguntarDatos("Introduzca el tipo del instrumento");
        String tipo = sc.nextLine();
        v.preguntarDatos("Introduzca el origen del instrumento");
        String origen = sc.nextLine();
        v.preguntarDatos("Introduzca el material del instrumento");
        String material = sc.nextLine();
        v.preguntarDatos("Introduzca el precio del instrumento");
        int precio = sc.nextInt();
        Instrumento i = new Instrumento(nombre, tipo, origen, material, precio);
        return i;
    }

}
