package io.github.dnloop.inventorycom.utils;

public class Trim {
    /*
     * Trims and replaces excessive whitespaces.
     */
    public static String trim(String str) {
	return str.trim().replaceAll(" +", " ");
    }

}
