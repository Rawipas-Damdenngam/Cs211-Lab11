package ku.cs.library.models;

import java.time.LocalDate;

public class Book {
    private String name; // ชื่อหนังสือ
    private String author; // ชื่อผู้แต่ง
    private int publicationYear; // ปีที่พิมพ์
    private int pages; // จำนวนหน้า
    private double price; // ราคา (บาท)

    public Book(String name, String author, int publicationYear, int pages, double price) {
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.price = price;
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getPages() {
        return pages;
    }

    public double getPrice() {
        return price;
    }

    public String toCsv() {
        return "Book," + name + "," + author + "," + publicationYear + "," + pages + "," + price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                ", price=" + price +
                '}';
    }
}
