
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
public class BusquedaLocal {
 
    ArrayList<Integer> bits = new ArrayList();
    int tam;
    GeneradorAleatorio gRandom;
    ArrayList<Integer> solucion;
    Evaluador evaluador;
    int max_iter;
    int evaluaciones;
    int vSol;
    
    public BusquedaLocal(int t, GeneradorAleatorio g, ArrayList<Integer> o, Evaluador e, int max, boolean inicio){
        tam = t;
        for(int i =0;i<tam;i++){
            bits.add(0);
        }
        
        gRandom = g;
        solucion = o;
        evaluador = e;
        max_iter = max;
        
        
        if(inicio){
            int v = evaluador.evaluacion(solucion);
            Inicio(v);
        }
        
        
        
    }
    
    public int Inicio(int valor_orig){
        
        ArrayList<Integer> aux = gRandom.Barajar(tam);
        int valor;
        int pos;
        boolean encontrado;
        int t_evals = 0;
        
        
        for(int i =0;i<tam && t_evals<max_iter;i++){
            pos = aux.get(i)-1;
            if(bits.get(pos) ==0){
                encontrado=false;
                for(int j=0;j<tam && !encontrado;j++){
                    if(j!=pos){
                        valor = evaluador.evaluacionFact(tam, j, pos, solucion, valor_orig);
                        t_evals++;
                        if(valor < valor_orig){     //Reiniciamos para que empiece de nuevo la busqueda
                            encontrado=true;
                            Modificar(solucion,j,pos);
                            valor_orig=valor;
                            aux = gRandom.Barajar(tam);
                            j=tam;
                            i=-1;
                        }
                    }
                }
                if(!encontrado)
                    bits.set(pos, 1);
            }
        }
        
        evaluaciones=t_evals;
        
        vSol = evaluador.evaluacion(solucion);
        
        
        return vSol;
        
    }
    
    public void Modificar(ArrayList<Integer> sol, int a, int b){
        
        int aux = sol.get(a);
        sol.set(a, sol.get(b));
        sol.set(b, aux);
    }
    
    public void setSolucion(ArrayList<Integer> s){
        solucion = s;
    }
    
    public ArrayList<Integer> getSolucion(){
        return solucion;
    }
    
    public int getEvaluaciones(){
        return evaluaciones;
    }
}
