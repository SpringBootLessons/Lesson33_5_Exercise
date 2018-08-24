import java.util.Random;

public class FizzBuzz {
    public static void main(String[] args){
        printNumbers();
    }

    protected static void printNumbers() {

        String word = "";
        int firstNum=0, lastNum=0, firstLastDiff=0;

        firstNum = getRandomNumber(1,100);
        lastNum = getRandomNumber(1,100);

        firstLastDiff = Math.abs(firstNum-lastNum);

        // If firstNum and lastNum are less than 10 digits apart, print this line
        if(firstLastDiff < 10) {
            System.out.println("This won't take long");
        }

        for(int i=1; i<=100; i++){
            word = "";

            // Multiple of 3, print "Fizz"
            if(i%3 == 0){
                word += "Fizz";
            }
            // Multiple of 5, print "Buzz"
            if(i%5 == 0) {
                word += "Buzz";
            }

            // Print word if not empty string, first or last num
            if(!word.equals("") && i != 1 && i != 100){
                System.out.println(word);
            }
            else{
                // If first num, print random num
                if(i == 1){

                    System.out.println(firstNum);
                }
                // If last num, print random num
                else if(i == 100){

                    System.out.println(lastNum);
                }
                // If multiple of 7, print text to indicate so
                else if(i%7 == 0){
                    System.out.println(i + " - multiple of seven");
                }
                else {
                    System.out.println(i);
                }
            }
            // If firstNum = lastNum generated, print "Shazam"
            if(firstNum == lastNum){
                System.out.println("Shazam!!");
            }

        }
    }

    protected static int getRandomNumber(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
