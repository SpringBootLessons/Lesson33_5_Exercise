
public class FizzBuzz {
    public static void main(String[] args){
        printNumbers();
    }

    protected static void printNumbers() {

        String word = "";

        for(int i=1; i<=100; i++){
            word = "";

            if(i%3 == 0){
                word += "Fizz";
            }
            if(i%5 == 0) {
                word += "Buzz";
            }

            if(!word.equals("")){
                System.out.println(word);
            }
            else{
                System.out.println(i);
            }

        }
    }
}
