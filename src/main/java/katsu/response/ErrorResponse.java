package katsu.response;

/**
 * Represents an error response from Katsu.
 * Contains an error message to be displayed to the user when an operation fails.
 */
public class ErrorResponse extends KatsuResponse {
    private final String error;

    /**
     * Constructs an ErrorResponse with user input and error message.
     *
     * @param userInput the original input provided by the user
     * @param error the error message to be displayed
     */
    public ErrorResponse(String userInput, String error) {
        super(userInput);
        this.error = error;
    }

    /**
     * Returns the error message for this response.
     *
     * @return the error message string
     */
    public String getError() {
        return this.error;
    }
}
