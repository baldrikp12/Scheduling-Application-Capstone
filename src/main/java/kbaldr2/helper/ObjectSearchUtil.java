package kbaldr2.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for searching objects based on field values.
 * It provides a generic method to search for objects within a list by filtering on their field values.
 *
 */
public class ObjectSearchUtil {
    
    /**
     * Searches for objects within a list by filtering on their field values.
     * The search is case-insensitive and searches across relevant fields of the objects.
     *
     * @param objects     The list of objects to search within.
     * @param searchString The text to search for within the objects' fields.
     * @param objectClass The class type of the objects in the list.
     * @return A list of objects that match the search criteria.
     */
    public static <T> List<T> searchObjectFields(List<T> objects, String searchString, Class<T> objectClass) {
        List<T> result = new ArrayList<>();
        
        for (T obj : objects) {
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                
                try {
                    Object fieldValue = field.get(obj);
                    if (fieldValue != null && fieldValue.toString().toLowerCase().contains(searchString.toLowerCase())) {
                        result.add(obj);
                        break;
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Error accessing field: " + field.getName());
                    e.printStackTrace();
                }
            }
        }
        
        return result;
    }
}
