package com.onesmile.droplistview;

import java.util.List;

public class Utils {

	/**
	 * ÒÆ¶¯Êý¾Ý
	 * @param list
	 * @param fromIndex
	 * @param toIndex
	 */
    public static <T> void removeData(List<T> list, int fromIndex, int toIndex) {
        if (fromIndex >= list.size() || fromIndex < 0 || toIndex >= list.size() || toIndex < 0 || fromIndex == toIndex) {
            return;
        }
        if (fromIndex < toIndex) {
            removeIncrease(list, fromIndex, toIndex);
        } else if (fromIndex > toIndex){
            removeDecrease(list, fromIndex, toIndex);
        }
    }

    private static <T> void removeDecrease(List<T> list, int fromIndex, int toIndex) {
        T tempItem = list.get(fromIndex);
        for (int i = fromIndex; i > toIndex; i--) {
            list.set(i, list.get(i - 1));
        }
        list.set(toIndex, tempItem);
    }

    private static <T> void removeIncrease(List<T> list, int fromIndex, int toIndex) {
        T tempItem = list.get(fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            list.set(i, list.get(i + 1));
        }
        list.set(toIndex, tempItem);
    }
}
