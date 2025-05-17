package task3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class task3 {

    static class Value {
        private int id;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class Test {
        private int id;
        private String value;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"id\": " + id + ", " +
                    "\"value\": \"" + value + "\", " +
                    "\"title\": \"" + title + "\"" +
                    "}";
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Ошибка: java task3 <values.json> <tests.json>");
            System.exit(1);
        }

        String valuesFilePath = args[0];
        String testsFilePath = args[1];
        String reportFilePath = "report.json";

        List<Value> values = readValuesFromFile(valuesFilePath);
        List<Test> tests = readTestsFromFile(testsFilePath);

        for (Test test : tests) {
            if (test.getTitle() == null || test.getTitle().isEmpty()) {
                test.setTitle("Test " + test.getId());
            }
        }

        for (Test test : tests) {
            int testId = test.getId();
            for (Value value : values) {
                if (value.getId() == testId) {
                    test.setValue(value.getValue());
                    break;
                }
            }
        }

        writeTestsToFile(reportFilePath, tests);
    }

    private static List<Value> readValuesFromFile(String filePath) {
        List<Value> values = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            String jsonString = json.toString().replaceAll("\\s+", "");
            if (jsonString.contains("\"values\":[")) {
                String valuesString = jsonString.split("\"values\":\\[")[1].split("\\]")[0].trim();
                String[] entries = valuesString.split("\\},\\s*\\{");
                for (String entry : entries) {
                    entry = entry.replaceAll("[{}]", "").trim();
                    String[] parts = entry.split(",");
                    int id = 0;
                    String value = "";
                    for (String part : parts) {
                        String[] keyValue = part.split(":");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim().replace("\"", "");
                            String val = keyValue[1].trim().replace("\"", "");
                            if (key.equals("id")) {
                                id = Integer.parseInt(val);
                            } else if (key.equals("value")) {
                                value = val;
                            }
                        }
                    }
                    Value valueObj = new Value();
                    valueObj.setId(id);
                    valueObj.setValue(value);
                    values.add(valueObj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    private static List<Test> readTestsFromFile(String filePath) {
        List<Test> tests = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            String jsonString = json.toString().replaceAll("\\s+", "");
            if (jsonString.contains("\"values\":[")) {
                String valuesString = jsonString.split("\"values\":\\[")[1].split("\\]")[0].trim();
                String[] entries = valuesString.split("\\},\\s*\\{");
                for (String entry : entries) {
                    entry = entry.replaceAll("[{}]", "").trim();
                    String[] parts = entry.split(",");
                    Test test = new Test();
                    for (String part : parts) {
                        String[] keyValue = part.split(":");
                        if (keyValue.length == 2) {
                            String key = keyValue[0].trim().replace("\"", "");
                            String value = keyValue[1].trim().replace("\"", "");
                            if (key.equals("id")) {
                                test.setId(Integer.parseInt(value));
                            } else if (key.equals("value")) {
                                test.setValue(value);
                            }
                        }
                    }
                    test.setTitle("Test " + test.getId());
                    tests.add(test);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tests;
    }

    private static void writeTestsToFile(String filePath, List<Test> tests) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("{\"tests\": [");
            for (int i = 0; i < tests.size(); i++) {
                writer.write(tests.get(i).toString());
                if (i < tests.size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("]}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}