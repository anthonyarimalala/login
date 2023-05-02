package etu2033.fonctions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Fonction {
    private static Object convertToPrimitive(Object value, Class<?> type) {
        if (type.equals(byte.class)) {
            return Byte.valueOf(value.toString());
        } else if (type.equals(short.class)) {
            return Short.valueOf(value.toString());
        } else if (type.equals(int.class)) {
            return Integer.valueOf(value.toString());
        } else if (type.equals(long.class)) {
            return Long.valueOf(value.toString());
        } else if (type.equals(float.class)) {
            return Float.valueOf(value.toString());
        } else if (type.equals(double.class)) {
            return Double.valueOf(value.toString());
        } else if (type.equals(boolean.class)) {
            return Boolean.valueOf(value.toString());
        } else if (type.equals(char.class)) {
            return value.toString().charAt(0);
        } else {
            throw new IllegalArgumentException("Type non supporté : " + type.getName());
        }
    }

    
    public static String setters(String variable_name){
        String premierelettre = variable_name.charAt(0) + "";
        String majuscule = premierelettre.toUpperCase();
        return "set"+variable_name.replaceFirst(premierelettre, majuscule);
    }
    
    public static Object buildObject(java.lang.Class classe, HashMap<String, String> parametres) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, ParseException{
        Object nouvelle_instance = classe.newInstance();
        for (Map.Entry<String, String> entry : parametres.entrySet()) {
            
                String key = entry.getKey();
                String value = entry.getValue();
                String setter = setters(key);
                
                Field field = nouvelle_instance.getClass().getDeclaredField(key);
                
                Class field_type = field.getType();
                
                Method m = nouvelle_instance.getClass().getDeclaredMethod(setter, field_type);
                
                value = value.trim();
                
                Object converted_value;
                if(field_type.isPrimitive()) {
                    converted_value = Fonction.convertToPrimitive(value, field_type);
                } 
                else if(field_type.equals(String.class)) {
                    converted_value = value;
                } 
                else if (field_type.equals(Date.class)) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(value);
                    converted_value = new java.sql.Date(parsedDate.getTime());
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Impossible de convertir la valeur en Date : " + value);
                }
                } else if (field_type.equals(LocalDate.class)) {
                    converted_value = LocalDate.parse(value);
                } 
                else {
                    converted_value = field_type.cast(value);
                }
                m.invoke(nouvelle_instance, converted_value);
        }
        return nouvelle_instance;
    }
    

}
