
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author antonio
 */
public class Archivo {
    
    int [][] matrizFlujo = null;
    int [][] matrizDistancias = null;
    int tam;
    
    public Archivo(String s) throws IOException{
        leerArchivo(s);
    }
    
    public void aniadirNumeros(String cadena, int fila, int [][] matriz){
        int nTokens = 0;
        int col = 0;
        String palabra;
        int numero;
        StringTokenizer st = new StringTokenizer (cadena);
        
        while(st.hasMoreTokens()){
            palabra = st.nextToken();
            nTokens++;
            numero = Integer.parseInt(palabra);
            matriz[fila][col] = numero;
            col++;
        }
        
    }
    
    public void leerArchivo(String archivo) throws FileNotFoundException, IOException{
  
        
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        cadena = b.readLine();
        tam = Integer.parseInt(cadena);
        matrizFlujo = new int [tam][tam];
        matrizDistancias = new int [tam][tam];        
        
        b.readLine(); //Linea en blanco
        
        for(int fila = 0;fila<tam;fila++) {
          cadena = b.readLine();
          aniadirNumeros(cadena,fila,matrizFlujo);
          
        }
        
        b.readLine();
        
        for(int fila = 0;fila<tam;fila++) {
          cadena = b.readLine();
          aniadirNumeros(cadena,fila,matrizDistancias);
          
        }
               
    }
    
    public int[][] getFlujos(){
        return matrizFlujo;
    }
    
    public int[][] getDistancias(){
        return matrizDistancias;
    }
    
}
