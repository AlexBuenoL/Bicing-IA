package IA.Bicing;

import aima.search.framework.HeuristicFunction;

// Funcion heuristica que combina el criterio de maximizacion de la heur?stica simple 
// con el criterio de minimizar los costes de transporte de las furgonetas segun los kms recorridos
public class BicingCombinatedHeuristicFunction implements HeuristicFunction{
    
    private int getDistancia(int xi, int yi, int xj, int yj) {
        int dist1 = Math.abs(xi-xj);
        int dist2 = Math.abs(yi-yj);
        return dist1 + dist2;
    }
    
    public double getHeuristicValue(Object n) {
        BicingBoard board = (BicingBoard) n;
        int[][] furgs = board.getFurgonetas();
        Estaciones ests = board.getEstaciones();

        double sumaTot = 0; 

        for (int i = 0; i < furgs.length; ++i) {
                int d1 = 0; int d2 = 0; int tot1 = 0; int tot2 = 0;
                if (furgs[i][1] != -1) {
                    sumaTot += furgs[i][3];
                    d1 = getDistancia(ests.get(furgs[i][0]).getCoordX(),ests.get(furgs[i][0]).getCoordY(),ests.get(furgs[i][1]).getCoordX(),ests.get(furgs[i][1]).getCoordY());
                    if (furgs[i][3] > 0) tot1 = ((furgs[i][3]+furgs[i][4]+9)/10)*d1/1000;
                    sumaTot -= tot1;
                }
                if (furgs[i][1] != -1 && furgs[i][2] != -1) {
                    sumaTot += furgs[i][4];
                    d2 = getDistancia(ests.get(furgs[i][1]).getCoordX(),ests.get(furgs[i][1]).getCoordY(),ests.get(furgs[i][2]).getCoordX(),ests.get(furgs[i][2]).getCoordY());
                    if (furgs[i][4] > 0) tot2 = ((furgs[i][4]+9)/10)*d2/1000;
                    sumaTot -= (tot1+tot2);
                }
        }
        board.setBeneficio(sumaTot);
        return -sumaTot;
    }
}