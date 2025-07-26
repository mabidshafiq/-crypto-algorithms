import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SHA-256 Hash Calculator ===");
        System.out.println("Enter text to hash (or 'quit' to exit):");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }
            
            if (input.isEmpty()) {
                System.out.println("Please enter some text.");
                continue;
            }
            
            byte[] hash = SHA256.hash(input.getBytes());
            String hexHash = bytesToHex(hash);
            
            System.out.println("Input: " + input);
            System.out.println("SHA-256: " + hexHash);
            System.out.println();
        }
        
        scanner.close();
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
} 