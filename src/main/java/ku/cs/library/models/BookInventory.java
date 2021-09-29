package ku.cs.library.models;

import ku.cs.services.ConditionFilterer;
import ku.cs.services.Filterer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class BookInventory {
    private ArrayList<Book> books;

    public BookInventory() {
        books = new ArrayList<>();
    }

    /**
     * ต้องการหาหนังสือปีที่พิมพ์ล่าสุด หรือมีชื่ออยู่หลังสุด หรือมีจำนวนหน้ามากที่สุด หรือมีราคามากที่สุด
     * ขึ้นกับว่าใช้ Comparator ใด
     * @param bookComparator callback comparator
     * @return Book object
     */
    public Book max(Comparator<Book> bookComparator)
    {
        if (books.isEmpty()) return null;
        Book max = books.get(0); // เปรียบเทียบเก็บค่ามาก
        for (Book book: this.books){
            if(bookComparator.compare(book, max) > 0){
                max = book;
            }
        }
        return max;
    }

    /**
     * ต้องการเรียงลำดับหนังสือจากชื่อหนังสือ หรือชื่อผู้แต่ง หรือปีที่พิมพ์ หรือจำนวนหน้า หรือราคา จากน้อยไปมากหรือมากไปน้อยก็ได้
     * ขึ้นกับว่าใช้ Comparator ใด
     *
     * @param bookComparator callback comparator
     */
    public void sort(Comparator<Book> bookComparator)
    {
        Collections.sort(books, bookComparator);
    }

    /**
     * ต้องการค้นหารายการหนังสือที่มีปีที่พิมพ์ตั้งแต่ปีที่กำหนดถึงปัจจุบัน หรือชื่อหนังสือมีคำที่กำหนด หรือชื่อผู้แต่งคือชื่อที่กำหนด หรือมีจำนวนหน้าหรือราคาอยู่ในช่วงที่กำหนด
     * ขึ้นกับว่าเรียก Filterer ใด
     * @param filterer callback filterer
     * @return ArrayList ของหนังสือตามเงื่อนไขของ Filterer
     */
    public ArrayList<Book> filter(Filterer<BookInventory> filterer) {
        return filterer.filter(this).getBooks();
    }
    public ArrayList<Book> filter(ConditionFilterer<Book> bookConditionFilterer){
        ArrayList<Book> filtered = new ArrayList<>();
        for (Book book: this.books){
            if(bookConditionFilterer.check(book)){
                filtered.add(book);
            }
        }
        return filtered;
    }

    // -------------------------------------------------------------------------------------------------------------- //

    public void addBook(Book book) {
        this.books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public int count() {
        return this.books.size();
    }

    public String toCsv() {
        String result = "";
        for (Book book : this.books) {
            result += book.toCsv() + "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return "BookInventory{" +
                "books=" + books +
                '}';
    }
}
