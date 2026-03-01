package motorph_oop.dao;

import motorph_oop.util.Constants;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class AttendanceDAO {

    // GET ATTENDANCE BY EMPLOYEE & MONTH

    public List<Object[]> getAttendanceByEmployeeAndMonth(
            String empNo,
            String selectedMonth,
            double hourlyRate) {

        List<Object[]> rows = new ArrayList<>();

        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        DateTimeFormatter timeFormat =
                DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);

        try (Scanner scanner =
                     new Scanner(new File(Constants.ATTENDANCE_CSV), "UTF-8")) {

            scanner.nextLine(); // ⏭ Skip header

            while (scanner.hasNextLine()) {

                String[] parts =
                        scanner.nextLine().split(",", -1);

                if (parts.length >= 6 &&
                    parts[0].trim().equals(empNo)) {

                    LocalDate date =
                            LocalDate.parse(parts[3].trim(), dateFormat);

                    String monthYear =
                            date.getMonth()
                                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                                + " " + date.getYear();

                    if (!"All".equals(selectedMonth)
                            && !monthYear.equals(selectedMonth))
                        continue;

                    LocalTime in =
                            LocalTime.parse(parts[4].trim(), timeFormat);

                    LocalTime out =
                            parts[5].trim().isEmpty()
                                    ? LocalTime.now()
                                    : LocalTime.parse(parts[5].trim(), timeFormat);

                    double hours =
                            Duration.between(in, out).toMinutes() / 60.0;

                    double dailySalary =
                            hours * hourlyRate;

                    rows.add(new Object[]{
                            parts[3],
                            parts[4],
                            parts[5],
                            hours,
                            dailySalary
                    });
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Attendance load error", e);
        }

        return rows;
    }

    // GET AVAILABLE MONTHS

    public List<String> getAvailableMonths(String empNo) {

        List<String> months = new ArrayList<>();

        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

        try (Scanner scanner =
                     new Scanner(new File(Constants.ATTENDANCE_CSV), "UTF-8")) {

            scanner.nextLine(); // ⏭ Skip header

            while (scanner.hasNextLine()) {

                String[] parts =
                        scanner.nextLine().split(",", -1);

                if (parts.length >= 6 &&
                    parts[0].trim().equals(empNo)) {

                    LocalDate date =
                            LocalDate.parse(parts[3].trim(), dateFormat);

                    String monthYear =
                            date.getMonth()
                                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                                + " " + date.getYear();

                    if (!months.contains(monthYear)) {
                        months.add(monthYear);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Month load error", e);
        }

        return months;
    }

    // RECORD TIME IN
    
    public void recordTimeIn(String empId,
                             String lastName,
                             String firstName) {

        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));

        String timeIn = LocalTime.now()
                .format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT));

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(Constants.ATTENDANCE_CSV, true))) {

            writer.write(String.join(",",
                    empId,
                    lastName,
                    firstName,
                    today,
                    timeIn,
                    ""));

            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Time-in failed", e);
        }
    }

    // RECORD TIME OUT
 
    public void recordTimeOut(String empId) {

        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));

        String timeOut = LocalTime.now()
                .format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT));

        File original = new File(Constants.ATTENDANCE_CSV);
        File temp = new File("src/temp.csv");

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(original));
             BufferedWriter writer =
                     new BufferedWriter(new FileWriter(temp))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",", -1);

                if (!updated &&
                    parts.length >= 6 &&
                    parts[0].equals(empId) &&
                    parts[3].equals(today) &&
                    !parts[4].isEmpty() &&
                    parts[5].isEmpty()) {

                    line = String.join(",",
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            timeOut);

                    updated = true;
                }

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Time-out failed", e);
        }

        original.delete();
        temp.renameTo(original);
    }

    // GET ALL ATTENDANCE BY EMPLOYEE
    
    public List<Object[]> getAttendanceByEmployee(String empId) {

        List<Object[]> rows = new ArrayList<>();

        try (Scanner scanner =
                     new Scanner(new File(Constants.ATTENDANCE_CSV), "UTF-8")) {

            boolean skipHeader = true;

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length >= 6 &&
                    parts[0].equals(empId)) {

                    rows.add(new Object[]{
                            parts[3],
                            parts[4],
                            parts[5]
                    });
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Load attendance failed", e);
        }

        return rows;
    }
}