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
            List<String> categories = splitCategories(book.getCategory());

            for (String category : categories) {
                if (category.equalsIgnoreCase(targetCategory)) {
                    result.add(book);
                    break;
                }
            }
        }
        return result;
    }

    private List<String> splitCategories(String rawCategory) {
        List<String> categories = new ArrayList<>();

        if (rawCategory == null || rawCategory.isBlank()) return categories;

        String cleaned = rawCategory
                .replace("\uFEFF", "")
                .replace("\"", "")
                .replace("'", "")
                .trim();

        if (cleaned.isEmpty()) return categories;

        String[] parts = cleaned.split("[\\|/,]\\s*|⁄|/", -1);

        for (String part : parts) {
            String candidate = part.trim();

            if (!candidate.isEmpty()) {
                categories.add(candidate);
            }
        }

        if (categories.isEmpty()) {
            categories.add(cleaned);
        }

        return categories;
    }
}
