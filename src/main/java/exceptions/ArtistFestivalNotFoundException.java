package exceptions;

public class ArtistFestivalNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ArtistFestivalNotFoundException(Integer festivalId) {
        super(String.format("Could not find artists from Festival with id %s", festivalId));
    }
}
