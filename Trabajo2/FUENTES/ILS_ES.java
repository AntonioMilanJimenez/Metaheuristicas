
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
public class ILS_ES {
    
    Enfriamiento ES;
    GeneradorAleatorio gRandom;
    Evaluador evaluador;
    int tam;
    ArrayList<Integer> mejorSolucion;
    int vSol;
    float temp_ini;
    
    
    public ILS_ES(int t, GeneradorAleatorio g, Evaluador e, int max, int m_vecinos, float t_ini, float tf){
        
        ES = new Enfriamiento(t, max, m_vecinos, g, e, t_ini, tf, null, 0);
        gRandom = g;
        evaluador=e;
        tam = t;
        temp_ini = t_ini;
        
    }
    
    public void Inicio(){
        
        ArrayList<Integer> solucion = gRandom.Barajar(tam);
        int valor = evaluador.evaluacion(solucion);
        ArrayList<Integer> solucion_aux;
        int vAux;
        
        
        ES.mejor_solucion = solucion;
        ES.vSol = valor;
        ES.Inicio();
        
        solucion=ES.getSolucion();
        valor = ES.getValorSolucion();
        mejorSolucion = (ArrayList<Integer>) solucion.clone();
        vSol = valor;
        
        for(int i=0;i<24;i++){
            solucion_aux = (ArrayList<Integer>) solucion.clone();
            solucion_aux = Mutar(solucion_aux);
            
            vAux = evaluador.evaluacion(solucion_aux);
            ES.mejor_solucion = solucion_aux;
            ES.vSol = vAux;
            ES.temperatura=temp_ini;
            ES.Inicio();
            
            solucion_aux = ES.getSolucion();
            vAux = ES.getValorSolucion();
            
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
