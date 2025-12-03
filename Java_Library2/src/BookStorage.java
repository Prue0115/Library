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

    private static final String HEADER = "title,author,publisher,isbn,callNumber";

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

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);

                    if (parts.length < 5) continue;

                    books.add(new Book(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4]
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
                            sanitize(book.getCallNumber())));
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

    private static Path resolveDefaultCsvPath() {
        Path workingDirCsv = Paths.get("books.csv");
        if (Files.exists(workingDirCsv)) {
            return workingDirCsv;
        }

        Path projectRootCsv = Paths.get("Java_Library2", "books.csv");
        if (Files.exists(projectRootCsv)) {
            return projectRootCsv;
        }

        return workingDirCsv;
    }

    private String sanitize(String value) {
        return value == null ? "" : value.replace(",", " ");
    }
}
