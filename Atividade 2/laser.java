import java.util.Scanner;

public class laser{
    public static void main(String[]args) {
        Scanner  scanner = new Scanner(System.in);

        while (true) {
            
            int A = scanner.nextInt();
            int C = scanner.nextInt();
            if (A == 0 && C == 0) break; 

            int[] alturas = new int[C];
            for (int i = 0; i < C; i++) {
                alturas[i] = scanner.nextInt();
            }

            int laserLigado = 0;
            int alturaAtual = A; 

            for (int i = 0; i < C; i++) {
                if (alturaAtual > alturas[i]) {
                    laserLigado += (alturaAtual - alturas[i]); 
                }

                alturaAtual = alturas[i]; 
            }

            System.out.println(laserLigado);
        }

        scanner.close();
    }
}