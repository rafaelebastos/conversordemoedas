import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;

public class ConversorDeMoedas {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/09219ac68b4c29404dd96094/latest/USD";

    public static Map<String, Double> obterTaxasDeConversao() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        // Usar um Map<String, Double> para maior segurança de tipo
        Map<String, Double> taxasDeConversao = new HashMap<>();
        for (Map.Entry<String, com.google.gson.JsonElement> entry : conversionRates.entrySet()) {
            taxasDeConversao.put(entry.getKey(), entry.getValue().getAsDouble());
        }

        return taxasDeConversao;
    }

    public static double converterMoeda(double valor, String moedaOrigem, String moedaDestino) throws IOException, InterruptedException {
        Map<String, Double> taxasDeConversao = obterTaxasDeConversao();

        if (!taxasDeConversao.containsKey(moedaOrigem) || !taxasDeConversao.containsKey(moedaDestino)) {
            throw new IllegalArgumentException("Moeda de origem ou destino inválida.");
        }

        double taxaOrigem = taxasDeConversao.get(moedaOrigem);
        double taxaDestino = taxasDeConversao.get(moedaDestino);

        // Calcula o valor convertido
        return (valor / taxaOrigem) * taxaDestino;
    }

    public static void main(String[] args) {
        try {
            double valor = 100;
            String moedaOrigem = "USD";
            String moedaDestino = "EUR";

            double valorConvertido = converterMoeda(valor, moedaOrigem, moedaDestino);
            System.out.println(valor + " " + moedaOrigem + " equivalem a " + valorConvertido + " " + moedaDestino);
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao converter moeda: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

