package IA.Bicing;

import java.util.*;

public class BicingBoard {
    
    // Atributos de la clase
    private double Beneficio;
    
    private static Estaciones Estacions;
    
    private int[][] Furgonetas;  // Fila 1 Origen, Fila 2 Est1, F3 Est2, F4 Bicis1, F5 Bicis2
    
    
    // Constructores del estado
    public BicingBoard(int nest, int nbic, int dem, int seed, int f, int ini) {
        Estacions = new Estaciones(nest, nbic, dem, seed);
        Beneficio = 0;
        Furgonetas = new int[f][5];
        
        for (int i = 0; i < f; ++i) {
            for (int j = 0; j < 3; ++j) Furgonetas[i][j] = -1;
            for (int j = 3; j < 5; ++j) Furgonetas[i][j] = 0;
        }
        if (ini == 0) solucion_inicial1();
        else solucion_inicial2();
    }

    public BicingBoard(double c, int[][] f) {
        Beneficio = c;
        Furgonetas = new int[f.length][5];
        
        // copia manual del array de furgonetas
        for (int i = 0; i < f.length; ++i) {
            for (int j = 0; j < 5; j++) Furgonetas[i][j] = f[i][j];
        }
    }
    
    
    // Getters y Setters
    public double getBeneficio() {return (Beneficio);}
    
    public void setBeneficio(double c) {Beneficio = c;}
    
    public Estaciones getEstaciones() {return (Estacions);}
    
    public int getNEstaciones() {return (Estacions.size());}
    
    public int[][] getFurgonetas() {return (Furgonetas);}
    
    public int getNFurgonetas () {return (Furgonetas.length);}
    
    public int getCoordXEst(int i) {return (Estacions.get(i).getCoordX());}
    
    public int getCoordYEst(int i) {return (Estacions.get(i).getCoordY());}
    
    
    // Soluciones iniciales
    public void solucion_inicial1() {
        int f = Furgonetas.length;
        int nest = Estacions.size();
        Random rand = new Random();

        List<Integer> secuenciaEstaciones = new ArrayList<>();
        for (int i = 0; i < nest; i++) {
            secuenciaEstaciones.add(i);
        }

        Collections.shuffle(secuenciaEstaciones); // Mezcla aleatoria de la secuencia original

        int longitudDeseada = f;
        List<Integer> secuenciaAleatoria = secuenciaEstaciones.subList(0, longitudDeseada);  // Estaciones origen de las furgonetas
        
        // Para cada furgoneta
        for (int i = 0; i < f; ++i) {
            int origen = secuenciaAleatoria.get(i);
            Furgonetas[i][0] = origen;
            
            
            // Bicicletas que puede dejar la estaci?n de origen
            int bPerDeixar = Math.max(0, Estacions.get(origen).getNumBicicletasNext()-Estacions.get(origen).getDemanda());
            bPerDeixar = Math.min(bPerDeixar, Estacions.get(origen).getNumBicicletasNoUsadas());
            bPerDeixar = Math.min(bPerDeixar, 30);
            
            
            // Estaciones destino
            for (int j = 1; j <= 2; ++j) {
                // Asigno de forma random la estaci?n de destino
                int dest = rand.nextInt(nest);
                while (dest == origen || dest == Furgonetas[i][1]) dest = rand.nextInt(nest);

                int bNecessaries = Estacions.get(dest).getNumBicicletasNext()-Estacions.get(dest).getDemanda();  // Bicis que necesita dest

                if (bNecessaries <= 0) {  // Si tiene suficientes bicis para cubrir la demando la a?ado a la ruta pero no le paso bicis
                    Furgonetas[i][j] = dest;
                    Furgonetas[i][j+2] = 0;
                }
                else {  // Si necesita bicis le paso las que necesite dest o las que pueda dejar origen y actualizo Estaciones
                    int bicis = Math.min(30, Math.min(bNecessaries, bPerDeixar));
                    Furgonetas[i][j] = dest;
                    Furgonetas[i][j+2] = bicis;
                    Estacions.get(dest).setNumBicicletasNext(Estacions.get(dest).getNumBicicletasNext()+bicis);
                    Estacions.get(origen).setNumBicicletasNext(Estacions.get(origen).getNumBicicletasNext()-bicis);
                    Beneficio += bicis;
                }
            }
        }
    }

