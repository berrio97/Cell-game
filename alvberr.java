/* Berrio Galindo, Alvaro */
/* Garcia Vazquez, Sara */
import java.util.Scanner;
 
public class alvberr {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] matriz = new int[8][8];
        int nivel = 5;
        int contador = 0; // numero de golpes dados
        int[][] matrizcopia = new int[8][8]; // permite recomenzar el nivel
        generapuntos(nivel, matriz, contador); /* genera los puntos aleatorios del nivel*/
        matrizcopia = copiarmatriz(matriz); /* crea una copia de la matriz*/
        impmatriz(matriz, nivel); /* imprime en pantalla el tablero*/
        golpes(matriz, matrizcopia, nivel, contador, in); /* realiza los golpes en la matriz o los casos del menu*/
    }
 
    public static int cambiarnivel(Scanner in) { /* permite el cambio de nivel al introducir 0 4*/
        System.out.println("Elige un nivel:");
        int nivel = in.nextInt();
        while (nivel > 9 || nivel < 1) {
            System.out.println("Error, escribir un numero del 1 al 9.");
            nivel = in.nextInt();
        }
        return nivel;
 
    }
 
    public static void generapuntos(int nivel, int matriz[][], int contador) {
        for (int f = 0; f < nivel * 3; f++) {
            int c = (int) (Math.random() * 6) + 1;
            int d = (int) (Math.random() * 6) + 1;
            incrementar(matriz, c, d);
        }
    }
 
    public static void incrementar(int matriz[][], int c, int d) { /* aumenta en una unidad las casillas del tablero los puntos*/
        matriz[c][d] = (matriz[c][d] + 1) % 4;
        matriz[c + 1][d] = (matriz[c + 1][d] + 1) % 4;
        matriz[c][d + 1] = (matriz[c][d + 1] + 1) % 4;
        matriz[c - 1][d] = (matriz[c - 1][d] + 1) % 4;
        matriz[c][d - 1] = (matriz[c][d - 1] + 1) % 4;
    }
 
    public static void impmatriz(int matriz[][], int nivel) {
        System.out.println(
                "Recomenzar ( 0 1 )  Nuevo ( 0 2 )  Calificacion ( 0 3 )  Cambiar nivel ( 0 4 )  Salir (0 -2)");
        System.out.println("Un golpe decrementara el valor de ese casilla en 1, y tambien los valores de sus 4 vecinas."
                        + "\n" + "Objetivo: Dejar todos las casillas en '0'");
        for (int x = 1; x < matriz.length - 1; x++) { /* crea el tablero*/
            System.out.print("\t" + "\t");
            System.out.print("|");
            for (int y = 1; y < matriz[x].length - 1; y++) {
                System.out.print(matriz[x][y]);
                if (y != matriz[x].length - 2)
                    System.out.print("  ");
            }
            System.out.println("|");
        }
        switch (nivel) { // imprime el nivel de juego
        case 1:
            System.out.print("Tirado(3 golpes)" + "\t" + "\t");
            break;
        case 2:
            System.out.print("Facilisimo(6 golpes)" + "\t" + "\t");
            break;
        case 3:
            System.out.print("Muy Facil(9 golpes)" + "\t" + "\t");
            break;
        case 4:
            System.out.print("Facil(12 golpes)" + "\t" + "\t");
            break;
        case 5:
            System.out.print("Normal(15 golpes)" + "\t" + "\t");
            break;
        case 6:
            System.out.print("Dificil(18 golpes)" + "\t" + "\t");
            break;
        case 7:
            System.out.print("Muy dificil(21 golpes)" + "\t" + "\t");
            break;
        case 8:
            System.out.print("Maestro(24 golpes)" + "\t" + "\t");
            break;
        case 9:
            System.out.print("Imposible(27 golpes)" + "\t" + "\t");
            break;
        }
    }
 
    public static void golpes(int matriz[][], int matrizcopia[][], int nivelito, int contador, Scanner in) {
        int[][] matrizJuego = new int[8][8];
        matrizJuego = copiarmatriz(matriz);
        int[][] matrizCopia = new int[8][8];
        matrizCopia = copiarmatriz(matrizcopia);
        int nivel = nivelito;
        int indicador = 0;
        do {
            System.out.println("Numero de golpes:" + contador);
            System.out.print("Golpe (fila, columna):");
            int fila = in.nextInt();
            int col = in.nextInt();
            while (fila > 6 || col > 6 || fila < 0 || col < -2 || col == -1) { /* caso en el que los datos sean erroneos*/
                System.out.println("Error. Introduzca un numero entre 1 y 6.");
                fila = in.nextInt();
                col = in.nextInt();
            }
            if (fila == 0) {
                switch (col) {
                case 1: // recomenzar el juego
                    contador=0;
                    matrizJuego = copiarmatriz(matrizCopia);
                    impmatriz(matrizJuego, nivel);
                    break;
                case 2: // nuevo juego
                    cerosmatriz(matrizJuego);
                    contador = 0;
                    generapuntos(nivel, matrizJuego, contador);
                    matrizCopia = copiarmatriz(matrizJuego);
                    impmatriz(matrizJuego, nivel);
                    break;
                case 3: // calificaciones
                	break;
                     
                case 4: // cambia de nivel
                    contador = 0;
                    cerosmatriz(matrizJuego);
                    nivel = cambiarnivel(in);
                    generapuntos(nivel, matrizJuego, contador);
                    matrizCopia = copiarmatriz(matrizJuego);
                    impmatriz(matrizJuego, nivel);
                    break;
                case (-2): /* llena la matriz de ceros para salir de golpes y termina*/
                    cerosmatriz(matrizJuego);
                    indicador = 1;
                    break;
                }
            } else { // realiza el golpe
                decrementar(matrizJuego, fila, col);
                impmatriz(matrizJuego, nivel);
                contador++;
            }
        } while (comprobar(matrizJuego) != true); /* comprueba que la matriz sea de ceros*/
        if (indicador == 1)
            ;
        else {
            puntuaciones(nivel, contador); /* imprime las puntuaciones del nivel en pantalla*/
            contador = 0;
            cerosmatriz(matrizJuego);
            jugarotravez(matrizJuego,matrizCopia,contador,nivel,in);
        }
    }
    	
    
