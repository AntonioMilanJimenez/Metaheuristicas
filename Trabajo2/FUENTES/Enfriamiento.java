
import static java.lang.Math.exp;
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
public class Enfriamiento {
    
    int tam;
    GeneradorAleatorio gRandom;
    ArrayList<Integer> mejor_solucion;
    Evaluador evaluador;
    int vSol;
    float temperatura;
    float t_final;
    float beta;
    int max_enfriamientos;
    int max_vecinos;
    
    public Enfriamiento(int t, int max, int m_vecinos, GeneradorAleatorio g, Evaluador e, float t_ini, float tf , ArrayList<Integer> ms, int v){
        
        tam=t;
        gRandom=g;
        evaluador=e;
        temperatura=t_ini;
        t_final=tf;
        mejor_solucion = ms;
        vSol = v;
        max_enfriamientos=max;
        max_vecinos=m_vecinos;
        beta=(temperatura-t_final)/(max_enfriamientos*temperatura*t_final);
        
    }
    
    public void Inicio(){
        
        ArrayList<Integer> solucion;
        ArrayList<Integer> vecino;
        ArrayList<Integer> solucion_aux;
        int valor,a,b, vVecino, diferenciaV;
        
        solucion=mejor_solucion;
        valor = vSol;
        
        while(temperatura>t_final){
            
            for(int i=0;i<max_vecinos;i++){
                
                a=gRandom.randPseudoInt(0, tam-1);
        
                do{
                    b=gRandom.randPseudoInt(0, tam-1);  
                }while(a==b);
                
                vecino=GeneraVecino(solucion,a,b);
                vVecino = evaluador.evaluacionFact(tam,a,b,solucion,valor);
                
                
                diferenciaV = vVecino - valor;
                
                if(diferenciaV<0 || (gRandom.randPseudoFloat(0,1) <= exp((-diferenciaV/(i+1))*temperatura))){      // || (gRandom.randPseudoFloat(0,1) <= exp((-diferenciaV/(i+1))*temperatura))
                    solucion=(ArrayList<Integer>) vecino.clone();
                    valor=vVecino;
                    if(valor<vSol){
                        mejor_solucion=(ArrayList<Integer>) solucion.clone();
                        vSol=valor;
                        
                    }
                }
                
            }
            
            
            enfria();
        }
        
    }
    
    public void enfria(){
        
        //temperatura= temperatura/(1+beta*temperatura);
        temperatura = (float) (temperatura*0.85);
        
        
    }
    
    
    public ArrayList<Integer> GeneraVecino(ArrayList<Integer> sol,int a, int b){
        
        
        ArrayList<Integer> vecino;
        
        vecino=(ArrayList<Integer>) sol.clone();
        
        
        int aux = vecino.get(a);
        vecino.set(a, vecino.get(b));
        vecino.set(b, aux);
        
        return vecino;
    }
    
    public ArrayList<Integer> getSolucion(){
        return mejor_solucion;
    }
    
    public int getValorSolucion(){
        return vSol;
    }
    
}
