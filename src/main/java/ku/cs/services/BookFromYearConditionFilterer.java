package ku.cs.services;

import ku.cs.library.models.Book;

public class BookFromYearConditionFilterer implements ConditionFilterer<Book>{
    private int fromYear;
    public BookFromYearConditionFilterer(int fromYear){
        this.fromYear = fromYear;
    }

    @Override
    public boolean check(Book book){
        return book.getPublicationYear() >= fromYear;
    }
}
