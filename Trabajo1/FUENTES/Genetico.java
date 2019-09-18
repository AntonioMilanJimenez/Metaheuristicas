
import static java.lang.Math.ceil;
import static java.lang.Math.log;
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
public class Genetico {
    
    ArrayList<Cromosoma> poblacion = new ArrayList();
    int nCrom = 0;
    GeneradorAleatorio gRandom = null;
    Evaluador evaluador;
    int tam_pob;
    int max_iter;
    int evaluaciones;
    int vSol;
    
    
    public Genetico(int n,int pb, GeneradorAleatorio g, Evaluador e,int seleccion, int cruce, float pCruce, float pMutar, int max, boolean generar){
        nCrom = n;
        gRandom = g;
        evaluador = e;
        tam_pob = pb;
        max_iter = max;
        
        if(generar)
            generarPobInicial();
        
        if(seleccion == 1 && cruce == 1)
            InicioGenPos(pMutar, pCruce);
        else if(seleccion == 1 && cruce == 2)
            InicioGenOX(pMutar, pCruce);
        else if(seleccion == 2 && cruce == 1)
            InicioEstPos(pMutar, pCruce);
        else if(seleccion == 2 && cruce == 2)
            InicioEstOX(pMutar, pCruce);
        
        
        
        
          
        
    }
    
    public void InicioGenPos(float pMutar, float pCruce){
        
        boolean fin=false;
        int n_iteraciones = 0;
        ArrayList<Cromosoma> padres = new ArrayList();
        ArrayList<Cromosoma> hijos = new ArrayList();
        int tam;
        int valor;
        int cruces;
        
         
        Cromosoma aux;
        
        ordenarPoblacion(poblacion);
        
       
        n_iteraciones = n_iteraciones + tam_pob;
        cruces = (int) (pCruce*tam_pob/2);
        
        while(!fin){
            
          Cromosoma copia = (poblacion.get(0)).copia();
          
          
          padres = seleccionGeneracional(); 
          
          hijos = cruzarPosicion(padres,cruces);
    
          Mutar(hijos, pMutar);
         

          tam = hijos.size();
          for(int i=0;i<tam;i++){
              aux = (hijos.get(i));
              valor = evaluador.evaluacion(aux.ObtenerCromosoma());
              aux.establecerValor(valor);
          }
          
          n_iteraciones = n_iteraciones + tam;
          
          
          tam = padres.size();
          for(int i=cruces+1;i<padres.size();i++){
              hijos.add(padres.get(i));
          }
          
         
          ordenarPoblacion(hijos);
          if(!hijos.contains(copia)){
              hijos.set(tam-1, copia);
          }
          ordenarPoblacion(hijos);
          poblacion = hijos;
          
                       
          if(n_iteraciones>max_iter)
              fin=true;
          
          
        }
        
        evaluaciones = n_iteraciones;
        
       
        
        vSol = poblacion.get(0).valoracion;
    }
    
    public void InicioGenOX(float pMutar, float pCruce){
        
        boolean fin=false;
        int n_iteraciones = 0;
        ArrayList<Cromosoma> padres = new ArrayList();
        ArrayList<Cromosoma> hijos = new ArrayList();
        int tam;
        int valor;
        Cromosoma aux;
        int cruces;
        
        ordenarPoblacion(poblacion);
        
        n_iteraciones = n_iteraciones + tam_pob;
        cruces = (int) (pCruce*tam_pob/2);
        
        while(!fin){
            
          Cromosoma copia = (poblacion.get(0)).copia();
          padres = seleccionGeneracional();
          hijos = cruzarOX(padres, cruces);
    
          Mutar(hijos, pMutar);

          tam = hijos.size();
          for(int i=0;i<tam;i++){
              aux = (hijos.get(i));
              valor = evaluador.evaluacion(aux.ObtenerCromosoma());
              aux.establecerValor(valor);
          }
          
          n_iteraciones = n_iteraciones + tam;
          
          tam = padres.size();
          for(int i=cruces+1;i<padres.size();i++){
              hijos.add(padres.get(i));
          }
         
          ordenarPoblacion(hijos);
          if(!hijos.contains(copia)){
              hijos.set(tam-1, copia);
          }
          ordenarPoblacion(hijos);
          poblacion = hijos;
                       
          if(n_iteraciones>max_iter)
              fin=true;
        }
        
        evaluaciones = n_iteraciones;
        
        vSol = poblacion.get(0).valoracion;
    }
    
