package IA.Bicing;

import aima.search.framework.HeuristicFunction;

// Funcion heuriatica que solo tiene en cuenta el beneficio obtenido por cubrir la demanda
public class BicingSimpleHeuristicFunction implements HeuristicFunction{
    
    public double getHeuristicValue(Object n) {
        
        BicingBoard board = (BicingBoard) n;
      
        int[][] furgs = board.getFurgonetas();
        Estaciones ests = board.getEstaciones();
        
        double sumaTot = 0; 
        
        for (int i = 0; i < furgs.length; ++i) {
            if (furgs[i][1] != -1) sumaTot += furgs[i][3];
            if (furgs[i][2] != -1) sumaTot += furgs[i][4];
        }
        
        board.setBeneficio(sumaTot);
        return -sumaTot;
    }
}