package IA.Bicing;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.*;
import aima.util.Pair;

public class BicingSuccessorFunctionSA implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal= new ArrayList();
        BicingBoard board=(BicingBoard) aState;
        BicingSimpleHeuristicFunction BicingHF = new BicingSimpleHeuristicFunction();
        int f = board.getNFurgonetas();
        int e = board.getNEstaciones();
        Random myRandom = new Random();       
        String S = "";
        int i, j;
        double v;
        
        BicingBoard newBoard = new BicingBoard(board.getBeneficio(), board.getFurgonetas());
        
        List<Pair> operadoresValidos;
        do {
            operadoresValidos = new ArrayList<>();
            i = myRandom.nextInt(f);
            for (j = 0; j < e; ++j) {
                if (board.puedoAnadirEstacionRuta(i,j)) {
                    operadoresValidos.add(new Pair("addEst", j));
                }
            }
            for (j = 0; j < e; ++j) {
                if (board.puedoCambiarOrigen(j)) {
                    operadoresValidos.add(new Pair("changeOrig", j));
                }
            }
        } while(operadoresValidos.isEmpty());

        int ind = myRandom.nextInt(operadoresValidos.size());
        Pair op = operadoresValidos.get(ind);

        if(op.getFirst().equals("addEst")) {
            newBoard.anadirEstacionRuta((Integer)op.getSecond(), i);
            v = BicingHF.getHeuristicValue(newBoard);
            S = " Furgoneta " + i + " Estacion " + j + " anadirEstacion(" + v + ")";
            retVal.add(new Successor(S, newBoard));
            
        } else if(op.getFirst().equals("changeOrig")) {
            newBoard.cambiarOrigen((Integer)op.getSecond(), i);
            v = BicingHF.getHeuristicValue(newBoard);
            S = " Furgoneta " + i + " Estacion " + j + " cambiarOrigen(" + v + ")";
            retVal.add(new Successor(S, newBoard));
        }
        return retVal;
    }
}