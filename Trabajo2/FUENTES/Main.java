
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
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
public class Main {
    
    static int [][] matrizFlujo;
    static int [][] matrizDistancias;
    
    
    
    
    
    public static void main(String[] args) throws IOException{
        
        
        int narchivos=20;
        
            
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        fichero = new FileWriter("/home/antonio/Descargas/data/resultados.txt");
        pw = new PrintWriter(fichero);
        float tiempo;
        ArrayList<Integer> optimos = new ArrayList();
        optimos.add(2298);
        optimos.add(6156);
        optimos.add(17212548);
        optimos.add(168);
        optimos.add(91420);
        optimos.add(12490441);
        optimos.add(3744);
        optimos.add(34458);
        optimos.add(48498);
        optimos.add(66256);
        optimos.add(152002);
        optimos.add(153890);
        optimos.add(147862);
        optimos.add(149576);
        optimos.add(149150);
        optimos.add(637117113);
        optimos.add(458821517);
        optimos.add(7205962);
        optimos.add(44759294);
        optimos.add(8133398);
        
        ArrayList<String> archivos = new ArrayList();
        archivos.add("/home/antonio/Descargas/data/chr20b.dat");
        archivos.add("/home/antonio/Descargas/data/chr22a.dat");
        archivos.add("/home/antonio/Descargas/data/els19.dat");
        archivos.add("/home/antonio/Descargas/data/esc32b.dat");
        archivos.add("/home/antonio/Descargas/data/kra30b.dat");
        archivos.add("/home/antonio/Descargas/data/lipa90b.dat");
        archivos.add("/home/antonio/Descargas/data/nug25.dat");
        archivos.add("/home/antonio/Descargas/data/sko56.dat");
        archivos.add("/home/antonio/Descargas/data/sko64.dat");
        archivos.add("/home/antonio/Descargas/data/sko72.dat");
        archivos.add("/home/antonio/Descargas/data/sko100a.dat");
        archivos.add("/home/antonio/Descargas/data/sko100b.dat");
        archivos.add("/home/antonio/Descargas/data/sko100c.dat");
        archivos.add("/home/antonio/Descargas/data/sko100d.dat");
        archivos.add("/home/antonio/Descargas/data/sko100e.dat");
        archivos.add("/home/antonio/Descargas/data/tai30b.dat");
        archivos.add("/home/antonio/Descargas/data/tai50b.dat");
        archivos.add("/home/antonio/Descargas/data/tai60a.dat");
        archivos.add("/home/antonio/Descargas/data/tai256c.dat");
        archivos.add("/home/antonio/Descargas/data/tho150.dat");
        
        
        
        
        Archivo a;
        int tam;
        float startTime;
        float elapsed;
        float elapsed2;
        float aux;
        int mivalor;
        float tiempo_medio;
        int mi_sol;
        ArrayList<Integer> mejor_solucion;
        ArrayList<Float> tiempos = new ArrayList();
        ArrayList<Float> resultados = new ArrayList();
        ArrayList<Integer> semillas = new ArrayList();
        
        
        for(int i=0;i<5;i++)
            tiempos.add( (float) 0.0);
        
        for(int i=0;i<5;i++)
            resultados.add((float) 0.0);
        
        semillas.add(486235468);
        semillas.add(2162468);
        semillas.add(486363);
        semillas.add(8135461);
        semillas.add(35468);
        semillas.add(22235448);
        semillas.add(87);
        semillas.add(666468);
        semillas.add(48625345);
        semillas.add(25587);
        
        Evaluador e;
        GeneradorAleatorio g;
        float t_final = (float) 0.001;
        float parametro_t_ini = (float) 1.2039;
        int nevaluaciones = 50000;
        float alphaGrasp = (float) 0.5;
        
        if(args.length == 5){
            t_final = Float.parseFloat(args[1]);
            parametro_t_ini = Float.parseFloat(args[2]);
            nevaluaciones = Integer.parseInt(args[3]);
            alphaGrasp = Float.parseFloat(args[4]);
        }
        
        for(int k=0;k<narchivos;k++){
            
            if(args.length > 0){
                k=Integer.parseInt(args[0]);
                narchivos=1;
            }
            
            a = new Archivo(archivos.get(k));
            matrizFlujo = a.getFlujos();
            matrizDistancias = a.getDistancias();
            tam = a.tam;
            e = new Evaluador(matrizFlujo,matrizDistancias);
            pw.println("------------------------------------------------------------------------------------------------------------");
            pw.println("ARCHIVO: " + archivos.get(k));
            
        
            for(int i=0;i<10;i++){
                
                pw.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");

                g = new GeneradorAleatorio(semillas.get(i),2147483647,65539, (float) 0.4656612875e-9);

                startTime = System.nanoTime();
                elapsed = startTime;
                
                mejor_solucion=g.Barajar(tam);
                mi_sol=e.evaluacion(mejor_solucion);
        
                Enfriamiento enfriado = new Enfriamiento(tam,nevaluaciones/(10*tam),10*tam,g,e, (float) ((0.3*mi_sol)/parametro_t_ini), (float) t_final,mejor_solucion,mi_sol);
                enfriado.Inicio();

                
                
                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = enfriado.vSol;
                tiempos.set(0, tiempos.get(0) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(0, (float) resultados.get(0) + 100*aux);

                pw.println("Enfriamiento -->" + mivalor + "tiempo " + tiempo);
                


                elapsed = System.nanoTime();

                BMB bmb = new BMB(tam,g,e, nevaluaciones);
                bmb.Inicio();

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = bmb.vSol;
                tiempos.set(1, tiempos.get(1) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(1, (float) resultados.get(1) + 100*aux);

                pw.println("BMB-->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                ILS ils = new ILS(tam,g,e,nevaluaciones);
                ils.Inicio();

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = ils.vSol;
                tiempos.set(2, tiempos.get(2) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(2, (float) resultados.get(2) + 100*aux);

                pw.println("ILS-->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                ILS_ES ils_es = new ILS_ES(tam, g, e, nevaluaciones/(10*tam),10*tam, (float) ((0.3*mi_sol)/parametro_t_ini), (float) t_final);
                ils_es.Inicio();

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = ils_es.vSol;
                tiempos.set(3, tiempos.get(3) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(3, (float) resultados.get(3) + 100*aux);

                pw.println("ILS_ES-->" + mivalor + "tiempo " + tiempo);
                
                elapsed = System.nanoTime();

                GRASP grasp = new GRASP(tam,g,e, (float) alphaGrasp);
                grasp.Inicio();

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = grasp.vSol;
                tiempos.set(4, tiempos.get(4) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(4, (float) resultados.get(4) + 100*aux);

                pw.println("GRASP-->" + mivalor + "tiempo " + tiempo);

                

            }

            for(int i=0;i<5;i++){
                tiempos.set(i,(float) tiempos.get(i)/10);
                resultados.set(i,(float) resultados.get(i)/10);
            }

            pw.println("Enfriamiento Desviacion: " + resultados.get(0) + " Tiempo " + tiempos.get(0));
            pw.println("BMB Desviacion: " + resultados.get(1) + " Tiempo " + tiempos.get(1));
            pw.println("ILS Desviacion: " + resultados.get(2) + " Tiempo " + tiempos.get(2));
            pw.println("ILS_ES Desviacion: " + resultados.get(3) + " Tiempo " + tiempos.get(3));
            pw.println("GRASP Desviacion: " + resultados.get(4) + " Tiempo " + tiempos.get(4));
            
            
            for(int i=0;i<5;i++)
                tiempos.set(i, (float) 0.0);
        
            for(int i=0;i<5;i++)
                resultados.set(i,(float) 0.0);
        
            pw.println("------------------------------------------------------------------------------------------------------------");    
        
        }
        
        fichero.close();

        
        
    }
    
}
