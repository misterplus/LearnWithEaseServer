package team.one.lwes.util;

import cn.hutool.crypto.SecureUtil;
import org.jetbrains.annotations.NotNull;

public class UserUtils {
    public static String getAccid(@NotNull String username) {
        return username.toLowerCase();
    }

    public static String getToken(@NotNull String username, @NotNull String password) {
        return SecureUtil.md5(username + password);
    }

    public static boolean isUsernameValid(@NotNull String username) {
        return TextUtils.isLegalUsername(username) && username.length() >= 6 && username.length() <= 16;
    }

    public static boolean isPasswordValid(@NotNull String password) {
        return TextUtils.isLegalPassword(password) && TextUtils.getPasswordComplexity(password) > 1 && password.length() >= 6 && password.length() <= 16;
    }
}
