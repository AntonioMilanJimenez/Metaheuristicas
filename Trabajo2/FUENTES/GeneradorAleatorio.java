
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
public class GeneradorAleatorio {
    
    int seed = 0;
    int mask = 0;
    int prime = 0;
    float scale = 0;
    
    public GeneradorAleatorio(int se, int m, int p, float s){
        
        seed = se;
        mask = m;
        prime = p;
        scale = s;
    }
    
    public float randPseudo(){
        float r = ((seed = ((seed*prime) & mask)) * scale);
        return r;
    }
    
    public int randPseudoInt(int low, int high){
        
        int r = (int) (low + (high-low+1) * randPseudo());
        if(r>=high)
            r=high-1;
        return r;
    }
    
    public float randPseudoFloat(float low, float high){
        
        float r = (low + (high-low) * randPseudo());
        return r;
    }
    
    public ArrayList<Integer> Barajar(int tam){
        
        ArrayList<Integer> resultado = new ArrayList();
        int r;
        int aux;
        
        
        for(int i=0;i<tam;i++){
            resultado.add(i+1);
        }
        
        for(int i=0;i<tam;i++){
            r=randPseudoInt(0,tam-1);
            aux= resultado.get(i);
            resultado.set(i, resultado.get(r));
            resultado.set(r,aux);
        }
        
        return resultado;
        
    }
    
    public ArrayList<Integer> BarajarLista(ArrayList<Integer> lista, int tam){
        
     
        int r;
        int aux;
        
         
        for(int i=0;i<tam;i++){
            r=randPseudoInt(0,tam-1);
            aux= lista.get(i);
            lista.set(i, lista.get(r));
            lista.set(r,aux);
        }
        
        return lista;
        
    }
    
}
