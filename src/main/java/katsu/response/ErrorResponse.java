package katsu.response;

public class ErrorResponse extends KatsuResponse {
    private final String error;

    public ErrorResponse(String userInput, String error) {
        super(userInput);
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
