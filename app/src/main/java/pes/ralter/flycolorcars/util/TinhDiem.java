package pes.ralter.flycolorcars.util;

public class TinhDiem {
    public static String getLevel(long point, String kwDifficult) {
        if (kwDifficult == MyKeyword.DIFFICULT_EASY) {
            if (point >= 100)
                return "Điểm kout";
            else
                return "Điểm kém";
        } else {
            if (point >= 25) {
                return "Điểm kout";
            } else {
                return "Điểm kém";
            }
        }
    }
}
