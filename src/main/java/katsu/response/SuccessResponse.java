package katsu.response;

public class SuccessResponse extends KatsuResponse {
    private final String message;

    public SuccessResponse(String userInput, String message) {
        super(userInput);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
