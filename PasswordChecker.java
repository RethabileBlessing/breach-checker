import java.io.*;
        import java.nio.file.*;
        import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;

public class PasswordChecker {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter a password: ");
        String password = reader.readLine();

        int score = checkStrength(password);
        boolean isBreached = checkBreached(password);

        System.out.println("\n--- Password Analysis ---");
        System.out.println("Strength Score: " + score + "/5");
        System.out.println("Strength Level: " + getStrengthLevel(score));
        System.out.println("Breach Check: " + (isBreached ? "⚠️ Found in breached list!" : "✔️ Safe (not found)"));
    }

    // ------------------ PASSWORD STRENGTH CHECK ------------------ //

    public static int checkStrength(String password) {
        int score = 0;

        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;

        if (Pattern.compile("[A-Z]").matcher(password).find()) score++;
        if (Pattern.compile("[0-9]").matcher(password).find()) score++;
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) score++;

        return score;
    }

    public static String getStrengthLevel(int score) {
        return switch (score) {
            case 0, 1 -> "Very Weak";
            case 2 -> "Weak";
            case 3 -> "Medium";
            case 4 -> "Strong";
            case 5 -> "Very Strong";
            default -> "Unknown";
        };
    }

    // ------------------ BREACH CHECK ------------------ //

    public static boolean checkBreached(String password) {
        try {
            Set<String> breachedList = new HashSet<>(Files.readAllLines(Paths.get("breached.txt")));
            return breachedList.contains(password);
        } catch (IOException e) {
            System.out.println("Error loading breach file.");
            return false;
        }
    }
}
