package store.util.message;

public enum FileErrorMessages {
    INVALID_FILE_PATH("유효하지 않은 파일 경로거나 파일을 읽을 수 없습니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    FileErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
