import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorMoneda {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("***************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
            System.out.println("1) Dólar => Peso argentino");
            System.out.println("2) Peso argentino => Dólar");
            System.out.println("3) Dólar => Real brasileño");
            System.out.println("4) Real brasileño => Dólar");
            System.out.println("5) Dólar => Peso colombiano");
            System.out.println("6) Peso colombiano => Dólar");
            System.out.println("7) Salir");
            System.out.println("***************************************");
            System.out.print("Elija una opción válida: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1 -> convertir("USD", "ARS", scanner);
                case 2 -> convertir("ARS", "USD", scanner);
                case 3 -> convertir("USD", "BRL", scanner);
                case 4 -> convertir("BRL", "USD", scanner);
                case 5 -> convertir("USD", "COP", scanner);
                case 6 -> convertir("COP", "USD", scanner);
                case 7 -> System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 7);

        scanner.close();
    }
public static void convertir(String monedaOrigen, String monedaDestino, Scanner scanner) throws IOException, InterruptedException {
    System.out.println("Ingrese el valor que desea convertir:  ");
    double cantidad = scanner.nextDouble();
    scanner.nextLine();

    String apiKey = "94afa6db09b320931d7500ef";  // Reemplaza por tu clave real
    String direccion = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaOrigen;

    HttpClient client =  HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(direccion)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


    JSONObject json = new JSONObject(response.body());
    if(json.getString("result").equals("success")){

        JSONObject conversionRates = json.getJSONObject("conversion_rates");
        double tasa = conversionRates.getDouble(monedaDestino);


        double resultado = cantidad * tasa;


        System.out.printf("El valor de %s: %.2f %s equivalen a %.2f %s%n",
                monedaOrigen, cantidad, monedaOrigen, resultado, monedaDestino);

    } else {
        System.out.println("No se pudo obtener la tasa de conversión. Verifique las monedas o su API Key.");
    }
}

}
