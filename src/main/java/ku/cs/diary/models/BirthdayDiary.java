package ku.cs.diary.models;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class BirthdayDiary {
    private Map<String, LocalDate> birthdays;

    public BirthdayDiary() {
        birthdays = new TreeMap<>();
    }

    /**
     * หาชื่อของคนที่มีอายุมากที่สุด หรือเกิดในปีค.ศ.ที่มากที่สุด
     * โดยใช้ Comparator ในรูปแบบของ Interface Callback
     * @param comparator
     * @return ชื่อของบุคคล
     */
    public String max(Comparator<LocalDate> comparator) {
        String maxName = "";
        LocalDate maxDate = null;
        //ให้ข้อมูลแรกเป็น Max
        for(String name: birthdays.keySet()){
             maxDate = this.getBirthdayFor(name);
             maxName = name;
             break;
        }
        // เทียบ Max กับทุกตัวเพื่อหา Max
        for(String name: birthdays.keySet()){
            LocalDate date = this.getBirthdayFor(name);
            if(comparator.compare(date, maxDate) > 0){
                maxDate = date;
                maxName = name;
            }
        }
        return maxName;
    }


    // -------------------------------------------------------------------------------------------------------------- //

    /**
     * เพิ่มข้อมูลวันเกิดของบุคคล
     * @param name ชื่อบุคคล
     * @param day วันเกิด (1 - 31)
     * @param month เดือนเกิด (1: มกราคม, 2: กุมภาพันธ์, ...)
     * @param year ปีค.ศ.ที่เกิด
     */
    public void addBirthday(String name, int day, int month, int year) {
        LocalDate birthday = LocalDate.of(year, month, day);
        birthdays.put(name, birthday);
    }

    /**
     * ต้องการวันเดือนปีเกิดของบุคคล
     * @param name ชื่อของบุคคล
     * @return
     */
    public LocalDate getBirthdayFor(String name) {
        return birthdays.get(name);
    }

    /**
     * ต้องการอายุของบุคคลนั้นในปี ค.ศ. ที่กำหนด
     * @param name ชื่อของบุคคล
     * @param year ปี ค.ศ.
     * @return อายุของบุคคล
     */
    public int getAgeInYear(String name, int year) {
        LocalDate birthday = this.getBirthdayFor(name);
        if (birthday == null) {
            throw new RuntimeException("Birthday Not Found for " + name);
        }
        int birthYear = birthday.getYear();
        if (year - birthYear >= 0) {
            return year - birthYear;
        }
        throw new RuntimeException("ปีค.ศ. " + year + " " + name + " ยังไม่เกิด");
    }

    /**
     * ต้องการ Set ของชื่อบุคคล ที่ในปีที่กำหนด มีอายุตามที่กำหนด
     * @param age อายุของบุคคล
     * @param year ปี ค.ศ.
     * @return Set ของชื่อบุคคล
     */
    public Set<String> getFriendsOfAgeIn(int age, int year) {
        Set<String> friends = new TreeSet<>();
        for (String name : birthdays.keySet()) {
            if (this.getBirthdayFor(name).getYear() > year) {
                continue;
            }
            if (this.getAgeInYear(name, year) == age) {
                friends.add(name);
            }
        }

        return friends;
    }

    /**
     * ต้องการ Set ของชื่อบุคคลที่เกิดในเดือนที่กำหนด
     * @param month เดือน
     * @return Set ของชื่อบุคคล
     */
    public Set<String> getBirthdaysIn(Month month) {
        Set<String> friends = new TreeSet<>();
        for (String name: birthdays.keySet()) {
            if (this.getBirthdayFor(name).getMonth().equals(month)) {
                friends.add(name);
            }
        }
        return friends;
    }

    /**
     * ต้องการ Set ของชื่อบุคคลที่เกิดในเดือนปัจจุบัน
     * @return Set ของชื่อบุคคล
     */
    public Set<String> getBirthdaysInCurrentMonth() {
        LocalDate date = LocalDate.now();
        return this.getBirthdaysIn(date.getMonth());
    }

    /**
     * ต้องการผลรวมของอายุของทุกคนในปีนี้
     * @return ผลรวมของอายุ
     */
    public int getTotalAgeInYears() {
        int total = 0;
        for (String name: birthdays.keySet()) {
            total += this.getAgeInYear(name, LocalDate.now().getYear());
        }
        return total;
    }

    public String toString(){
        String result = "";
        for(String name: birthdays.keySet()){
            LocalDate date = this.getBirthdayFor(name);
            result += name + "," + date.getDayOfMonth() + "\n";
        }
        return result;
    }
}
