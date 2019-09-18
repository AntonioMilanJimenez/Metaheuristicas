
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author antonio
 */
public class Greedy {
    
    
    static int [][] matrizFlujo;
    static int [][] matrizDistancias;
    ArrayList<Integer> potencial_f = new ArrayList();      
    ArrayList<Integer> potencial_d = new ArrayList();
    ArrayList<Integer> solucion = new ArrayList();
    int valor_solucion;
    Evaluador evaluador;
    int tam;
    
    public Greedy(int [][] f, int [][] d, Evaluador e, int t){
        
        matrizFlujo = f;
        matrizDistancias = d;
        evaluador = e;
        tam = t;
        for(int i=0;i<tam;i++)
            solucion.add(-1);
        
        
    }
    
    public ArrayList<Integer> Inicio(){
        
        int n,max,min,pos,pos_min;
        int k=0;
        
        for(int i=0;i<tam;i++){
            n=0;
            for(int j=0;j<tam;j++){
                n=n+matrizFlujo[i][j];
            }
            potencial_f.add(n);
        }
        
        for(int i=0;i<tam;i++){
            n=0;
            for(int j=0;j<tam;j++){
                n=n+matrizDistancias[i][j];
            }
            potencial_d.add(n);
        }

        
        while(k<tam){
            max = Collections.max(potencial_f);
            pos = potencial_f.indexOf(max);
            potencial_f.set(pos,-1);
            
            min = Collections.min(potencial_d);
            pos_min = potencial_d.indexOf(min);
            potencial_d.set(pos_min, 1000000000);
            solucion.set(pos, pos_min+1);
            k++;
        }
        

        
        valor_solucion = evaluador.evaluacion(solucion);
        
        return solucion;
        
    }
    
}
