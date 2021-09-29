package ku.cs.services;

import ku.cs.library.models.Book;
import ku.cs.library.models.BookInventory;

import java.io.*;

public class BookInventoryFileDataSource implements DataSource<BookInventory> {
    private String directoryName;
    private String filename;

    public BookInventoryFileDataSource() {
        this("csv-data", "books.csv");
    }

    public BookInventoryFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileIfNotExist();
    }

    private void initialFileIfNotExist() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = directoryName + File.separator + filename;
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public BookInventory readData() {
        BookInventory inventory = new BookInventory();

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ( (line = buffer.readLine()) != null ) {
                String[] data = line.split(",");
                Book book = new Book(data[1], data[2],
                        Integer.parseInt(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5]));
                inventory.addBook(book);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inventory;
    }

    @Override
    public void writeData(BookInventory bookInventory) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(bookInventory.toCsv());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                buffer.close();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
