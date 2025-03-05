import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Scanner;

public class PasswordGenerator {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

    private static final HashMap<String, String> passwordStorage = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        System.out.println("====================================");
        System.out.println("|      Secure Password Manager    |");
        System.out.println("====================================");

        while (!exitProgram) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Generate and Save Password");
            System.out.println("2. View Saved Passwords");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    generateAndSavePassword(scanner);
                    break;
                case "2":
                    viewSavedPasswords(scanner);
                    break;
                case "3":
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        }
        
        System.out.println("Thank you for using Password Manager!");
        System.out.println("====================================");
        scanner.close();
    }

    private static void generateAndSavePassword(Scanner scanner) {
        boolean continueGenerating = true;

        while (continueGenerating) {
            int length = getPasswordLength(scanner);
            String password = generateSecurePassword(length);

            System.out.println("------------------------------------");
            System.out.println("Generated Password: " + password);
            System.out.println("------------------------------------");

            System.out.print("Do you want to save this password? (yes/no): ");
            String saveChoice = scanner.nextLine().trim().toLowerCase();
            
            if (saveChoice.equals("yes")) {
                System.out.print("Enter a label (e.g., Facebook, Email, Instagram): ");
                String label = scanner.nextLine();
                passwordStorage.put(label, password);
                System.out.println("Password saved successfully!");
            }

            System.out.print("Do you want to generate another password? (yes/no): ");
            String repeatChoice = scanner.nextLine().trim().toLowerCase();
            continueGenerating = repeatChoice.equals("yes");
        }
    }

    private static void viewSavedPasswords(Scanner scanner) {
        if (passwordStorage.isEmpty()) {
            System.out.println("No saved passwords found.");
            return;
        }

        System.out.println("\n===== Stored Passwords =====");
        for (String label : passwordStorage.keySet()) {
            System.out.println(label + ": " + passwordStorage.get(label));
        }
        System.out.println("============================");
    }

    private static int getPasswordLength(Scanner scanner) {
        int length;
        while (true) {
            System.out.print("Enter password length (min 6, max 20): ");
            try {
                length = Integer.parseInt(scanner.nextLine());
                if (length >= 6 && length <= 20) break;
                System.out.println("Invalid input! Length must be between 6 and 20.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return length;
    }

    private static String generateSecurePassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        return shuffleString(password.toString(), random);
    }

    private static String shuffleString(String input, SecureRandom random) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }
}
