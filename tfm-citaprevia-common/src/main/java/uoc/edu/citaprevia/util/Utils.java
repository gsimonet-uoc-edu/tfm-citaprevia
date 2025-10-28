package uoc.edu.citaprevia.util;

import java.util.List;

public final class Utils {
	
	public static final int size(List<?> list) { return list == null ? 0 : list.size(); }

	
	public static String getStr(final String str) {
		return (str == null ? "" : str);
	}
	
	public static boolean isEmpty(final String[] list) {
		return (list == null || list.length == 0);
	}

	public static boolean isEmpty(final List<?> list) {
		return (list == null || list.size() == 0);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isEmpty(Long num) {
		return num == null || num==0;
	}
	
	public static boolean isEmpty(Integer num) {
		return num == null || num==0;
	}
	
}
