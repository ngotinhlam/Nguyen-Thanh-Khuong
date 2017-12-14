package handle;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Lớp kiểm tra tính hợp lệ của các thông tin người dùng nhập

public class Validate {

    //Kiểm tra một email hợp lệ
    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    //Kiểm tra một tên tài khoản hợp lệ
    public static boolean isValidUsername(String username) {
        return username.indexOf(" ") == -1 && !username.equals("");
    }

    //Kiểm tra một số điện thoại hợp lệ
    public static boolean isValidPhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else if (number.length() == 10 || number.length() == 11) {
            if (number.length() == 10) {
                if (number.substring(0, 2).equals("09")) {
                    return true;
                } else {
                    return false;
                }
            } else if (number.substring(0, 2).equals("01")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Kiểm tra một mật khẩu hợp lệ
    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}
