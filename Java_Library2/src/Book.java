public class Book {

    private final String title;
    private final String author;
    private final String publisher;
    private final String isbn;
    private final String category;

    public Book(String title, String author, String publisher,
                String isbn, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.category = category;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return  "--------------------------------------\n" +
                "             [도서 정보]\n" +
                "--------------------------------------\n" +
                "도서명 : " + title + "\n" +
                "저  자 : " + author + "\n" +
                "출판사 : " + publisher + "\n" +
                "I S B N : " + isbn + "\n" +
                "카테고리 : " + category + "\n" +
                "--------------------------------------";
    }
}
