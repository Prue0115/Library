import java.util.ArrayList;
import java.util.List;

public class BookSearcher {

    private final BookStorage storage;

    public BookSearcher(BookStorage storage) {
        this.storage = storage;
    }

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();

        if (title == null || title.trim().isEmpty()) return result;

        for (Book book : storage.getBooks()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> recommendByCollege(String college) {
        List<Book> result = new ArrayList<>();

        String targetCategory = switch (college) {
            case "공과대학", "AI·SW융합대학" -> "기술과학";
            case "인문사회대학" -> "철학";
            case "자연과학대학" -> "자연과학";
            case "예술대학" -> "예술";
            case "경상대학" -> "사회과학";
            case "보건의료과학대학" -> "기술과학";
            default -> "";
        };

        if (targetCategory.isEmpty()) return result;

        for (Book book : storage.getBooks()) {
            String category = classifyByCallNumber(book.getCallNumber());
            if (!category.isEmpty() && category.contains(targetCategory)) {
                result.add(book);
            }
        }
        return result;
    }

    private String classifyByCallNumber(String callNumber) {
        if (callNumber == null || callNumber.isBlank()) return "";

        String digits = callNumber.replaceAll("[^0-9]", "");
        digits = digits.replaceFirst("^0+", "");

        if (digits.isEmpty()) return "";

        return switch (digits.charAt(0)) {
            case '1' -> "철학";
            case '2' -> "종교";
            case '3' -> "사회과학";
            case '4' -> "자연과학";
            case '5' -> "기술과학";
            case '6' -> "예술";
            case '7' -> "어학";
            case '8' -> "문학";
            case '9' -> "역사";
            default -> "";
        };
    }
}
