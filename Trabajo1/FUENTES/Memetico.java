
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
public class Memetico {
    
    int tamCrom;
    int tamPob;
    GeneradorAleatorio gRandom;
    Evaluador evaluador;
    int valS;
    
    
    public Memetico(int n,int pb, GeneradorAleatorio g, Evaluador e,int cruce, float pCruce, float pMutar, int tipo){
        
        tamCrom = n;
        tamPob = pb;
        gRandom = g;
        evaluador = e;
        int n_iteraciones;
        
        if(tipo == 1)
            Inicio1(pCruce, pMutar, cruce);
        else if(tipo == 2)
            Inicio0(pCruce, pMutar, cruce);
        else
            InicioMejor(pCruce, pMutar, cruce);
        
        
        
    }
    
    public void Inicio1(float pCruce, float pMutar, int cruce){
        
        int n_iteraciones;
        
        Cromosoma aux = new Cromosoma();
        Cromosoma aux2;
        int eval;
        
        int max = ((tamPob+1)*4)-1; //Conseguimos que solo haga 10 generaciones
        BusquedaLocal local = new BusquedaLocal(tamCrom,gRandom,null,evaluador,400,false);
        Genetico genetico = new Genetico(tamCrom,tamPob,gRandom,evaluador,1,cruce,pCruce, pMutar,max,true);
        
        
       
        
        max = (4*tamPob)+9;
        genetico.setMax(max); //Ya no hace generar la poblacion inicial
        
        n_iteraciones = genetico.getEvaluaciones();
        
        while(n_iteraciones < 50000){
        
            ArrayList<Cromosoma> pob = genetico.getPoblacion();
            
            

            for(int i=0;i<tamPob;i++){
                local.setSolucion(pob.get(i).ObtenerCromosoma());
                eval = local.Inicio(pob.get(i).getValoracion());
                aux.establecerValor(eval);
                aux.SetCromosoma(local.getSolucion());
                aux2 = aux.copia();
                pob.set(i, aux2);
                n_iteraciones = n_iteraciones + local.getEvaluaciones();
            }
            
            genetico.setPoblacion(pob);

            
            if(cruce == 1)
                genetico.InicioGenPos(pMutar, pCruce);
            else
                genetico.InicioGenOX(pMutar, pCruce);
            
            n_iteraciones = n_iteraciones + genetico.getEvaluaciones();
        
        }
        
        valS = genetico.getPoblacion().get(0).valoracion;
        
    }
    
    public void Inicio0(float pCruce, float pMutar, int cruce){
        
        int n_iteraciones;
        
        Cromosoma aux = new Cromosoma();
        int eval;
        int r;
        
        int max = ((tamPob+1)*4)-1; //Conseguimos que solo haga 10 generaciones
        BusquedaLocal local = new BusquedaLocal(tamCrom,gRandom,null,evaluador,400,false);
        Genetico genetico = new Genetico(tamCrom,tamPob,gRandom,evaluador,1,cruce,pCruce, pMutar,max,true);
        
        max = (4*tamPob)+9;
        genetico.setMax(max); //Ya no hace generar la poblacion inicial
        
        n_iteraciones = genetico.getEvaluaciones();
        
        while(n_iteraciones < 50000){
        
            ArrayList<Cromosoma> pob = genetico.getPoblacion();

             r=gRandom.randPseudoInt(0, tamPob-1);
            
            
            local.setSolucion(pob.get(r).ObtenerCromosoma());
            eval = local.Inicio(pob.get(r).getValoracion());
            aux.establecerValor(eval);
            aux.SetCromosoma(local.getSolucion());
            pob.set(r, aux);
            n_iteraciones = n_iteraciones + local.getEvaluaciones();
            

            
            genetico.setPoblacion(pob);
            if(cruce == 1)
                genetico.InicioGenPos(pMutar, pCruce);
            else
                genetico.InicioGenOX(pMutar, pCruce);
            
            n_iteraciones = n_iteraciones + genetico.getEvaluaciones();
        
        }
        
        valS = genetico.getPoblacion().get(0).valoracion;
        
    }
    
    public void InicioMejor(float pCruce, float pMutar, int cruce){
        
        int n_iteraciones;
        
        Cromosoma aux = new Cromosoma();
        int eval;
        int r;
        
        int max = ((tamPob+1)*4)-1; //Conseguimos que solo haga 10 generaciones
        BusquedaLocal local = new BusquedaLocal(tamCrom,gRandom,null,evaluador,400,false);
        Genetico genetico = new Genetico(tamCrom,tamPob,gRandom,evaluador,1,cruce,pCruce, pMutar,max,true);
        
        max = (4*tamPob)+9;
        genetico.setMax(max); //Ya no hace generar la poblacion inicial
        
        n_iteraciones = genetico.getEvaluaciones();
        
        while(n_iteraciones < 50000){
        
            ArrayList<Cromosoma> pob = genetico.getPoblacion();
            
            
            local.setSolucion(pob.get(0).ObtenerCromosoma());
            eval = local.Inicio(pob.get(0).getValoracion());
            aux.establecerValor(eval);
            aux.SetCromosoma(local.getSolucion());
            pob.set(0, aux);
            n_iteraciones = n_iteraciones + local.getEvaluaciones();
            

            
            genetico.setPoblacion(pob);
            if(cruce == 1)
                genetico.InicioGenPos(pMutar, pCruce);
            else
                genetico.InicioGenOX(pMutar, pCruce);
            
            n_iteraciones = n_iteraciones + genetico.getEvaluaciones();
        
        }
        
        valS = genetico.getPoblacion().get(0).valoracion;
        
    }
    
}
