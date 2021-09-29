package ku.cs.services;

import ku.cs.diary.models.BirthdayDiary;

import java.io.*;

public class BirthdayDiaryFileDataSource implements DataSource<BirthdayDiary> {

    private String directoryName;
    private String filename;

    public BirthdayDiaryFileDataSource() {
        this("csv-data", "birthday-diary.csv");
    }

    public BirthdayDiaryFileDataSource(String directoryName, String filename) {
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
    public BirthdayDiary readData() {
        BirthdayDiary birthdayDiary = new BirthdayDiary();

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
                birthdayDiary.addBirthday(
                        data[0],
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3])
                );
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
        return birthdayDiary;
    }

    @Override
    public void writeData(BirthdayDiary birthdayDiary) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);
            buffer.write(birthdayDiary.toString());
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
