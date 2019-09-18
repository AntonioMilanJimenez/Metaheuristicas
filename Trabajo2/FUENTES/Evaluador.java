
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
public class Evaluador {
    
    static int [][] matrizFlujo;
    static int [][] matrizDistancias;
    
    public Evaluador(int [][] f, int [][] d){
        matrizFlujo = f;
        matrizDistancias = d;
    }
    
    public int evaluacion(ArrayList<Integer> cr){
        int n = 0;
        int k,z;
        int tam = cr.size();
        
        
        for(int i=0;i<tam;i++){
            for(int j=0;j<tam;j++){
               k= cr.get(i)-1;
               z = cr.get(j)-1;
               n = n + matrizFlujo[i][j]*matrizDistancias[k][z]; 
            }
        }
        
        return n;
    }
    
    public int evaluacionGrasp(ArrayList<Integer> cr){
        int n = 0;
        int k,z;
        int tam = cr.size();
        
        
        for(int i=0;i<tam;i++){
            for(int j=0;j<tam;j++){
                    if(cr.get(i)!=0 && cr.get(j)!=0){ 
                    k= cr.get(i)-1;
                    z = cr.get(j)-1;
                    n = n + matrizFlujo[i][j]*matrizDistancias[k][z];
               }
            }
        }
        
        return n;
    }
    
    public int evaluacionFact(int tam, int r, int s, ArrayList<Integer> cr, int valor_orig){
        
        int n=0;
        int vr = cr.get(r)-1;
        int vs = cr.get(s)-1;
        int vk;
        
        for(int i=0;i<tam;i++){
           if(i!= r && i!= s){
                vk = cr.get(i)-1;
                n=n+matrizFlujo[r][i]*(matrizDistancias[vs][vk]-matrizDistancias[vr][vk]) +matrizFlujo[s][i]*(matrizDistancias[vr][vk]-matrizDistancias[vs][vk]) +matrizFlujo[i][r]*(matrizDistancias[vk][vs]-matrizDistancias[vk][vr]) +matrizFlujo[i][s]*(matrizDistancias[vk][vr]-matrizDistancias[vk][vs]); 
           } 
        }
        n=n+valor_orig;
        return n;
    }
    
    
    
    
    
}
