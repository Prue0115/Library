import java.util.ArrayList;
import java.util.List;

public class BookStorage {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    public List<Book> getBooks() {
        return books;
    }
    // 단과대학 : 경상대학, 인문사회대학, 공과대학, 자연과학대학, 예쑬대학, 보건의료과학대학, AI·SW융합대학
    // 카테고리 : 철학, 종교, 사회과학, 자연과학, 기술과학, 예술, 어학, 문학, 역사
    public void sampleBooks() {
        books.add(new Book("데이터 통신", "Forouzan", "한빛아카데미", "004.5", "기술과학"));
        books.add(new Book("컴퓨터 네트워크", "김봉한", "한빛미디어", "004.6", "기술과학"));
        books.add(new Book("운영체제", "Silberschatz", "McGrawHill", "005.4", "기술과학"));
        books.add(new Book("철학의 이해", "김임시", "임시출판사", "100", "철학"));
        books.add(new Book("경영학 원론", "이임시", "임시출판사", "325", "경영"));
        books.add(new Book("AI 개론", "홍임시", "임시출판사", "006", "기술과학"));
    }
}