    public void InicioEstPos(float pMutar, float pCruce){
        
        boolean fin=false;
        int n_iteraciones = 0;
        ArrayList<Cromosoma> padres = new ArrayList();
        ArrayList<Cromosoma> hijos = new ArrayList();
        int tam;
        int valor;
        int cruces;
        Cromosoma aux;
        
        ordenarPoblacion(poblacion);
        
        n_iteraciones = n_iteraciones + tam_pob;
        
        cruces = 2;
        
        while(!fin){
            
            Cromosoma copia = (poblacion.get(0)).copia();
            padres = seleccionEstacionario();
            
            
            hijos = cruzarPosicion(padres,cruces);

            Mutar(hijos, pMutar);

            tam = hijos.size();
            
            for(int i=0;i<tam;i++){
                aux = (hijos.get(i));
                valor = evaluador.evaluacion(aux.ObtenerCromosoma());
                aux.establecerValor(valor);
            }

            n_iteraciones = n_iteraciones + tam;
         
            ordenarPoblacion(hijos);
            if(hijos.get(0).valoracion < poblacion.get(tam_pob-2).valoracion){
                poblacion.set(tam_pob-2,hijos.get(0));
                if(hijos.get(1).valoracion < poblacion.get(tam_pob-1).valoracion){
                    poblacion.set(tam_pob-1,hijos.get(1));
                }
                ordenarPoblacion(poblacion);
            }else if(hijos.get(0).valoracion < poblacion.get(tam_pob-1).valoracion){
                poblacion.set(tam_pob-1,hijos.get(0));
                ordenarPoblacion(poblacion);
            }
                       
          if(n_iteraciones>max_iter)
              fin=true;
        }
        
        vSol = poblacion.get(0).valoracion;
    }
    
    public void InicioEstOX(float pMutar, float pCruce){
        
        boolean fin=false;
        int n_iteraciones = 0;
        ArrayList<Cromosoma> padres = new ArrayList();
        ArrayList<Cromosoma> hijos = new ArrayList();
        int tam;
        int valor;
        int cruces;
        Cromosoma aux;
        
        
        ordenarPoblacion(poblacion);
        
        n_iteraciones = n_iteraciones + tam_pob;
        cruces = 2;
        
        while(!fin){
            
            Cromosoma copia = (poblacion.get(0)).copia();
            padres = seleccionEstacionario();
            hijos = cruzarOX(padres, cruces);

            Mutar(hijos, pMutar);

            tam = hijos.size();
            
            
            for(int i=0;i<tam;i++){
                aux = (hijos.get(i));
                valor = evaluador.evaluacion(aux.ObtenerCromosoma());
                aux.establecerValor(valor);
            }

            n_iteraciones = n_iteraciones + tam;
         
            ordenarPoblacion(hijos);
            if(hijos.get(0).valoracion < poblacion.get(tam_pob-2).valoracion){
                poblacion.set(tam_pob-2,hijos.get(0));
                if(hijos.get(1).valoracion < poblacion.get(tam_pob-1).valoracion){
                    poblacion.set(tam_pob-1,hijos.get(1));
                }
                ordenarPoblacion(poblacion);
            }else if(hijos.get(0).valoracion < poblacion.get(tam_pob-1).valoracion){
                poblacion.set(tam_pob-1,hijos.get(0));
                ordenarPoblacion(poblacion);
            }
                       
          if(n_iteraciones>max_iter)
              fin=true;
        }
        
        vSol = poblacion.get(0).valoracion;
    }
    
    
    public boolean checkRandom(int n, Cromosoma cr){
        return cr.encontrar(n);
    }
    
    public void ordenarPoblacion(ArrayList<Cromosoma> vector){
        int min;
        int posmin;
        Cromosoma aux = new Cromosoma();
        int m_tam = vector.size();
        for(int i=0;i<m_tam;i++){
            posmin=i;
            min=(vector.get(posmin)).valoracion;
            for(int j=i+1;j<m_tam;j++){
               if((vector.get(j)).valoracion<min){
                   min=(vector.get(j)).valoracion;
                   posmin=j;
               }
            }
            if(i!=posmin){
                aux=vector.get(i);
                vector.set(i,vector.get(posmin));
                vector.set(posmin, aux);
            }
        }
    }
    
    public void generarPobInicial(){
        Cromosoma aux = new Cromosoma();
        int n;
        for(int j=0;j<tam_pob;j++){
            aux.SetCromosoma(gRandom.Barajar(nCrom));
            aux.establecerValor(evaluador.evaluacion(aux.ObtenerCromosoma()));
            poblacion.add(aux);
            aux = new Cromosoma();
        }
        
        
        
    }
   
    public ArrayList<Cromosoma> seleccionGeneracional(){
        ArrayList<Cromosoma> padres = new ArrayList();
        int n1,n2,ipadre;
        
        for(int i=0;i<tam_pob;i++){
            n1 = gRandom.randPseudoInt(0, (tam_pob-1));
            do{
                n2 = gRandom.randPseudoInt(0, (tam_pob-1));
            }while(n2==n1);    
            ipadre = torneoBinario(n1,n2);
            padres.add(poblacion.get(ipadre));
        }
        
        return padres;
    }
    
    public ArrayList<Cromosoma> seleccionEstacionario(){
        ArrayList<Cromosoma> padres = new ArrayList();
        int n1,n2,ipadre;
        
        for(int i=0;i<2;i++){
            n1 = gRandom.randPseudoInt(0, tam_pob-1);
            do{
                n2 = gRandom.randPseudoInt(0, tam_pob-1);
            }while(n2==n1);    
            ipadre = torneoBinario(n1,n2);
            padres.add(poblacion.get(ipadre).copia());
        }
        
        return padres;
    }
    
    
    public void mostrarPob(ArrayList<Cromosoma> mostrar){
        for(int i=0;i<mostrar.size();i++){
            mostrar.get(i).mostrarCrom();
        }
    }
    
