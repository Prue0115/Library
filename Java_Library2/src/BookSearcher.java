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

        String target = targetCategory.toLowerCase();

        for (Book book : storage.getBooks()) {
            String category = normalizeCategory(book.getCategory());
            String normalized = category.toLowerCase();

            if (!normalized.isEmpty() && normalized.contains(target)) {
                result.add(book);
            }
        }
        return result;
    }

    private String normalizeCategory(String category) {
        if (category == null) return "";

        return category.trim();
    }
}
