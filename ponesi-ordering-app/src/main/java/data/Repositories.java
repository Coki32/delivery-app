package data;


import java.util.HashMap;
import java.util.Map;

public class Repositories {

    private static final Map<String, Object> repositoryMap = new HashMap<>();


    public void register(Object instance){
        repositoryMap.put(instance.getClass().getName(), instance);
    }

    public<T> T getRepository(Class<T> repoClass){
        var existing = repositoryMap.get(repoClass.getName());
        if(existing==null)
            return null;
        try{
            return repoClass.cast(existing);
        }catch (ClassCastException ex){
            System.err.println("That repository is not registered (yet).");
            return null;
        }
    }
}
