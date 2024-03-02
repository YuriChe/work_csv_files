/*
package appCSV.geo;

import org.geotools.geometry.jts.JTS;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

public class CalculateGeoHash {

    public static void main(String[] args) throws Exception {
        double latitude = 37.7749;
        double longitude = -122.4194;

        // Создаем точку из координат широты и долготы
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coordinate);

        // Преобразуем геометрический объект в GeoJSON
        GeometryJSON geometryJson = new GeometryJSON();
        String geoJson = geometryJson.toString(point);

        // Извлекаем GeoHash из GeoJSON
        String geoHash = extractGeoHashFromGeoJson(geoJson);

        System.out.println("GeoHash: " + geoHash);
    }

    private static String extractGeoHashFromGeoJson(String geoJson) {
        // В GeoJSON геохеш хранится в свойстве "geohash"
        String[] parts = geoJson.split("\"geohash\":\"");
        if (parts.length > 1) {
            String[] hashParts = parts[1].split("\"", 2);
            return hashParts[0];
        }
        return null;
    }
}
*/
