
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
public class ILS {
    
    BusquedaLocal BL;
    GeneradorAleatorio gRandom;
    Evaluador evaluador;
    int tam;
    ArrayList<Integer> mejorSolucion;
    int vSol;
    
    
    public ILS(int t, GeneradorAleatorio g, Evaluador e, int max){
        
        BL = new BusquedaLocal(t,g,null,e,max,false);
        gRandom = g;
        evaluador=e;
        tam = t;
        
    }
    
    public void Inicio(){
        
        ArrayList<Integer> solucion = gRandom.Barajar(tam);
        int valor = evaluador.evaluacion(solucion);
        ArrayList<Integer> solucion_aux;
        int vAux;
        
        
        BL.solucion = solucion;
        BL.vSol = valor;
        BL.Inicio(valor);
        
        solucion=BL.getSolucion();
        valor = BL.getValorSolucion();
        mejorSolucion = (ArrayList<Integer>) solucion.clone();
        vSol = valor;
        
        for(int i=0;i<24;i++){
            solucion_aux = (ArrayList<Integer>) solucion.clone();
            solucion_aux = Mutar(solucion_aux);
            
            vAux = evaluador.evaluacion(solucion_aux);
            BL.solucion = solucion_aux;
            BL.vSol = vAux;
            BL.Inicio(vAux);
            
            solucion_aux = BL.getSolucion();
            vAux = BL.getValorSolucion();
            
            if(vAux < valor){
                solucion=(ArrayList<Integer>) solucion_aux.clone();
                valor = vAux;
                if(valor < vSol){
                   mejorSolucion = (ArrayList<Integer>) solucion.clone();
                   vSol = valor; 
                }
            }
        }
        
    }
    
    public ArrayList<Integer> Mutar(ArrayList <Integer> solucion_aux){
        
        ArrayList<Integer> subLista = new ArrayList();
        int ini, fin;
        
        ini = gRandom.randPseudoInt(0, (tam-1)-tam/4);
        fin=ini+tam/4;
        subLista.addAll(solucion_aux.subList(ini, fin+1));
        subLista = gRandom.BarajarLista(subLista, subLista.size());
        
        for(int j=ini, k=0;j<fin+1;j++,k++){
            solucion_aux.set(j, subLista.get(k));
        }
        
        
        return solucion_aux;
    }
    
}
