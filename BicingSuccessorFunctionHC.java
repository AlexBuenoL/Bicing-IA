package IA.Bicing;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.*;

public class BicingSuccessorFunctionHC implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal= new ArrayList();
        BicingBoard board=(BicingBoard) aState;
        BicingSimpleHeuristicFunction BicingHF = new BicingSimpleHeuristicFunction();
        int f = board.getFurgonetas().length;
        int e = board.getEstaciones().size();
        double v = 0;
        String S;
        
        for(int i=0;i<f;i++){ //provem per a totes les furgonetes per generar maxim de successors
            /*
            if (board.puedoBorrarEstacionRuta(i)) {
                    BicingBoard newBoard = new BicingBoard(board.getBeneficio(), board.getFurgonetas());
                    newBoard.borrarEstacionRuta(i);
                    v = BicingHF.getHeuristicValue(newBoard);
                    S = " " + i + " borrar(" + v + ")";
                    retVal.add(new Successor(S , newBoard));
            }*/
            
            for (int j = 0; j < e; j++) {
                if (board.puedoAnadirEstacionRuta(i,j)) {
                    BicingBoard newBoard = new BicingBoard(board.getBeneficio(), board.getFurgonetas());
                    newBoard.anadirEstacionRuta(j,i);
                    v = BicingHF.getHeuristicValue(newBoard);
                    S = " Furgoneta " + i + " Estacion " + j + " anadirEstacion(" + v + ")";
                    retVal.add(new Successor(S , newBoard));
                }
            }
            
            
            for (int j = 0; j < e; j++) {
                if (board.puedoCambiarOrigen(j)) {
                    BicingBoard newBoard = new BicingBoard(board.getBeneficio(), board.getFurgonetas());
                    newBoard.cambiarOrigen(j,i);
                    v = BicingHF.getHeuristicValue(newBoard);
                    S = " Furgoneta " + i + " Estacion " + j + " cambiarOrigen(" + v + ")";
                    retVal.add(new Successor(S , newBoard));
                }
            }
        }
        return (retVal);
    }
}