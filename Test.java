public class Test {
    public static void main(String[] args) {
        System.out.println("=== SHA-256 Test ===");
        
        // Test case 1: "abc"
        String input1 = "abc";
        byte[] hash1 = SHA256.hash(input1.getBytes());
        String expected1 = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
        String result1 = bytesToHex(hash1);
        
        System.out.println("Test 1 - Input: " + input1);
        System.out.println("Expected: " + expected1);
        System.out.println("Result:   " + result1);
        System.out.println("Match:    " + result1.equals(expected1));
        System.out.println();
        
        // Test case 2: Empty string
        String input2 = "";
        byte[] hash2 = SHA256.hash(input2.getBytes());
        String expected2 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String result2 = bytesToHex(hash2);
        
        System.out.println("Test 2 - Input: (empty string)");
        System.out.println("Expected: " + expected2);
        System.out.println("Result:   " + result2);
        System.out.println("Match:    " + result2.equals(expected2));
        System.out.println();
        
        // Test case 3: "Hello, World!"
        String input3 = "Hello, World!";
        byte[] hash3 = SHA256.hash(input3.getBytes());
        String expected3 = "dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f";
        String result3 = bytesToHex(hash3);
        
        System.out.println("Test 3 - Input: " + input3);
        System.out.println("Expected: " + expected3);
        System.out.println("Result:   " + result3);
        System.out.println("Match:    " + result3.equals(expected3));
        System.out.println();
        
        // Summary
        boolean allTestsPassed = result1.equals(expected1) && 
                                result2.equals(expected2) && 
                                result3.equals(expected3);
        
        System.out.println("=== Test Summary ===");
        System.out.println("All tests passed: " + allTestsPassed);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
} 