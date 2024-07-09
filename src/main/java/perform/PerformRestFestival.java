package perform;

import domain.Artist;
import domain.Festival;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.IntStream;

public class PerformRestFestival {
    private final String SERVER_URI = "http://localhost:8080";
    private WebClient webClient = WebClient.create();

    public PerformRestFestival() {
        IntStream.rangeClosed(1,9).forEach(number -> {
            try {
                System.out.printf("------- GET ARTIST FESTIVAL %d ------- %n", number);
                getArtistFestival(number);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        IntStream.rangeClosed(1,6).forEach(number -> {
            try {
                System.out.printf("------- GET FESTIVALS GENRE %d ------- %n", number);
                getFestivalsGenre(number);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void getArtistFestival(int number) {
        webClient.get()
                .uri(SERVER_URI + "/artist/festival/" + number)
                .retrieve()
                .bodyToMono(Artist.class)
                .doOnSuccess(this::printArtistData)
                .block();
    }

    private void printArtistData(Artist artist) {
        System.out.printf("ID=%s, Name=%s", artist.getArtistid(), artist.getName());
    }

    private void getFestivalsGenre(int number) {
        webClient.get()
                .uri(SERVER_URI + "/festival/genre/" + number)
                .retrieve()
                .bodyToMono(Festival.class)
                .doOnSuccess(this::printFestivalData)
                .block();
    }

    private void printFestivalData(Festival festival) {
        System.out.printf("ID=%s, Festival=%s", festival.getFestivalid(), festival.getName());
    }
}
