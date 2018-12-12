import java.util.UUID;

public class GUID {
    public static void main(String[] args) {
        System.out.println(getGUID());
    }
    public static String getGUID(){
        String strGuid =  UUID.randomUUID().toString();
        strGuid = strGuid.replace("-", "");
        return strGuid;
    }
}