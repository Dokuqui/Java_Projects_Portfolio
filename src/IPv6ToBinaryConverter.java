import java.util.Scanner;

public class IPv6ToBinaryConverter {

    public static String convertToBinary(String ipv6Address) {
        String[] groups = ipv6Address.split(":");

        if (groups.length != 8) {
            return "Invalid IPv6 address";
        }

        StringBuilder binaryAddress = new StringBuilder();

        for (String group : groups) {
            // Ensure each group is represented by 16 bits
            String paddedHex = String.format("%4s", group).replace(' ', '0');

            try {
                String binaryRepresentation = Integer.toBinaryString(Integer.parseInt(paddedHex, 16));
                // Ensure each group is represented by 16 bits
                String paddedBinary = String.format("%16s", binaryRepresentation).replace(' ', '0');

                binaryAddress.append(paddedBinary);
            } catch (NumberFormatException e) {
                return "Invalid IPv6 address";
            }
        }

        return binaryAddress.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please input your IPv6 address: ");
        String ipv6Address = scanner.nextLine();

        String binaryRepresentation = convertToBinary(ipv6Address);

        System.out.println("IPv6: " + ipv6Address);
        System.out.println("Binary: " + binaryRepresentation);
    }
}
