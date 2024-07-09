package exceptions;

public class FestivalGenreNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FestivalGenreNotFoundException(Integer id) {
        super(String.format("Could not find festivals from genre with id %s", id));
    }
}
