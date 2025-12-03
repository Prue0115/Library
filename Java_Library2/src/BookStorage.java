import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookStorage {

    private static final String HEADER = "title,author,publisher,isbn,category";

    private final Path csvPath;
    private final List<Book> books = new ArrayList<>();

    public BookStorage() {
        this(resolveDefaultCsvPath().toString());
    }

    public BookStorage(String csvFilePath) {
        this.csvPath = Paths.get(csvFilePath);
        loadFromCsv();
    }

    public void addBook(Book book) {
        if (book == null) return;

        books.add(book);
        saveToCsv();
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    private void loadFromCsv() {
        books.clear();

        try {
            if (Files.notExists(csvPath)) {
                initializeFile();
                return;
            }

            try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
                String line = reader.readLine(); // header
                if (line == null) return;

                String[] headerParts = line.split(",", -1);
                String lastColumn = headerParts.length >= 5 ? headerParts[4].trim().toLowerCase() : "category";
                boolean hasCategoryColumn = lastColumn.equals("category");
                boolean hasCallNumberColumn = lastColumn.equals("callnumber") || lastColumn.equals("청구기호");

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split(",", -1);

                    if (parts.length < 5) continue;

                    for (int i = 0; i < 5; i++) {
                        parts[i] = parts[i].trim();
                    }

                    String category = parts[4];

                    if (!hasCategoryColumn && hasCallNumberColumn) {
                        category = classifyByCallNumber(category);
                    }

                    books.add(new Book(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            category
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load books from CSV", e);
        }
    }

    private void saveToCsv() {
        try {
            if (Files.notExists(csvPath)) {
                initializeFile();
            }

            try (BufferedWriter writer = Files.newBufferedWriter(csvPath)) {
                writer.write(HEADER);
                writer.newLine();

                for (Book book : books) {
                    writer.write(String.join(",",
                            sanitize(book.getTitle()),
                            sanitize(book.getAuthor()),
                            sanitize(book.getPublisher()),
                            sanitize(book.getIsbn()),
                            sanitize(book.getCategory())));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save books to CSV", e);
        }
    }

    private void initializeFile() throws IOException {
        if (csvPath.getParent() != null) {
            Files.createDirectories(csvPath.getParent());
        }
        Files.write(csvPath, (HEADER + System.lineSeparator()).getBytes());
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

    private static Path resolveDefaultCsvPath() {
        Path current = Paths.get("").toAbsolutePath();

        while (current != null) {
            Path candidate = current.resolve("books.csv");
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }

        return Paths.get("books.csv");
    }

    private String sanitize(String value) {
        return value == null ? "" : value.replace(",", " ");
    }
}
