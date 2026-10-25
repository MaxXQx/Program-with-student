package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Student {
    private final String name;
    private final List<Book> books;

    public Student(String name, List<Book> books) {
        this.name = name;
        this.books = new ArrayList<>(books);
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public record Book(String title, String author, int year, int pages) {
        public Book {
            Objects.requireNonNull(title, "title");
            Objects.requireNonNull(author, "author");
        }

        // Требование(на сколько я понял): вернуть Optional от года через short-circuit / условие
        // Если год > minYearInclusive => Optional.of(year), иначе Optional.empty()
        public Optional<Integer> findYearAfter(int minYearInclusive) {
            return year > minYearInclusive ? Optional.of(year) : Optional.empty();
        }
    }

    @Override
    public String toString() {
    	String rez = "Student " + name + "\n"+  books;
        return rez;
    }
}
