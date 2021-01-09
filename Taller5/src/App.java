import com.ucab.taller5.Chain;
import com.ucab.taller5.Console;

public class App {
    public static void main(String[] args) {
        Chain chain = new Chain("");
        while (true) {
            Console.clear();
            System.out.println("1. Ingresar cadena de caracteres");
            System.out.println("2. Contar I y A que contiene la cadena");
            System.out.println("3. Salir");
            System.out.println();
            
            String op = System.console().readLine("Opcion: ");
            System.out.println();
            boolean sigExit = false;
            switch (op) {
                case "1":
                    chain = new Chain(System.console().readLine("Cadena: "));
                    System.out.println(chain.value);
                    break;
                case "2":
                    if (chain.value == "") {
                        System.out.println("> Cadena indefinida");
                        break;
                    }
                    System.out.println("Cadena: " + chain.value);
                    int Is = chain.countChar('i');
                    int As = chain.countChar('a');
                    System.out.println("Letras 'i': " + Is);
                    System.out.println("Letras 'a': " + As);
                    break;
                case "3":
                    System.out.println("> Bye");
                    sigExit = true;
            }
            System.out.println();
            if (sigExit) break;
        }
    }
}
