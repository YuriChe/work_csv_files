package appCSV.config;

import java.io.*;
import java.util.Properties;

public final class Config {

    private static final String CONFIG_FILE = "config.properties";

    private static String currentStreets;
    public static String CITY;
    public static boolean DEBUG;
    public static String PATH;
    public static String RESULT_PATH;
    public static String PATH_QUERY;
    public static String PATH_ERROR_FILES;

    public static String getCurrentStreets() {
        return currentStreets;
    }

    public Config() {
        loadConfig();
    }

    private void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IOException("Config file not found");
            }
            properties.load(input);

            DEBUG = properties.getProperty("debug", "false").equals("true");
            RESULT_PATH = properties.getProperty("path.result", "\\Users\\Public\\RES_JAVA\\");
            PATH = properties.getProperty("path.csv");
            PATH_QUERY = properties.getProperty("path.query");
            PATH_ERROR_FILES = properties.getProperty("path.wb_errors");
            currentStreets = loadQueries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadQueries() {
        StringBuilder streets = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_QUERY))) {
            if ((CITY = bufferedReader.readLine()) != null) {
                CITY = CITY.replace(",", "").toUpperCase().strip();
            } else {
                throw new NullPointerException("Отсутствует указание региона в файле запроса query№№.txt");
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (streets.length() > 2) {
                    streets.append(",");
                }
                streets.append(line);
            }
            int index;
            while ((index = streets.indexOf(" ")) != -1) {
                streets.replace(index, index+1, ",");
            }
            while ((index = streets.indexOf(",,")) != -1) {
                streets.deleteCharAt(index);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return streets.toString().toUpperCase();
    }
}
