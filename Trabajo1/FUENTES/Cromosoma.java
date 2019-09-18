
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
public class Cromosoma {
    
    ArrayList<Integer> cromosoma = null;
    int valoracion;
    
    public Cromosoma(){
        cromosoma = new ArrayList();
        valoracion = 100000;
    }
    
    public void aniade(int n){
        cromosoma.add(n);
    }
    
    public int getValoracion(){
        return valoracion;
    }
    
    public int obtener(int i){
        return cromosoma.get(i);
    }
    
    public void establecer(int n, int pos){
        cromosoma.add(pos, n);
    }
    
    public void establecerValor(int n){
        valoracion = n;
    }
    
    public void reemplazar(int n, int pos){
        cromosoma.set(pos, n);
    }
    
    public boolean encontrar(int n){
        return cromosoma.contains(n);
    }
    
    public boolean contiene(int x,int min,int max){
        return (cromosoma.subList(min,max)).contains(x);
    }
    
    public int tama(){
        return cromosoma.size();
    }
    
    public void mostrarCrom(){
        int n = cromosoma.size();
        for(int i=0;i<n;i++)
            System.out.print(cromosoma.get(i) + " ");
        System.out.println();
    }
    
    public Cromosoma copia(){
        Cromosoma nuevo = new Cromosoma();
        nuevo.valoracion = this.valoracion;
        int tam = this.cromosoma.size();
        for(int i=0;i<tam;i++){
            nuevo.aniade(this.obtener(i));
        }
        
        return nuevo;
        
    }
    
    public ArrayList<Integer> ObtenerCromosoma(){
        return cromosoma;
    }
    
    public void SetCromosoma(ArrayList<Integer> a){
        cromosoma=a;
    }
    
}
