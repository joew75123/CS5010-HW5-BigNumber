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
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public BigNumberImpl(String number) {
        if (!isValidNumber(number)) {
            throw new IllegalArgumentException("Invalid number format");
        }

        // Remove leading zeros
        int startIndex = 0;
        while (startIndex < number.length() - 1 && number.charAt(startIndex) == '0') {
            startIndex++;
        }
        number = number.substring(startIndex);

        // Create linked list representation
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            addDigitToFront(digit);
        }
    }

    private boolean isValidNumber(String number) {
        return number.matches("\\d+");
    }

    private void addDigitToFront(int digit) {
        DigitNode newNode = new DigitNode(digit);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public void shiftLeft(int shifts) {
        if (shifts < 0) {
            shiftRight(-shifts);
            return;
        }

        for (int i = 0; i < shifts; i++) {
            addDigitToFront(0);
        }
    }

    @Override
    public void shiftRight(int shifts) {
        if (shifts < 0) {
            shiftLeft(-shifts);
            return;
        }

        if (shifts >= size) {
            head = null;
            tail = null;
            size = 0;
            return;
        }

        for (int i = 0; i < shifts; i++) {
            head = head.next;
            size--;
        }
    }

    @Override
    public void addDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }
        addDigitToFront(digit);
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
            sb.insert(0, current.digit);
            current = current.next;
        }
        return sb.toString();
    }
}
