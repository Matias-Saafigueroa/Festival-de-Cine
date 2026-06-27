package Persistencia;

import java.io.*;

public class PersistenciaManager {
    public static void guardar(String archivoPath, Object objeto) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoPath))) {
            oos.writeObject(objeto);
        }
    }

    public static Object cargar(String archivoPath) throws IOException, ClassNotFoundException {
        File archivo = new File(archivoPath);
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo " + archivoPath + " no existe todavia");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return ois.readObject();
        }
    }
}