    public int torneoBinario(int cr1, int cr2){
        if((poblacion.get(cr1)).valoracion < (poblacion.get(cr2)).valoracion)
            return cr1;
        else
            return cr2;
          
    }
    
    public Cromosoma crucePosicion(Cromosoma cr1, Cromosoma cr2){
        
        ArrayList<Integer> hijo = new ArrayList();
        Cromosoma resultado = new Cromosoma();
        ArrayList<Integer> r = gRandom.Barajar(nCrom);
        
        int n,aux, aux2, t1;
        
        for(int i=0;i<nCrom;i++){
            if(cr1.obtener(i) == cr2.obtener(i)){
                aux = cr1.obtener(i);
                aux2 = r.get(i);
                if(aux != aux2){
                    t1 = hijo.indexOf(aux);
                    if(t1 != -1)
                        hijo.set(t1, aux2);
                    else{
                        t1 = r.indexOf(aux);
                        r.set(t1, aux2);
                        r.set(i, aux);
                    }
                }
                hijo.add(aux);
            }else{
               hijo.add(r.get(i));           
            }    
        }

        
        resultado.SetCromosoma(hijo);
        
        return resultado;
    }
    
    public ArrayList<Cromosoma> cruzarPosicion(ArrayList<Cromosoma> padres, int cruces){
        
        
        ArrayList<Cromosoma> hijos = new ArrayList();
        Cromosoma hijo1 = new Cromosoma();
        Cromosoma hijo2 = new Cromosoma();

        
        for(int i=0;i<cruces;i=i+2){
            hijo1 = crucePosicion(padres.get(i),padres.get(i+1));
            hijo2 = crucePosicion(padres.get(i),padres.get(i+1));
            hijos.add(hijo1);
            hijos.add(hijo2);
        }
        
        
        return hijos;
    }
    
    public ArrayList<Cromosoma> cruzarOX(ArrayList<Cromosoma> padres, int cruces){
        
        
        ArrayList<Cromosoma> hijos = new ArrayList();
        Cromosoma hijo1 = new Cromosoma();
        Cromosoma hijo2 = new Cromosoma();
        
        for(int i=0;i<cruces;i=i+2){
            hijo1 = cruceOX(padres.get(i),padres.get(i+1));
            hijo2 = cruceOX(padres.get(i+1),padres.get(i));
            hijos.add(hijo1);
            hijos.add(hijo2);
        }
        
        
        return hijos;
    }
    
    public Cromosoma cruceOX(Cromosoma cr1, Cromosoma cr2){
        
        Cromosoma hijo1 = new Cromosoma();
        ArrayList<Integer> auxiliar = new ArrayList();
        ArrayList<Integer> orden = new ArrayList();
        
        
        
        int n = nCrom/2;
        int min = n-n/2;
        int max = n+n/2;
        
        for(int i=0;i<nCrom;i++){
            if(i>=min && i < max)
                hijo1.aniade(cr1.obtener(i));
            else{
                hijo1.aniade(-1);
                auxiliar.add(cr1.obtener(i));
            }  
        }    
        
        
        
        
        for(int i = 0;i<auxiliar.size();i++){
            orden.add(cr2.ObtenerCromosoma().indexOf(auxiliar.get(i))); //Obtenemos el orden en el que aparecen en el cromosoma 2 los valores que han quedado fuera del cromosoma 1     
        }
        
        int j=max;
        int a;
        for(int i=0;i<nCrom;i++){
            a= orden.indexOf(i);
            if(a!=-1){
                j=j%nCrom;
                hijo1.reemplazar(auxiliar.get(a), j);
                j++;
            }
        }
     
        return hijo1;
        
    }
    
    public void Mutar(ArrayList<Cromosoma> hijos, float pMutar){
        int tam_hijos = hijos.size();
        int n_mutaciones = (int) (pMutar*nCrom*tam_pob);
        int n=0;
        float m;
        float next=0;
        float next2;
        int cromMuta, gen1, gen2, aux;
        Cromosoma cromMutado;
        
        
        
        while(n<n_mutaciones){
            
            cromMuta = gRandom.randPseudoInt(1, tam_hijos)-1;
            gen1 = gRandom.randPseudoInt(1, nCrom)-1;
            do{
            gen2 = gRandom.randPseudoInt(1, nCrom)-1;
            }while(gen1 == gen2);
            cromMutado = hijos.get(cromMuta);
            aux = cromMutado.obtener(gen1);
            cromMutado.reemplazar(cromMutado.obtener(gen2), gen1);
            cromMutado.reemplazar(aux, gen2);
            
            
            
            n++;
        }
        
    }
    
    public void setPoblacion(ArrayList<Cromosoma> p){
        poblacion = p;
    }
    
    public ArrayList<Cromosoma> getPoblacion(){
        return poblacion;
    }
    
    public void setMax(int a){
        max_iter = a;
    }
    
    public int getEvaluaciones(){
        return evaluaciones;
    }
    
}
