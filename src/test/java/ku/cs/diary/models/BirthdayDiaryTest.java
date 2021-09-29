package ku.cs.diary.models;

import ku.cs.services.BirthdayDiaryFileDataSource;
import ku.cs.services.DataSource;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayDiaryTest {

    @Test
    void testAddBirthdayFromFile() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        // Kamui Kobayashi,13,9,1986
        LocalDate expected = LocalDate.of(1986, 9, 13);
        LocalDate actual = birthdayDiary.getBirthdayFor("Kamui Kobayashi");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testGetAgeInYear() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        // Kamui Kobayashi,13,9,1986
        assertEquals(14, birthdayDiary.getAgeInYear("Kamui Kobayashi", 2000));
    }

    @Test
    void testGetFriendsOfAgeIn() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        // Ricky Martin,24,12,1971
        // Mariah Carey,27,3,1971
        // Lance Armstrong,18,9,1971
        Set<String> friends = birthdayDiary.getFriendsOfAgeIn(29, 2000);
        assertEquals(3, friends.size());
        String expected = "[Lance Armstrong, Mariah Carey, Ricky Martin]";
        assertEquals(expected, friends.toString());
    }

    @Test
    void testGetBirthdaysIn() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        //David Beckham,2,5,1975
        //Ronaldinho,21,5,1980
        LocalDate date = LocalDate.of(2021, 5, 1);
        Set<String> people = birthdayDiary.getBirthdaysIn(date.getMonth());
        assertEquals(2, people.size());
        String expected = "[David Beckham, Ronaldinho]";
        assertEquals(expected, people.toString());
    }

    @Test
    void testGetBirthdaysInCurrentMonth() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        Set<String> people = birthdayDiary.getBirthdaysInCurrentMonth();
        // Current Month = 9; คนที่เกิดเดือน 9 มี 8 คน
        assertEquals(8, people.size());
    }

    @Test
    void testGetTotalAgeInYears() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        int totalAge = birthdayDiary.getTotalAgeInYears();
        // Current Year = 2021
        assertEquals(997, totalAge);
    }

    @Test
    void testWriteDataToFile() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        dataSource = new BirthdayDiaryFileDataSource("unit-test", "birthday-00.csv");
        dataSource.writeData(birthdayDiary);

        File file = new File("unit-test" + File.separator + "birthday-00.csv");
        assertTrue(file.exists());
    }

    @Test
    void testMaxAge() {
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();

        Comparator<LocalDate> ageComparator = new Comparator<LocalDate>() {
            @Override
            public int compare(LocalDate o1, LocalDate o2) {
                int year1 = o1.getYear();
                int year2 = o1.getYear();
                if(year1 < year2) return 1; // แปลว่า o1 มีอายุมากกว่า o2
                if(year1 > year2) return -1; // แปลว่า o1 มีอายุน้อยกว่า o2
                return 0;
            }
        };
        String name = birthdayDiary.max(ageComparator);
        assertEquals("Agatha Christie",name);
    }
    @Test
    void  testMaxBirthYear(){
        DataSource<BirthdayDiary> dataSource;
        dataSource = new BirthdayDiaryFileDataSource();
        BirthdayDiary birthdayDiary = dataSource.readData();
        Comparator<LocalDate> birthYearComparator = new Comparator<LocalDate>() {
            @Override
            public int compare(LocalDate o1, LocalDate o2) {
                int year1 = o1.getYear();
                int year2 = o2.getYear();
                if(year1 > year2) return 1;
                if(year1 < year2) return -1;
                return 0;
            }
        };
        String name = birthdayDiary.max(birthYearComparator);
        assertEquals("Anucha \"Jabz\" Jirawong",name);
    }
}