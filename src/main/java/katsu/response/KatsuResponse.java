package katsu.response;

/**
 * Abstract base class for all Katsu response types.
 * Provides a common structure for handling user input and generating responses.
 */
public abstract class KatsuResponse {
    private final String userInput;

    /**
     * Constructs a KatsuResponse with the given user input.
     *
     * @param userInput the original input provided by the user
     */
    public KatsuResponse(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Returns the user input that triggered this response.
     *
     * @return the original user input string
     */
    public String getUserInput() {
        return this.userInput;
    }

    /**
     * Returns the response message to be displayed to the user.
     * Subclasses should override this method to provide specific response content.
     *
     * @return a response message string
     */
    public String getMessage() {
        return "";
    };

    /**
     * Returns any error message generated during response processing.
     * Subclasses should override this method to provide specific error information.
     *
     * @return an error message string
     */
    public String getError() {
        return "";
    };
}
