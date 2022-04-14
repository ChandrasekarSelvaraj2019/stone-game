package src.main.java.com.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static src.main.java.com.game.StoneUtil.countStones;
import static src.main.java.com.game.StoneUtil.isAllElementsExceptBigPitIsZero;

public class StoneGame {

    private static boolean isUser1Playing = true;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the number of small pits for each player");
        int numberOfSmallPits = Integer.parseInt(bufferedReader.readLine().trim());
        System.out.println("Enter the number of stones in each small pit");
        int numberOfStonesInEachPit = Integer.parseInt(bufferedReader.readLine().trim());

        int[] user1Pits = new int[numberOfSmallPits + 1];
        int[] user2Pits = new int[numberOfSmallPits + 1];
        Arrays.fill(user1Pits, numberOfStonesInEachPit);
        Arrays.fill(user2Pits, numberOfStonesInEachPit);
        System.out.println("user 1 pits" + Arrays.toString(user1Pits) + "\n" + "user 2 pits" + Arrays.toString(user2Pits));

        boolean isGameStillOn = true;

        while (isGameStillOn) {
            System.out.println("Select pit for " + (isUser1Playing ? "user1" : "user2") + " starting from pit zero,except empty pit and large pit");
            int inputPitPosition = Integer.parseInt(bufferedReader.readLine().trim());
            if (inputPitPosition >= numberOfSmallPits) {
                System.out.println("Pit position entered is not valid");
                continue;
            }
            int totalStones = getTotalStonesFromSelectedPit(inputPitPosition, user1Pits, user2Pits);
            if (totalStones == 0) {
                System.out.println("Pit position entered is not valid");
                continue;
            }
            if (isUser1Playing) {
                user1Pits[inputPitPosition] = 0;
                isUser1Playing = !isUser1Playing;
                sowToNextPits(inputPitPosition, user1Pits, user2Pits, totalStones);
            } else {
                user2Pits[inputPitPosition] = 0;
                isUser1Playing = !isUser1Playing;
                sowToNextPits(inputPitPosition, user2Pits, user1Pits, totalStones);
            }

            System.out.println("user 1 pits" + Arrays.toString(user1Pits) + "\n" + "user 2 pits" + Arrays.toString(user2Pits));

            if (isAllElementsExceptBigPitIsZero(user1Pits) || isAllElementsExceptBigPitIsZero(user2Pits)) {
                isGameStillOn = false;
            }
        }
        announceWinner(user1Pits, user2Pits);
        bufferedReader.close();
    }

    private static int getTotalStonesFromSelectedPit(int inputPitPosition, int[] user1Pits, int[] user2Pits) {
        return isUser1Playing ? user1Pits[inputPitPosition] : user2Pits[inputPitPosition];
    }

    private static void sowToNextPits(int inputPitPosition, int[] user1Pits, int[] user2Pits, int totalStones) {
        for (int i = inputPitPosition + 1; i < user1Pits.length; i++) {
            user1Pits[i] += 1;
            totalStones--;
            if (totalStones == 0) {
                if (user1Pits[i] == 1 && i != user1Pits.length - 1) {
                    int oppositePit = user2Pits.length - 1 - i;
                    user1Pits[i] = user1Pits[i] + user2Pits[oppositePit];
                    user2Pits[oppositePit] = 0;
                } else if (i == user1Pits.length - 1) {
                    isUser1Playing = !isUser1Playing;
                }
                break;
            }
        }
        if (totalStones > 0) {
            for (int i = 0; i < user2Pits.length - 1; i++) {
                user2Pits[i] += 1;
                totalStones--;
                if (totalStones == 0) {
                    break;
                }
            }
        }
        while (totalStones != 0) {
            sowToNextPits(0, user1Pits, user2Pits, totalStones);
        }
    }

    private static void announceWinner(int[] user1Pits, int[] user2Pits) {
        int user1Stones = countStones(user1Pits);
        int user2Stones = countStones(user2Pits);
        if (user1Stones == user2Stones) {
            System.out.println("Game Ended in a Draw");
        } else {
            System.out.println("Game Ended. winner is " + (user1Stones > user2Stones ? "user 1" : "user2"));
        }
    }
}

