package katsu.response;

/**
 * Represents a successful response from Katsu.
 * Contains a positive message to be displayed to the user.
 */
public class SuccessResponse extends KatsuResponse {
    private final String message;

    /**
     * Constructs a SuccessResponse with user input and success message.
     *
     * @param userInput the original input provided by the user
     * @param message the success message to be displayed
     */
    public SuccessResponse(String userInput, String message) {
        super(userInput);
        this.message = message;
    }

    /**
     * Returns the success message for this response.
     *
     * @return the success message string
     */
    public String getMessage() {
        return this.message;
    }
}
