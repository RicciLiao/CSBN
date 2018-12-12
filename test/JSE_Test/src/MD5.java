import java.security.MessageDigest;
import java.util.Scanner;

public class MD5 {
    public static void Encryption(String psw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = psw.getBytes();
            byte[] outputData = md.digest(inputData);
            System.out.println(psw + ":" + bytesToHex(outputData));
            System.out.println(bytesToHex(outputData).length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String psw = sc.next();
        MD5 md5 = new MD5();
        md5.Encryption(psw);
    }
}
