package task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class task3 {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Ошибка: java task3 <values.json> <tests.json> <report.json>");
            System.exit(1);
        }

        String valuesPath = args[0];

        String testsPath = args[1];

        String reportPath = args[2];

        try {
            String valuesContent = readFile(valuesPath);
            Map<String, String> valuesMap = parseValues(valuesContent);

            String testsContent = readFile(testsPath);

            String reportContent = fillValues(testsContent, valuesMap);

            writeFile(reportPath, reportContent);

            System.out.println("Отчет успешно создан: " + reportPath);

        } catch (IOException e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());

            e.printStackTrace();

            System.exit(1);
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    private static Map<String, String> parseValues(String content) {
        Map<String, String> valuesMap = new HashMap<>();

        Pattern pattern = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"value\"\\s*:\\s*\"([^\"]+)\"\\s*\\}");

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String id = matcher.group(1);

            String value = matcher.group(2);

            valuesMap.put(id, value);
        }

        return valuesMap;
    }

    private static String fillValues(String content, Map<String, String> valuesMap) {
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("(\\{[^{}]*\"id\"\\s*:\\s*(\\d+)[^{}]*)(\"value\"\\s*:\\s*\"[^\"]*\")([^{}]*\\})");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String before = matcher.group(1);

            String id = matcher.group(2);

            String oldValue = matcher.group(3);

            String after = matcher.group(4);

            String newValue = "\"value\": \"" + valuesMap.getOrDefault(id, "") + "\"";

            matcher.appendReplacement(result, before + newValue + after);
        }
        matcher.appendTail(result);

        if (result.toString().matches(".*\"id\"\\s*:\\s*\\d+.*\"value\"\\s*:\\s*\"[^\"]*\".*")) {
            return fillValues(result.toString(), valuesMap);
        }

        return result.toString();
    }
}
