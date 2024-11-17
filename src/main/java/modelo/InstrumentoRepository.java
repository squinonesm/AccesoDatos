package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                bw.write(listaInstrumento.getNombre() + ", ");
                bw.write(listaInstrumento.getTipo() + ", ");
                bw.write(listaInstrumento.getOrigen() + ", ");
                bw.write(listaInstrumento.getMaterial() + ", ");
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

    //Escribimos un fichero binario leyendo el txt, almacenalorlo en un array
    //Recorreremos ese array e iremos escribiendo los datos del objeto
    public void escribirFicheroBinarioDatos(String rutaOrigen, String rutaDestino) {

        ArrayList<Instrumento> listaInstrumentos = leerFicheroTxt(rutaOrigen);

        File f;
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        f = new File(rutaDestino);

        try {
            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);

            //Simplemente recorremos el array y vamos escribiendo cada uno de los datos
            //nombre,tipo,origen,material,precio
            for (Instrumento listaInstrumento : listaInstrumentos) {
                dos.writeUTF(listaInstrumento.getNombre());
                dos.writeUTF(listaInstrumento.getTipo());
                dos.writeUTF(listaInstrumento.getOrigen());
                dos.writeUTF(listaInstrumento.getMaterial());
                dos.writeInt(listaInstrumento.getPrecio());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }

                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //En este caso vamos a escribir un fichero binario, pero utilizando objetos, debemos tener en cuenta
    //la cabecera que se genera al escribir objetos binarios
    public void escribirFicheroBinarioObjetos(String rutaOrigen, String rutaDestino) {

        ArrayList<Instrumento> listaInstrumentos = leerFicheroTxt(rutaOrigen);

        File f;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        f = new File(rutaDestino);

        try {
            //En caso de tener contenido, no escribiremos la cabecera para evitar problemas de lectura
            if (f.length() > 0) {
                fos = new FileOutputStream(f, true);
                oos = new MiObjectOutputStream(fos);
            } else {
                fos = new FileOutputStream(f);
                oos = new ObjectOutputStream(fos);
            }

            //Recorremos el array y escribimos los objetos
            for (Instrumento listaInstrumento : listaInstrumentos) {
                oos.writeObject(listaInstrumento);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Leeremos el fichero binario dato a dato y los almacenaremos en objetos
    public ArrayList<Instrumento> leerFicheroBinarioDatos(String rutaOrigen) {

        ArrayList<Instrumento> listaInstrumentos = new ArrayList<>();

        File f;
        FileInputStream fis = null;
        DataInputStream dis = null;

        f = new File(rutaOrigen);

        try {
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);

            //Recorremos todo el fichero y vamos cogiendo dato a dato para crear un objeto instrumento
            while (true) {
                String nombre = dis.readUTF();
                String tipo = dis.readUTF();
                String origen = dis.readUTF();
                String material = dis.readUTF();
                int precio = dis.readInt();

                Instrumento i = new Instrumento(nombre, tipo, origen, material, precio);
                listaInstrumentos.add(i);
            }

        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaInstrumentos;
    }

    //Leeremos el fichero binario y lo almacenaremos directamente en objetos
    public ArrayList<Instrumento> leerFicheroBinarioObjetos(String rutaOrigen) {

        ArrayList<Instrumento> listaInstrumentos = new ArrayList<>();

        File f;
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        f = new File(rutaOrigen);

        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            //Recorremos todo el fichero y capturamos el error
            while (true) {
                //Vamos leyendo los objetos y los añadimos al array
                Instrumento i = (Instrumento) ois.readObject();
                listaInstrumentos.add(i);
            }

        } catch (EOFException e) {
            System.out.println("Fin del fichero");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InstrumentoRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listaInstrumentos;
    }

    //Generaremos un comprobador que nos servira para saber si hemos leido bien los archivos.
    public String vallezJesuitasComprobador(ArrayList<Instrumento> listaInstrumentos) {
        String aux = "";
        for (Instrumento listaInstrumento : listaInstrumentos) {
            aux += listaInstrumento.toString();
        }
        return aux;
    }
}
