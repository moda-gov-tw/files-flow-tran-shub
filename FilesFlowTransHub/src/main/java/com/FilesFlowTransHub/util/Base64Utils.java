package com.FilesFlowTransHub.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {
	/**
	 * 將文字轉成base64
	 * @param data
	 * @return
	 */
    public static String stringToBase64(String data) {
        String encodedText = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        return encodedText;
    }
    
    /**
     * 將 Base64 轉回文字
     * @param data Base64 編碼的字串
     * @return 解碼後的文字
     */
    public static String base64ToString(String data) {
        try {
            String decodedText = new String(Base64.getDecoder().decode(data.replaceAll(" ", "")), StandardCharsets.UTF_8);
            return decodedText;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input! Please try again.");
            return "";
        }
    }
}
