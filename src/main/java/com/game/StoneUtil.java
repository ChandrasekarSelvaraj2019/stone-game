package src.main.java.com.game;

public class StoneUtil {

    private StoneUtil() {

    }

    public static int countStones(int[] userPits) {
        int count = 0;
        for (int i : userPits) {
            count += i;
        }
        return count;
    }

    public static boolean isAllElementsExceptBigPitIsZero(int[] userPits) {
        int count = 0;
        for (int i = 0; i < userPits.length; i++) {
            if (i != userPits.length - 1) {
                count += userPits[i];
            }
        }
        return count == 0;
    }
}
