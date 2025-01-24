
// Importing all the classes/utilities I need to run my program.
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FunOnTheCheap {

    public static void main(String[] args) {

        // Here we are initializing the class Random and Scanner which are used
        // accordingly. Random to pick a random integer, and scanner to read from the
        // terminal when an user types something.
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Here we are starting the loop of the game. Essentially this will always until
        // it is manually closed.
        while (true) {
            // To add a bit of spice I thought that having 50% the program not to open and
            // guide the user to a rick roll was a bit fun.
            if (Math.random() < 0.5) {
                // Here I display such messages
                System.out.println(
                        "Oh, no! Your 'FunOnTheCheap' premium subscription just expired! In order to play you must have an active subscription.");
                System.out.println("Renew here: https://tinyurl.com/funOnTheCheapRenewPremium");
                System.out.println("Try relaunching the program after renewal. :)");
                return;
            }

            // There's also a chance (~50%) that when the user guesses right, it outright
            // tells them that they were wrong.
            // However, we want to explicitly tell the users we did this when they exit the
            // game. The only problem is that I only found a way to trigger this when the
            // program shutdown
            // Gracefully. If they just Ctrl + C, the message doesn't trigger.
            boolean confess = false;

            // I am printing the difficulty options and some intro message.
            System.out.println("Welcome to Fun On The Cheap! (It is just a guess the number game)");
            System.out.println("1 - Easy (1 to 10)");
            System.out.println("2 - Medium (1 to 50)");
            System.out.println("3 - Hard (1 to 100)");
            System.out.println("4 - Impossible (1 to 1,000,000)");
            System.out.println("5 - Lottery Level (1 to 300,000,000)");
            System.out.print("Enter a number (1-5) to select difficulty (or type 'q' to quit): ");

            // The variable input is initialized to essentially what the user enters.
            String input = scanner.nextLine().trim();

            // We first should check if the user wants to leave.
            if (input.equalsIgnoreCase("q")) {
                // Say bye to the user.
                System.out.println("Bye now!");
                // If we just return nothing the program will end.
                return;
            }

            // Here we are checking if what is being read from the terminal (the user input,
            // which is a string by default)
            // is equal to one of the difficulty values.
            // If it isn't then it says to please enter a whole number from 1 to 5.
            if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
                    && !input.equals("5")) {
                System.out.println("Please enter a whole number from 1 to 5 in order to select a matching difficulty.");
                continue;
            }

            // We set the difficulty as an integer and using the value that the user
            // entered.
            int difficulty = Integer.parseInt(input);
            // we initialize the maxRange variable.
            int maxRange = 0;

            // Using switch we setup the maxRange according to the difficulty.
            switch (difficulty) {
                case 1 -> maxRange = 10;
                case 2 -> maxRange = 50;
                case 3 -> maxRange = 100;
                case 4 -> maxRange = 1000000;
                case 5 -> maxRange = 30000000;
                default -> {
                }
            }

            // Then we select a random number from the maxRange, we have to do +1 to
            // guarantee it is between the range of maxRange because it nextInt is not
            // inclusive.
            int targetNumber = random.nextInt(maxRange) + 1;
            // We init an empty Array in order to save numbers that the user has guessed,
            // this is used for telling the user they already guessed that number
            // and saving them an attempt.
            ArrayList<Integer> guessed = new ArrayList<>();
            // we init the attempts variable to 3. Which is the number of times the user can
            // guess wrong without losing.
            int attempts = 3;

            // Here while the user still has attempts left
            while (attempts > 0) {
                // We tell the user to guess a number
                System.out.print("Guess the number: ");
                // we redeclare the input variable as the new input from the user
                input = scanner.nextLine();
                // Here we use regex which in this specific case reads as \d+, we have to use \\
                // because as a string \ escapes a character so, using \\ is the way to go.
                // additionally, "d" means numbers starting from 0 to 9.
                // + basically means allow for repetition. so 123.. etc is possible and it
                // matches.
                if (!input.matches("\\d+")) {
                    // We let the user know that the input is invalid if it doesn't matches that
                    System.out.println("Invalid input. Please enter a whole number.");
                    // we continue here to basically re enter the loop and trigger "guess the
                    // number" from the lines above.
                    continue;
                }

                // if the user correctly entered a number then we init the variable userGUess to
                // the input the user entered and converted to an int using .parseInt();
                int userGuess = Integer.parseInt(input);

                // We check the list of guesses the user has already done, and if what is
                // currently being guessed is in the list of guesses then we tell the user to
                // try again.
                if (guessed.contains(userGuess)) {
                    System.out.println("You already guessed that number! Try again.");
                    // again, we use continue to re-enter the while loop and trigger a "guess the
                    // number" from the start of the loop.
                    continue;
                }

                // if the guess is valid and has not been seen before, then we add that number
                // to the list of guesses.
                guessed.add(userGuess);

                // if the user guess matches the originally picked random number, then the
                // following happens.
                if (userGuess == targetNumber) {
                    // Heres the fun part. The user actually has 50% chance of "winning" even after
                    // guessing correctly.

                    // Here we are comparing the result of Math.random (which is from 0 to 1 the
                    // value) to see if it is less than 0.5, which essentially
                    // would make this if statement be true about 50% of the time

                    // We are checking if attempts is equal to 1, TRUE and if the 50% chance of happening is actually triggering.

                    // Just to add a bit of spice I made it to only run on the last chance! Which is being done by checking if attempts = 1
                    if (attempts == 1 && Math.random() < 0.99) {
                        confess = true; // Here we are re-declaring the variable confess with the value "true" in order
                                        // to tell to the users that they actually guessed but we
                        // told them otherwise.
                        System.out.printf("Sorry you lose. Better luck next time!");
                        // We do break to leave the while loop
                        break;
                    } else {
                        // if the user wins, we congratulate and use break again to leave the while loop
                        System.out.println("Congratulations! You guessed the number!");
                        break;
                    }
                } else {
                    // However, if the user doesn't answers correctly we subtract -1 from the number
                    // of attempts and inform the user.
                    attempts--;
                    if (attempts > 0) {
                        // If the user still has attempts we let the user know how many are left and
                        // that the guess was wrong.
                        System.out.printf("Nope, that's not it. %d attempts left.%n", attempts);
                    }
                }
                // If the user runs out of attempts we tell them what the number was and at this
                // point, the program will ask if the user wants to play again.
                if (attempts == 0) {
                    System.out.printf("Nice tries! The number was %d.%n", targetNumber);
                }
            }

            System.out.print("Do you want to play again? (Y/N): ");
            input = scanner.nextLine();
            // Here we prompt the user to see if they want to play again or not, and
            // depending on the input, we break the main while loop or continue running it.
            // In this specific case we are checking that the input isnt equal to Y (lower
            // or capitalized)
            if (!input.equalsIgnoreCase("Y")) {
                // This will trigger if the user types anything that isnt Y, assuuming the user
                // wants to leave.
                if (confess) {
                    // Here we are confessing the user, that we essentially told them they didnt
                    // guess right, when they actually did.
                    System.out.println(
                            "The guilt is eating me alive, you actually guessed right, but I said you didn't T_______________T please forgive me.");
                }
                // We say bye to the user
                System.out.println("Thanks for playing! Goodbye!");
                // We break off the main while loop.
                break;
            }
        }
        // Here we must close the scanner, because it keeps listening and is a memory
        // leak?
        scanner.close();
    }
}
