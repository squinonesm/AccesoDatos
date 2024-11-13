package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
public class InstrumentoRepository {

    //Leemos un txt pasandole la ruta, devolvemos un array de instrumentos,
    public ArrayList<Instrumento> leerFicheroTxt(String rutaOrigen) {

        //Array en el que almacenaremos los instrumentos que leamos
        ArrayList<Instrumento> listaInstrumentos = new ArrayList<>();

        File f;
        FileReader fr = null;
        BufferedReader br = null;
        String linea;

        try {
            f = new File(rutaOrigen);
            fr = new FileReader(f);
            //Creamos un buffer reader porque vamos a leer lineas (omitire el de caracteres me parece muy sencillito)
            br = new BufferedReader(fr);
            //Nos saltamos la primera linea, suele ser la cabecera
            br.readLine();

            //Un bucle para recorrer todo el archivo linea a linea
            while ((linea = br.readLine()) != null) {
                //Dividimos la linea leida, guardando en cada posicion lo que este antes de la ,
                //Con eso podremos crear el objeto instrumento para luego almacenarlo en nuestro array
                String[] datos = linea.split(", ");

                //Convertimos los String en el tipo de variable del constructor de nuestro instrumento
                Instrumento i = new Instrumento(datos[0], datos[1], datos[2], datos[3], Integer.parseInt(datos[4]));
                //Añadimos el instrumento a nuestro array
                listaInstrumentos.add(i);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            //Importante recordar cerrarlos.
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Devolveremos el array de instrumentos
        return listaInstrumentos;
    }

    //Vamos a escribir un fichero txt pasandole una ruta de origen (para obtener el array),
    //y una de destino para decirle donde escribir
    public void escribirFicheroTxt(String rutaOrigen, String rutaDestino) {
        //Con el metodo de leer obtenemos el array
        ArrayList<Instrumento> listaInstrumentos = leerFicheroTxt(rutaOrigen);

        File f;
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            f = new File(rutaDestino);
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);

            //Escribiremos la primera linea (suele ser la cabecera)
            bw.write("Nombre, Tipo, Origen, Material, Precio");
            //Saltamos a la siguiente linea, como una maquina de escribir
            bw.newLine();

            //Recorremos el array y reescribimos con el mismo formato que nos dio la profesora.
            for (Instrumento listaInstrumento : listaInstrumentos) {
                bw.write(listaInstrumento.getNombre()+", ");
                bw.write(listaInstrumento.getTipo()+", ");
                bw.write(listaInstrumento.getOrigen()+", ");
                bw.write(listaInstrumento.getMaterial()+", ");
                bw.write(Integer.toString(listaInstrumento.getPrecio()));
                bw.newLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
      
    //Generaremos un comprobador que nos servira para saber si hemos leido bien los archivos.
    public void vallezJesuitasComprobador(ArrayList<Instrumento> listaInstrumentos){      
        for (Instrumento listaInstrumento : listaInstrumentos) {
            System.out.println(listaInstrumento.toString());
        }     
    }
}
