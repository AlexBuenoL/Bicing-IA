package IA.Bicing;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        
        int nest, nbic, dem, seed, f, heuristic, solIni;
        //nest = 25; nbic = 1250; dem = 1; seed = 420; f = 5; heuristic = 1; solIni = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueixi el nombre d estacions: ");
        nest = sc.nextInt();
        System.out.println("Introdueixi el nombre de bicicletes: ");
        nbic = sc.nextInt();
        System.out.println("Introdueixi el tipus de demanda: ");
        dem = sc.nextInt();
        System.out.println("Introdueixi el seed: ");
        seed = sc.nextInt();
        System.out.println("Introdueixi el nombre de furgonetes: ");
        f = sc.nextInt();
        System.out.println("Introdueixi el tipus de heuristica: ");
        heuristic = sc.nextInt();
        System.out.println("Introdueixi el tipus de solucio inicial: ");
        solIni = sc.nextInt();
        BicingBoard board = new BicingBoard(nest, nbic, dem, seed, f, solIni);
        
        if(heuristic == 0) { //h1
            BicingHillClimbingSearch(board);
            BicingSimulatedAnnealingSearch(board);
        } else if(heuristic == 1) { //h2
            BicingHillClimbingSearch2(board);
            BicingSimulatedAnnealingSearch2(board);
        }
    }
    
    private static void BicingHillClimbingSearch(BicingBoard board) {
        System.out.println("\nBicing HillClimbing  -->");
        try {
            Problem problem =  new Problem(board,
                                        new BicingSuccessorFunctionHC(),
                                        new BicingGoalTest(),
                                        new BicingSimpleHeuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            
            BicingBoard newBoard = (BicingBoard)search.getGoalState();
            System.out.println(newBoard.getBeneficio());
            printInstrumentation(agent.getInstrumentation());
            System.out.println();
            printActions(agent.getActions());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void BicingHillClimbingSearch2(BicingBoard board) {
        System.out.println("\nBicing HillClimbing2  -->");  
        try {
            Problem problem =  new Problem(board,
                                        new BicingSuccessorFunctionHC(),
                                        new BicingGoalTest(),
                                        new BicingCombinatedHeuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            BicingBoard newBoard = (BicingBoard)search.getGoalState();
            System.out.println(newBoard.getBeneficio());
            printInstrumentation(agent.getInstrumentation());
            System.out.println();
            printActions(agent.getActions());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void BicingSimulatedAnnealingSearch(BicingBoard board) {
        System.out.println("\nBicing SimulatedAnnealing  -->");
        try {
            Problem problem =  new Problem(board,
                                        new BicingSuccessorFunctionSA(),
                                        new BicingGoalTest(),
                                        new BicingSimpleHeuristicFunction());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(25000,100, 50, 1.0); //prueba
            SearchAgent agent = new SearchAgent(problem, search);
            BicingBoard newBoard = (BicingBoard)search.getGoalState();
            System.out.println(newBoard.getBeneficio());
            printInstrumentation(agent.getInstrumentation());
            System.out.println();
            //printActions(agent.getActions());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void BicingSimulatedAnnealingSearch2(BicingBoard board) {
        System.out.println("\nBicing SimulatedAnnealing2  -->");        
        try {
            Problem problem =  new Problem(board,
                                        new BicingSuccessorFunctionSA(),
                                        new BicingGoalTest(),
                                        new BicingCombinatedHeuristicFunction());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(25000,150,25,0.01); //prueba
            SearchAgent agent = new SearchAgent(problem, search);
            BicingBoard newBoard = (BicingBoard)search.getGoalState();
            System.out.println(newBoard.getBeneficio());
            printInstrumentation(agent.getInstrumentation());
            System.out.println();
            //printActions(agent.getActions());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}