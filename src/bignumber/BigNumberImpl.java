package bignumber;

public class BigNumberImpl implements BigNumber {
    private DigitNode head;
    private DigitNode tail;
    private int size;

    private static class DigitNode {
        int digit;
        DigitNode next;

        public DigitNode(int digit) {
            this.digit = digit;
            this.next = null;
        }
    }

    public BigNumberImpl() {
        DigitNode node=new DigitNode(0);
        this.head = node;
        this.tail = node;
        this.size = 1;
    }

    // Constructor that takes a string representation of the number
    public BigNumberImpl(String numberStr) {
        if (!isValidNumber(numberStr)) {
            throw new IllegalArgumentException("Invalid number format");
        }
        // Initialize the linked list with the digits from the string
        for (int i = 0; i <numberStr.length(); i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            this.addDigitToFront(digit);
        }

    }

    private boolean isValidNumber(String number) {
        return number.matches("\\d+");
    }


    private void addDigitToFront(int digit) {
        DigitNode newNode = new DigitNode(digit);
        if (this.head == null) {
            this.head = newNode;
            size++;
        }
        else if(this.head.digit==0) {this.head=newNode;}
        else{
                newNode.next = this.head;
                this.head = newNode;
                size++;
            }
    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public void shiftLeft(int shifts) {
        if (shifts < 0) {
            shiftRight(-shifts); // Convert to right shift if shifts are negative
            return;
        }

        if (this.head == null || (this.head.digit == 0 && this.head.next == null)) {
            // If the number is 0, shifting left does not change its value
            return;
        }

        DigitNode current = this.head;
        while (current.next != null) {
            // Move to the last digit
            current = current.next;
        }

        for (int i = 0; i < shifts; i++) {
            System.out.println(current.digit);
            // Append zeros to the end of the list for each shift
            current.next = new DigitNode(0);
            current = current.next;
            System.out.println(this);
        }
    }
    public void appendDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }

        // Create a new node for the digit
        DigitNode newNode = new DigitNode(digit);

        // If the list is empty, set the new node as both head and tail
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            // Insert the new node after the current tail
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }
    @Override
    public void shiftRight(int shifts) {
        if (shifts < 0) {
            shiftLeft(-shifts); // Convert to left shift if shifts are negative
            return;
        }

        if (this.head == null || (this.head.digit == 0 && this.head.next == null)) {
            // If the number is 0, shifting right does not change its value
            return;
        }

        for (int i = 0; i < shifts; i++) {
            if (this.head.next == null) {
                // If there's only one digit left, set the number to 0
                this.head.digit = 0;
                break;
            } else {
                // Remove the rightmost digit
                DigitNode prev = null;
                DigitNode current = this.head;
                while (current.next != null) {
                    prev = current;
                    current = current.next;
                }
                if (prev != null) {
                    prev.next = null; // Remove the last digit
                }
            }
            size++;
        }
    }


    @Override
    public void addDigit(int digit) throws IllegalArgumentException {
        // Step 1: Validate the input
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("The digit must be between 0 and 9");
        }

        // Special case: Adding a digit to 0
        if (this.head.digit == 0 && this.head.next == null) {
            this.head.digit = digit;
            return;
        }

        // Step 2: Add the digit to the rightmost node
        int carry = digit;
        DigitNode current = this.head;

        // Traverse to the rightmost node
        while (current.next != null) {
            current = current.next;
        }

        // Add the digit to the rightmost node and calculate the carry
        int sum = current.digit + carry;
        current.digit = sum % 10;
        carry = sum / 10;

        // Step 3: Propagate the carry leftwards
        while (carry > 0) {
            if (current.next == null) {
                current.next = new DigitNode(carry % 10);
                carry /= 10;
            } else {
                current = current.next;
                sum = current.digit + carry;
                current.digit = sum % 10;
                carry = sum / 10;
            }
        }
    }

    @Override
    public int getDigitAt(int position) {
        if (position < 0 || position >= size) {
            throw new IllegalArgumentException("Invalid position");
        }
        DigitNode current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.digit;
    }

    @Override
    public BigNumber copy() {
        BigNumberImpl copy = new BigNumberImpl();
        DigitNode current = head;
        while (current != null) {
            copy.addDigit(current.digit);
            current = current.next;
        }
        return copy;
    }

    @Override
    public BigNumber add(BigNumber other) {
        BigNumberImpl result = new BigNumberImpl();
        int carry = 0;
        int maxLength = Math.max(this.length(), other.length());
        for (int i = 0; i < maxLength; i++) {
            int sum = this.getDigitAt(i) + other.getDigitAt(i) + carry;
            result.addDigit(sum % 10);
            carry = sum / 10;
        }
        if (carry > 0) {
            result.addDigit(carry);
        }
        return result;
    }

    @Override
    public int compareTo(BigNumber other) {
        if (this.length() != other.length()) {
            return Integer.compare(this.length(), other.length());
        }
        for (int i = this.length() - 1; i >= 0; i--) {
            int digit1 = this.getDigitAt(i);
            int digit2 = other.getDigitAt(i);
            if (digit1 != digit2) {
                return Integer.compare(digit1, digit2);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DigitNode current = head;
        while (current != null) {
            sb.insert(sb.length(), current.digit);
            current = current.next;
        }
        return sb.toString();
    }
}
