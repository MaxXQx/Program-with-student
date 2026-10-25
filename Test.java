package Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static List<Student> loadStudents(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        List<Student> students = new ArrayList<>();

        String currentStudentName = null;
        List<Student.Book> currentBooks = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("Student:")) {
                if (currentStudentName != null) {
                    students.add(new Student(currentStudentName, currentBooks));
                    currentBooks = new ArrayList<>();
                }

                currentStudentName = line.substring("Student:".length()).trim();
            } else if (line.startsWith("Book:")) {


                String data = line.substring("Book:".length()).trim();
                // ожидаем: title; author; year; pages
                String[] parts = data.split(";");

                String title = parts[0].trim();
                String author = parts[1].trim();
                int year = Integer.parseInt(parts[2].trim());
                int pages = Integer.parseInt(parts[3].trim());

                currentBooks.add(new Student.Book(title, author, year, pages));
            }
        }

        // сохранить последнего студента
        if (currentStudentName != null) {
            students.add(new Student(currentStudentName, currentBooks));
        }

        return students;
    }

    public static void main(String[] args) throws Exception {
        Path path = Path.of("D:\\перенести\\java\\11.txt"); 
        List<Student> students = loadStudents(path);

        System.out.println("Студентов: " + students.size());

        students.stream()
	        .map(s -> { System.out.println(s); return s; })
	        .flatMap(s -> s.getBooks().stream())
	        .sorted((b1, b2) -> Integer.compare(b1.pages(), b2.pages()))
	        .distinct()
	        .filter(b -> b.year() > 2000)
	        .limit(3)
	        .map(b -> b.findYearAfter(2000))
	        .forEach(opt -> System.out.println(opt.map(y -> String.valueOf(y)).orElse("Такая книга отсутствует")
        ));
    }
}
