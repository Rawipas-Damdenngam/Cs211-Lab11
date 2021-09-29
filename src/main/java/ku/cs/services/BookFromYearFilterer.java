package ku.cs.services;

import ku.cs.library.models.Book;
import ku.cs.library.models.BookInventory;

public class BookFromYearFilterer implements  Filterer<BookInventory>{
    private int fromYear;
    public BookFromYearFilterer(int fromYear){
        this.fromYear = fromYear;
    }
    @Override
    public BookInventory filter(BookInventory bookInventory) {
        BookInventory filtered = new BookInventory();
        for(Book book: bookInventory.getBooks()){
            if(book.getPublicationYear() >= fromYear){
                filtered.addBook(book);
            }
        }
        return filtered;
    }
}
