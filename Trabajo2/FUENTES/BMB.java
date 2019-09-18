
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author antonio
 */
public class BMB {
    
    BusquedaLocal BL;
    ArrayList<Integer> mejorSolucion;
    int vSol;
    GeneradorAleatorio gRandom;
    Evaluador evaluador;
    int tam;
    
    public BMB(int t, GeneradorAleatorio g, Evaluador e, int max){
        
        BL = new BusquedaLocal(t,g,null,e,max,false);
        gRandom = g;
        evaluador=e;
        tam = t;
        
    }
    
    public void Inicio(){
        
        ArrayList<Integer> solucion;
        int valor;
        
        for(int i=0;i<25;i++){
            solucion= gRandom.Barajar(tam);
            valor=evaluador.evaluacion(solucion);
            BL.solucion=solucion;
            BL.Inicio(valor);
            
            if(i==0 || (vSol > BL.getValorSolucion())){
                mejorSolucion = (ArrayList<Integer>) BL.getSolucion().clone();
                vSol = BL.getValorSolucion();
            }
            
        }
        
        
    }
    
    
}
