
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
public class GRASP {
    
    float alpha;
    GeneradorAleatorio gRandom;
    Evaluador evaluador;
    int tam;
    ArrayList<Integer> mejor_solucion;
    int vSol;
    
    public GRASP(int t,GeneradorAleatorio g, Evaluador e, float a){
        
        gRandom = g;
        evaluador = e;
        alpha = a;
        tam=t;        
        
    }
    
    
    public void Inicio(){
        
        
        
        ArrayList<Integer> lcU = gRandom.Barajar(tam);
        ArrayList<Integer> lcL = gRandom.Barajar(tam);
        ArrayList<Integer> lcrU = (ArrayList<Integer>) lcU.clone();
        ArrayList<Integer> lcrL = (ArrayList<Integer>) lcL.clone();
        
        ArrayList<Integer> solucion = new ArrayList();
        for(int i=0;i<tam;i++){
            solucion.add(0);
        }
        ArrayList<Integer> solucion_aux = new ArrayList();
        
        int a,b,c,d,valor_aux,ap,bp,cp,dp;
        
        float cota_u = tam - alpha*(tam-1);
        float cota_l = 1 + alpha*(tam-1);
        
        for(int i=0;i<lcrU.size();i++){
            if(lcrU.get(i) < cota_u){
                lcrU.remove(i);
                i--;
            }
        }
        
        for(int i=0;i<lcrL.size();i++){
            if(lcrL.get(i) > cota_l){
                lcrL.remove(i);
                i--;
            }
        }
        
        ap = gRandom.randPseudoInt(0, lcrU.size()-1);
        a=lcU.indexOf(lcrU.get(ap));
        lcrU.remove(ap);
        
        bp = gRandom.randPseudoInt(0, lcrL.size()-1);
        b=lcL.indexOf(lcrL.get(bp));
        lcrL.remove(bp);
        
        solucion.set(a, b+1);
        
        cp = gRandom.randPseudoInt(0, lcrU.size()-1);
        do{
            c=lcU.indexOf(lcrU.get(cp));
        }while(c==-1);
        lcrU.remove(cp);
        
        dp = gRandom.randPseudoInt(0, lcrU.size()-1);
        do{
            d=lcL.indexOf(lcrL.get(dp));
        }while(d==-1);
        lcrU.remove(dp);
        
        solucion.set(c, d+1);
        
        ArrayList<ParejaGrasp> lc = new ArrayList();
        ParejaGrasp mejor_pareja=null;
        int  v_minimo;
        ArrayList<Integer> cogidos = new ArrayList();
        int cogido=-1;
        cogidos.add(b);
        cogidos.add(d);
        
        for(int i=0;i<tam;i++){
            v_minimo=999999999;
            if(solucion.get(i)==0){
                for(int j=0;j<tam;j++){
                    if(cogidos.indexOf(j+1)==-1){
                        solucion_aux = (ArrayList<Integer>) solucion.clone();
                        solucion_aux.set(i,j+1);
                        valor_aux=evaluador.evaluacionGrasp(solucion_aux);

                        if(valor_aux<v_minimo || j==0){
                            v_minimo=valor_aux;
                            mejor_pareja=new ParejaGrasp(i,j+1,v_minimo);
                            cogido=j+1;
                        }
                    }    
                }
                cogidos.add(cogido);
                lc.add(mejor_pareja);
            }
            
        }

        
        for(int i=0;i<tam-2;i++){
            solucion.set(lc.get(i).pos,lc.get(i).valor);
        }

        
        mejor_solucion = solucion;
        vSol = evaluador.evaluacion(mejor_solucion);
    }
    
}
