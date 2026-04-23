class NumberPrinter {
    private int counter = 1;
    private int MAX_NUMBERS;

    // Constructor to set the maximum limit
    public NumberPrinter(int maxNumbers) {
        this.MAX_NUMBERS = maxNumbers;
    }

    // Method for the Odd Thread
    public synchronized void printOdd() {
        while (counter <= MAX_NUMBERS) {
            // If the number is even, the Odd thread should wait
            while (counter % 2 == 0) {
                try {
                    wait(); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            // Check again to prevent printing beyond MAX_NUMBERS after waking up
            if (counter <= MAX_NUMBERS) {
                System.out.println("Odd Thread prints: " + counter);
                counter++;
                notify(); // Wake up the Even thread
            }
        }
    }

    // Method for the Even Thread
    public synchronized void printEven() {
        while (counter <= MAX_NUMBERS) {
            // If the number is odd, the Even thread should wait
            while (counter % 2 != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            // Check again to prevent printing beyond MAX_NUMBERS after waking up
            if (counter <= MAX_NUMBERS) {
                System.out.println("Even Thread prints: " + counter);
                counter++;
                notify(); // Wake up the Odd thread
            }
        }
    }
}

public class EvenOddThreadExample {
    public static void main(String[] args) {
        // Set the maximum number up to which you want to print
        int max = 10; 
        
        // Shared printer object
        NumberPrinter printer = new NumberPrinter(max);

        // Creating the Odd Thread using a Lambda Expression for Runnable
        Thread oddThread = new Thread(() -> {
            printer.printOdd();
        });

        // Creating the Even Thread using a Lambda Expression for Runnable
        Thread evenThread = new Thread(() -> {
            printer.printEven();
        });

        // Start both threads
        oddThread.start();
        evenThread.start();
    }
}