public static void jugarotravez(int[][]matrizJuego,int[][]matrizCopia,int contador,int nivel,Scanner in){
	System.out.println("Quieres volver a jugar? Introduce 1 si quieres volver a jugar o -1 para salir.");
    int jugardenuevo = in.nextInt(); /* permite elegir entre volver a jugar o salir del juego*/
    while ((jugardenuevo != -1) && (jugardenuevo != 1)) { /* comprueba que jugardenuevo no sea erroneo*/
                                                         
        System.out.println("Error. Introduce otra vez el numero,");
        jugardenuevo = in.nextInt();
    }
    if (jugardenuevo == 1) { // se comienza el juego
        generapuntos(nivel, matrizJuego, contador);
        matrizCopia = copiarmatriz(matrizJuego);
        impmatriz(matrizJuego, nivel);
        golpes(matrizJuego, matrizCopia, nivel, contador, in);
    } else { // sale del juego
        if (jugardenuevo == -1)
            ;
        else
            ;
    }
}
 
    public static void decrementar(int[][] matriz, int c, int d) {/* disminuye en una unidad las casillas del tablero correspondientes al golpe dado*/
                                                                     
        if (matriz[c][d] - 1 == -1)
            matriz[c][d] = 3;
        else
            matriz[c][d] = (matriz[c][d] - 1);
        if (matriz[c + 1][d] - 1 == -1)
            matriz[c + 1][d] = 3;
        else
            matriz[c + 1][d] = (matriz[c + 1][d] - 1);
        if (matriz[c][d + 1] - 1 == -1)
            matriz[c][d + 1] = 3;
        else
            matriz[c][d + 1] = (matriz[c][d + 1] - 1);
        if (matriz[c - 1][d] - 1 == -1)
            matriz[c - 1][d] = 3;
        else
            matriz[c - 1][d] = (matriz[c - 1][d] - 1);
        if (matriz[c][d - 1] - 1 == -1)
            matriz[c][d - 1] = 3;
        else
            matriz[c][d - 1] = (matriz[c][d - 1] - 1);
    }
 
    public static boolean comprobar(int[][] matriz) {
        for (int x = 1; x < matriz.length - 1; x++) {
            for (int y = 1; y < matriz.length - 1; y++) {
                if (matriz[x][y] != 0)
                    return false;
            }
        }
        return true;
    }
 
    public static int[][] copiarmatriz(int[][] matriz) {
        int[][] matrizcopia = new int[8][8];
        for (int x = 1; x < matriz.length - 1; x++) {
            for (int y = 1; y < matriz.length - 1; y++)
                matrizcopia[x][y] = matriz[x][y];
        }
        return matrizcopia;
    }
 
    public static void cerosmatriz(int[][] matriz) {
        for (int j = 1; j < matriz.length - 1; j++) {
            for (int i = 1; i < matriz.length; i++)
                matriz[i][j] = 0;
        }
    }
 
    public static void puntuaciones(int nivel, int contador) {
        int golpes = nivel * 3;
        if (contador < golpes) {
            System.out.println("Extraordinariamente bien: Hecho en " + contador + " golpes");
        } else {
            if (contador == golpes) {
                System.out.println("Perfecto. Hecho en " + contador + " golpes.");
            } else {
                System.out.println("Hecho en " + contador + " golpes.");
            }
        }
    }
}