
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.util.ArrayList;
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
        
        float probCruceGen = (float) 0.7;
        float probCruceEst = 1;
        float probMutar = (float) 0.001;
        int tamPobMemetico = 10;
        int tamPobGenetico = 50;
        
        if(args.length > 0){
            probCruceGen = Float.parseFloat(args[0]);
            probCruceEst = Float.parseFloat(args[1]);
            probMutar = Float.parseFloat(args[2]);
            tamPobMemetico = Integer.parseInt(args[3]);
            tamPobGenetico = Integer.parseInt(args[4]);
        }
        
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
        ArrayList<Float> tiempos = new ArrayList();
        ArrayList<Float> resultados = new ArrayList();
        ArrayList<Integer> semillas = new ArrayList();
        
        
        for(int i=0;i<9;i++)
            tiempos.add( (float) 0.0);
        
        for(int i=0;i<9;i++)
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
        
        for(int k=0;k<narchivos;k++){
            
            if(args.length == 6){
                k=Integer.parseInt(args[5]);
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

                Greedy voraz = new Greedy(matrizFlujo, matrizDistancias,e,tam);
                ArrayList<Integer> solucion_greedy = voraz.Inicio();

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = e.evaluacion(solucion_greedy);
                tiempos.set(0, tiempos.get(0) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(0, (float) resultados.get(0) + 100*aux);

                pw.println("Greedy -->" + mivalor + "tiempo " + tiempo);

                ArrayList<Integer> inicio = g.Barajar(tam);

                elapsed = System.nanoTime();

                BusquedaLocal local = new BusquedaLocal(tam,g,inicio,e, 50000, true);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = local.vSol;
                tiempos.set(1, tiempos.get(1) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(1, (float) resultados.get(1) + 100*aux);

                pw.println("Busqueda local-->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                Genetico genAlg = new Genetico(tam,tamPobGenetico,g,e,1,1,probCruceGen,probMutar,50000, true);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = genAlg.vSol;
                tiempos.set(2, tiempos.get(2) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(2, (float) resultados.get(2) + 100*aux);

                pw.println("Genetico Generacional posicion-->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                genAlg = new Genetico(tam,tamPobGenetico,g,e,1,2,probCruceGen,probMutar,50000, true);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = genAlg.vSol;
                tiempos.set(3, tiempos.get(3) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(3, (float) resultados.get(3) + 100*aux);

                pw.println("Genetico Generacional OX-->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                genAlg = new Genetico(tam,tamPobGenetico,g,e,2,1,probCruceEst,probMutar,50000, true);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = genAlg.vSol;
                tiempos.set(4, tiempos.get(4) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(4, (float) resultados.get(4) + 100*aux);

                pw.println("Genetico Estacionario posicion-->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                genAlg = new Genetico(tam,tamPobGenetico,g,e,2,2,probCruceEst,probMutar,50000, true);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = genAlg.vSol;
                tiempos.set(5, tiempos.get(5) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(5, (float) resultados.get(5) + 100*aux);

                pw.println("Genetico Estacionario OX-->" + mivalor + "tiempo " + tiempo);


                elapsed = System.nanoTime();

                Memetico mem = new Memetico(tam,tamPobMemetico, g, e,1, probCruceGen,probMutar, 1);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = mem.valS;
                tiempos.set(6, tiempos.get(6) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(6, (float) resultados.get(6) + 100*aux);

                pw.println("Memetico toda la poblacion-->" + mivalor + "tiempo " + tiempo); 

                elapsed = System.nanoTime();

                mem = new Memetico(tam,tamPobMemetico, g, e,1, probCruceGen,probMutar, 2);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = mem.valS;
                tiempos.set(7, tiempos.get(7) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(7, (float) resultados.get(7) + 100*aux);

                pw.println("Memetico aleatorio -->" + mivalor + "tiempo " + tiempo);

                elapsed = System.nanoTime();

                mem = new Memetico(tam,tamPobMemetico, g, e,1, probCruceGen,probMutar, 3);

                elapsed2 = System.nanoTime() - elapsed;
                tiempo = (float) elapsed2/1000000000;
                mivalor = mem.valS;
                tiempos.set(8, tiempos.get(8) + tiempo);
                aux = (float)(mivalor-optimos.get(k))/optimos.get(k);
                resultados.set(8, (float) resultados.get(8) + 100*aux);

                pw.println("Memetico los mejores -->" + mivalor + "tiempo " + tiempo);

            }

            for(int i=0;i<9;i++){
                tiempos.set(i,(float) tiempos.get(i)/10);
                resultados.set(i,(float) resultados.get(i)/10);
            }

            pw.println("Greedy Desviacion: " + resultados.get(0) + " Tiempo " + tiempos.get(0));
            pw.println("Busqueda local Desviacion: " + resultados.get(1) + " Tiempo " + tiempos.get(1));
            pw.println("Genetico GenPos Desviacion: " + resultados.get(2) + " Tiempo " + tiempos.get(2));
            pw.println("Genetico GenOX Desviacion: " + resultados.get(3) + " Tiempo " + tiempos.get(3));
            pw.println("Genetico EstPos Desviacion: " + resultados.get(4) + " Tiempo " + tiempos.get(4));
            pw.println("Genetico EstOX Desviacion: " + resultados.get(5) + " Tiempo " + tiempos.get(5));
            pw.println("Memetico todos Desviacion: " + resultados.get(6) + " Tiempo " + tiempos.get(6));
            pw.println("Memetico aleatorio Desviacion: " + resultados.get(7) + " Tiempo " + tiempos.get(7));
            pw.println("Memetico mejores Desviacion: " + resultados.get(8) + " Tiempo " + tiempos.get(8));
            
            for(int i=0;i<9;i++)
            tiempos.set(i, (float) 0.0);
        
            for(int i=0;i<9;i++)
                resultados.set(i,(float) 0.0);
        
            pw.println("------------------------------------------------------------------------------------------------------------");    
        
        }
        
        fichero.close();
        
        
        
        
        
        
        
        
        
    }
    
}
