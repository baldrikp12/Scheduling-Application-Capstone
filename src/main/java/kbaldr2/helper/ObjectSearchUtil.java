package kbaldr2.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectSearchUtil {
    
    public static <T> List<T> searchObjectFields(List<T> objects, String searchString, Class<T> objectClass) {
        List<T> result = new ArrayList<>();
        
        for (T obj : objects) {
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Enable access to private fields if necessary
                
                try {
                    Object fieldValue = field.get(obj);
                    if (fieldValue != null && fieldValue.toString().toLowerCase().contains(searchString.toLowerCase())) {
                        result.add(obj);
                        break; // Stop searching fields once a match is found
                    }
                } catch (IllegalAccessException e) {
                    // Handle IllegalAccessException if needed
                }
            }
        }
        
        return result;
    }
}