    public void solucion_inicial2() {
        int f = Furgonetas.length;
        int nest = Estacions.size();

        ArrayList<Integer> estConExc = new ArrayList(); //estaciones con exceso
        ArrayList<Integer> estNecBic = new ArrayList(); //estaciones que les hacen falta bicis

        // Separo las estaciones con exceso de bicicletas y las que necesitan
        for (int i = 0; i < nest; ++i) {
            int excesoBicis = Estacions.get(i).getNumBicicletasNext()-Estacions.get(i).getDemanda();

            //a?ado dos valores a la vez: la estaci?n y el numero de bicis
            if (excesoBicis > 0) {
                estConExc.add(i);
                estConExc.add(excesoBicis);
            }
            else if (excesoBicis < 0) {
                estNecBic.add(i);
                estNecBic.add(-excesoBicis);
            }
        }

        int aux = Math.min(f, estConExc.size()/2);  // Sirve para comprobar que si tengo mas furgonetas que estaciones con exceso o al rev?s

        for (int i = 0; i < f-aux; i++) Furgonetas[i][0] = estNecBic.get(2*i);   //Pongo las furgonetas que sobren a estaciones que necesitan bicis

        // Furgonetas a estaciones con exceso
        for (int i = 0; i < aux; ++i) {
            int bt = Math.min(estConExc.get(2*i+1), Estacions.get(estConExc.get(2*i)).getNumBicicletasNoUsadas());
            bt = Math.min(bt, 30);  //Maxim de bicicletes que puc transportar des d'aquesta estaci?
            
            int origen = estConExc.get(2*i);
            Furgonetas[i][0] = origen;   //Guardo origen furgo
            int n = Math.min(2, estNecBic.size());   //A quantes estacions reparteixo (2 a no s? que ja no en quedin menys de 2)
            
            for (int j = 1; j <= n && bt > 0; ++j) {
                int dest = estNecBic.get(0);
                int bEstacio = estNecBic.get(1);

                Furgonetas[i][j] = dest;  //Dest?

                int bicis = Math.min(bt, bEstacio);  // Son les bicicletes que deixo a aquesta estaci?

                Furgonetas[i][j+2] = bicis;
                Estacions.get(dest).setNumBicicletasNext(Estacions.get(dest).getNumBicicletasNext()+bicis);
                Estacions.get(origen).setNumBicicletasNext(Estacions.get(origen).getNumBicicletasNext()-bicis);
                Beneficio += bicis;

                bt = bt - bicis;  // N?mero de bicis que queden despr?s de deixar-ne a la primera estaci?
                if (bicis < bEstacio) estNecBic.set(1, bEstacio-bicis);  //Si no podemos llenar toda la demanda, la siguiente furgoneta vendra aqui tmb
                else {
                    estNecBic.remove(0);
                    estNecBic.remove(0);
                }
            }
            
        }
    }

    public boolean isGoal() {
        return false; 
    }

    
    //Condiciones de aplicabilidad
    public boolean puedoCambiarOrigen(int est) {
        for (int i = 0; i < Furgonetas.length; ++i) 
            if (Furgonetas[i][0] == est) return false;
        return true;
    }
    
    public boolean puedoAnadirEstacionRuta(int f, int e) {
        if ((Furgonetas[f][1] == -1 && Furgonetas[f][0] != e) || (Furgonetas[f][2] == -1 && Furgonetas[f][0] != e && Furgonetas[f][1] != e)) return true;
        else return false;
    }
    
    public boolean puedoBorrarEstacionRuta(int f) {        
        if (Furgonetas[f][2] != -1 || Furgonetas[f][1] != -1) return true;
        else return false; 
    }


    // Operadores
    public void cambiarOrigen(int est, int f) {
        int first = Furgonetas[f][1];
        int second = Furgonetas[f][2];
        
        // la furgoneta te la ruta amb 2 estacions -> treus les dues estacions
        if (Furgonetas[f][1] != -1 && Furgonetas[f][2] != -1) {
            borrarEstacionRuta(f); 
            borrarEstacionRuta(f);
            Furgonetas[f][0] = est;
            anadirEstacionRuta(first, f);
            anadirEstacionRuta(second, f);
        }
        
        // la furgoneta nomes te 1 estacio asignada a la ruta
        else if (Furgonetas[f][1] != -1 && Furgonetas[f][2] == -1) {
            borrarEstacionRuta(f);
            Furgonetas[f][0] = est;
            anadirEstacionRuta(first, f);
        }
        
        else {
            Furgonetas[f][0] = est;
        }
    }

    public void anadirEstacionRuta(int est, int f) {
        if (Furgonetas[f][1] == -1) {
            Furgonetas[f][1] = est;
            int b = Math.min(30, Estacions.get(Furgonetas[f][0]).getNumBicicletasNoUsadas());
            int c = Math.max(0, Estacions.get(Furgonetas[f][1]).getDemanda() - Estacions.get(Furgonetas[f][1]).getNumBicicletasNext());
            int bicis = Math.min(b,c);      
            Furgonetas[f][3] = bicis;
            Estacions.get(est).setNumBicicletasNext(Estacions.get(est).getNumBicicletasNext()+bicis);
        }
        else {
            Furgonetas[f][2] = est;
            int b = Math.min(30-Furgonetas[f][3], Estacions.get(Furgonetas[f][0]).getNumBicicletasNoUsadas());
            int c = Math.max(0, Estacions.get(Furgonetas[f][2]).getDemanda() - Estacions.get(Furgonetas[f][2]).getNumBicicletasNext());
            int bicis = Math.min(b,c);   
            Furgonetas[f][4] = bicis;
            Estacions.get(est).setNumBicicletasNext(Estacions.get(est).getNumBicicletasNext()+bicis);
        }
    }

    public void borrarEstacionRuta(int f) {
        if (Furgonetas[f][2] != -1) {
            Estacions.get(Furgonetas[f][2]).setNumBicicletasNext(Estacions.get(Furgonetas[f][2]).getNumBicicletasNext()-Furgonetas[f][4]);
            Estacions.get(Furgonetas[f][0]).setNumBicicletasNoUsadas(Estacions.get(Furgonetas[f][0]).getNumBicicletasNoUsadas()+Furgonetas[f][4]);
            Furgonetas[f][2] = -1;
            Furgonetas[f][4] = 0;
        }
        else if (Furgonetas[f][1] != -1) {
            Estacions.get(Furgonetas[f][1]).setNumBicicletasNext(Estacions.get(Furgonetas[f][1]).getNumBicicletasNext()-Furgonetas[f][3]);
            Estacions.get(Furgonetas[f][0]).setNumBicicletasNoUsadas(Estacions.get(Furgonetas[f][0]).getNumBicicletasNoUsadas()+Furgonetas[f][3]);
            Furgonetas[f][1] = -1;
            Furgonetas[f][3] = 0;
        }
        else Furgonetas[f][0] = -1;
    }
}
