import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            BookStorage storage = new BookStorage();
            BookSearcher searcher = new BookSearcher(storage);

            while (true) {
                System.out.println("--------------------------------------");
                System.out.println("    도서 검색/단과대학별 도서 추천");
                System.out.println("--------------------------------------");
                System.out.println("1. 도서 검색");
                System.out.println("2. 단과대학별 도서 추천");
                System.out.println("3. 도서 추가");
                System.out.println("4. 종료");
                System.out.println("--------------------------------------");
                System.out.print("메뉴를 선택하세요 : ");

                String menu = sc.nextLine();

                switch (menu) {
                    case "1" -> {
                        System.out.print("도서명: ");
                        String title = sc.nextLine();

                        List<Book> results = searcher.searchByTitle(title);

                        if (results.isEmpty()) {
                            System.out.println("검색 결과가 없습니다.");
                        } else {
                            for (Book book : results) {
                                System.out.println(book);
                            }
                        }
                    }

                    case "2" -> {

                        String college = "";

                        while (college.isEmpty()) {
                            System.out.println("--------------------------------------");
                            System.out.println("단과대학을 선택하세요");
                            System.out.println("1. 경상대학         5. 예술대학");
                            System.out.println("2. 인문사회대학      6. 보건의료과학대학");
                            System.out.println("3. 공과대학         7. AI·SW융합대학");
                            System.out.println("4. 자연과학대학");
                            System.out.println("--------------------------------------");
                            System.out.print("선택: ");

                            String input = sc.nextLine();

                            college = switch (input) {
                                case "1" -> "경상대학";
                                case "2" -> "인문사회대학";
                                case "3" -> "공과대학";
                                case "4" -> "자연과학대학";
                                case "5" -> "예술대학";
                                case "6" -> "보건의료과학대학";
                                case "7" -> "AI·SW융합대학";
                                default -> {
                                    System.out.println("잘못된 입력입니다.");
                                    yield "";
                                }
                            };
                        }

                        List<Book> results = searcher.recommendByCollege(college);

                        if (results.isEmpty()) {
                            System.out.println("등록된 단과대학 추천 도서가 없습니다.");
                            break;
                        }

                        int currentPage = 1;
                        int totalPages = (results.size() + 4) / 5;

                        while (true) {
                            System.out.println("--------------------------------------");
                            System.out.println("      [단과대학별 추천 도서 목록]");
                            System.out.println("--------------------------------------");

                            for (int i = 0; i < 5; i++) {
                                int index = (currentPage - 1) * 5 + i;

                                if (index < results.size()) {
                                    System.out.println((i + 1) + ". " + results.get(index).getTitle());
                                }
                            }

                            System.out.println("--------------------------------------");
                            System.out.println("6. 메뉴 이동");
                            System.out.println("7. 다음 페이지 (" + currentPage + "/" + totalPages + ")");
                            System.out.println("8. 이전 페이지 (" + currentPage + "/" + totalPages + ")");
                            System.out.println("--------------------------------------");
                            System.out.print("선택: ");

                            String choice = sc.nextLine();

                            if (choice.equals("6")) break;
                            else if (choice.equals("7") && currentPage < totalPages) currentPage++;
                            else if (choice.equals("8") && currentPage > 1) currentPage--;
                        }
                    }
                    case "3" -> {
                        System.out.println("카테고리 : 철학, 종교, 사회과학, 자연과학, 기술과학, 예술, 어학, 문학, 역사");
                        System.out.print("도서명: ");
                        String title = sc.nextLine();

                        System.out.print("저자: ");
                        String author = sc.nextLine();

                        System.out.print("출판사: ");
                        String publisher = sc.nextLine();

                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();

                        System.out.print("카테고리: ");
                        String category = sc.nextLine();
                        System.out.println("--------------------------------------");

                        storage.addBook(new Book(title, author, publisher, isbn, category));
                        System.out.println("도서가 저장되었습니다.");
                    }

                    case "4" -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }

                    default -> System.out.println("잘못된 입력입니다.");
                }
            }
        }
    }
}
