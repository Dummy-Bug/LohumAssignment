package Lohum.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceApi {

    @GetMapping("/price")
    public ResponseEntity<Object> getPrice() {
        String url = "https://www.metal.com/Lithium-ion-Battery/202303240001";
        try {
            // Fetch the page using JSoup
            Document doc = Jsoup.connect(url).get();
            // Find the price element using the ID selector and class selector
            Elements div = doc.select(".priceContent___3lf_D " +
                    ".row___24FL9:nth-child(1)" +
                    " .block___2RfAT:nth-child(1)");
            String price = div.select("span:nth-child(2)").text();
            return ResponseEntity.ok("The Current Price is " + price);
        } catch (Exception e) {
            // Handle any errors that may occur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the price.");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing the request.");
    }
}
