package appCSV.config;

import java.io.*;
import java.util.Properties;

public final class Config {

    private final static Config instance = new Config();

    private static final String CONFIG_FILE = "config.properties";

    private static String currentStreets;
    public static String city;
    public static boolean debug;
    public static String path;
    public static String resultPath;
    public static String pathQueryToSave;
    public static String pathErrorFiles;
    public static boolean isDeleteFirstLine;
    public static TypeRead typeRead;


    public static String getCurrentStreets() {
        return currentStreets;
    }

    public static Config getInstance() {
        return instance;
    }

    private Config() {
        loadConfig();
    }

    private void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IOException("Config file not found");
            }
            properties.load(input);

            debug = properties.getProperty("debug", "false").equals("true");
            resultPath = properties.getProperty("path.result", "\\Users\\Public\\RES_JAVA\\");
            path = properties.getProperty("path.csv");
            pathQueryToSave = properties.getProperty("path.query");
            pathErrorFiles = properties.getProperty("path.wb_errors");
            currentStreets = loadQueries();
            isDeleteFirstLine = properties.getProperty("column.titles", "true").equals("true");
            switch (properties.getProperty("type.read", "basic")) {
                case "error" :  typeRead = TypeRead.ERROR;
                break;
                case "basic" : typeRead = TypeRead.BASIC;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadQueries() {
        StringBuilder streets = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathQueryToSave))) {
            if ((city = bufferedReader.readLine()) != null) {
                city = city.replace(",", "").toUpperCase().strip();
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
