import java.util.Random;

public class FizzBuzz {
    public static void main(String[] args){
        printNumbers();
    }

    protected static void printNumbers() {

        String word = "";
        int firstNum=0, lastNum=0;

        for(int i=1; i<=100; i++){
            word = "";

            if(i%3 == 0){
                word += "Fizz";
            }
            if(i%5 == 0) {
                word += "Buzz";
            }

            if(!word.equals("") && i != 1 && i != 100){
                System.out.println(word);
            }
            else{

                if(i == 1){
                    firstNum = getRandomNumber(1,100);
                    System.out.println(firstNum);
                }
                else if(i == 100){
                    lastNum = getRandomNumber(1,100);
                    System.out.println(lastNum);
                }
                else {
                    System.out.println(i);
                }
            }

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
