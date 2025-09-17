package katsu.response;

public abstract class KatsuResponse {
    private final String userInput;

    public KatsuResponse(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput() {
        return this.userInput;
    }

    public String getMessage() {
        return "";
    };

    public String getError() {
        return "";
    };
}
