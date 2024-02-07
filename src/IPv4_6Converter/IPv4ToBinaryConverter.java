package IPv4_6Converter;

import java.util.Scanner;

public class IPv4ToBinaryConverter {

    public static String convertToBinary(String ipAddress) {
        String[] octets = ipAddress.split("\\.");

        if (octets.length != 4) {
            return "Invalid IPv4 address";
        }

        StringBuilder binaryAddress = new StringBuilder();

        for (String octet : octets) {
            try {
                int decimalValue = Integer.parseInt(octet);
                if (decimalValue < 0 || decimalValue > 255) {
                    return "Invalid IPv4 address";
                }

                String binaryRepresentation = Integer.toBinaryString(decimalValue);
                // Ensure each octet is represented by 8 bits
                String paddedBinary = String.format("%8s", binaryRepresentation).replace(' ', '0');

                binaryAddress.append(paddedBinary);
            } catch (NumberFormatException e) {
                return "Invalid IPv4 address";
            }
        }

        return binaryAddress.toString();
    }

    public static void main(String[] args) {

        Scanner scanner =  new Scanner(System.in);

        System.out.println("Please input your IPv4 address: ");
        String ipAddress = scanner.nextLine();

        String binaryRepresentation = convertToBinary(ipAddress);

        System.out.println("IPv4: " + ipAddress);
        System.out.println("Binary: " + binaryRepresentation);

        scanner.close();
    }
}
