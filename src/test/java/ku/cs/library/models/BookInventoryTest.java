package ku.cs.library.models;

import ku.cs.services.BookFromYearConditionFilterer;
import ku.cs.services.BookFromYearFilterer;
import ku.cs.services.BookInventoryFileDataSource;
import ku.cs.services.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BookInventoryTest {

    @Test
    @DisplayName("ถ้าปีมากสุดเท่ากัน ให้ตอบเล่มแรกสุด")
    void testMaxBookByPublicationYear(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> yearComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if(o1.getPublicationYear() > o2.getPublicationYear()) return 1;
                if(o1.getPublicationYear() < o2.getPublicationYear()) return -1;
                return 0;
            }
        };
        Book book = bookInventory.max(yearComparator);
        String expected = "Book,คู่มือเขียนโปรแกรมด้วยภาษา Java ฉบับสมบูรณ์,อรพิน ประวัติบริสุทธิ์,2021,384,325.0";
        assertEquals(expected,book.toCsv());
    }

    @Test
    @DisplayName("ถ้าปีมากสุดเท่ากัน ให้ตอบเล่มแพงสุด")
    void testMaxBookByPublicationYear2(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> yearComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if(o1.getPublicationYear() > o2.getPublicationYear()) return 1;
                if(o1.getPublicationYear() < o2.getPublicationYear()) return -1;
                if(o1.getPrice() > o2.getPrice()) return 1;
                if(o1.getPrice() < o2.getPrice()) return -1;
                return 0;
            }
        };
        Book book = bookInventory.max(yearComparator);
        String expected = "Book,สร้างการเรียนรู้สำหรับ AI ด้วย Python Machine Learning,บัญชา ปะสีละเตสัง,2021,388,330.0";
        assertEquals(expected,book.toCsv());
    }

    @Test
    void testMaxBookByBookName(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Book book = bookInventory.max(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if (o1.getName().compareTo(o2.getName()) > 0) return 1;
                if (o1.getName().compareTo(o2.getName()) < 0) return -1;
                return 0;
            }
        });
    }

    @Test
    void testMaxBookByPages(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> pagesComparator = new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if(o1.getPages() > o2.getPages()) return 1; // หน้าของ o1 มากกว่า o2
                if(o1.getPages() < o2.getPages()) return -1; // หน้าของ o1 น้อยกว่า o2
                return 0;
            }
        };
        Book book = bookInventory.max(pagesComparator);
        assertEquals("Book,แฮร์รี่ พอตเตอร์ กับเครื่องรางยมทูต เล่ม 7 ฉบับปี 2020,J.K. Rowling (เจ.เค. โรว์ลิ่ง),2020,720,595.0",book.toCsv());
    }

    @Test
    void testMaxBookByPrice(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        Comparator<Book> priceComparator = new Comparator<Book>() {
            public int compare(Book o1, Book o2){
                if(o1.getPrice() > o2.getPrice()) return 1; // ราคาของ o1 มากกว่า o2
                if(o1.getPrice() < o2.getPrice()) return -1; // ราคาของ o1 น้อยกว่า o2
                return 0;
            }
        };
        Book book = bookInventory.max(priceComparator);
        assertEquals("Book,George's Secret Key to the Universe 1Ed.(H),Lucy Hawking & Stephen Hawking,2007,298,650.0",book.toCsv());


    }

    @Test
    void  testFilterBookFromYear(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        ArrayList<Book> filtered = bookInventory.filter(new BookFromYearFilterer(2020));
        assertEquals(6,filtered.size());
    }

    @Test
    void  testConditionFilterBookFromYear(){
        DataSource<BookInventory> dataSource;
        dataSource = new BookInventoryFileDataSource();
        BookInventory bookInventory = dataSource.readData();

        ArrayList<Book> filtered = bookInventory.filter(new BookFromYearConditionFilterer(2020));
        assertEquals(6,filtered.size());
    }

}