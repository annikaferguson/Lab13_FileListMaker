import java.util.Scanner;
public class SafeInput
{
    /**
     * PART A: getNonZeroLenString
     * use this method when we don't know what form the user's response will be,
     * but know that it must not be blank
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt a prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; //Set this to zero length. Loop runs until it isn't
        do {
            System.out.println("\n" + prompt + ""); //show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }

    /**
     * PART B: getInt
     * method that prompts the user to input any integer
     * no pre-conception of what the range for the integer might be
     */

    public static int getInt(Scanner pipe, String prompt)
    {
        int retValue = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.println("\n" + prompt + ": ");
            if(pipe.hasNextInt()) {
                retValue = pipe.nextInt();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not: " + trash);
            }
        } while (!done);

        return retValue;

    }

    /**
     * PART C: getDouble
     * method that prompts the user to input any double
     * no pre-conception of what the range might be
     * like getInt just with double
     */

    public static double getDouble(Scanner pipe, String prompt)
    {
        double retValue = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.println("\n" + prompt + ": ");
            if(pipe.hasNextDouble()) {
                retValue = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not: " + trash);
            }
        } while (!done);

        return retValue;

    }

    /**
     * PART D: getRangedInt
     * method that prompts the user to input an integer within a specified inclusive range
     * inclusive means that low and high are valid inputs
     */

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int retValue = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.println("\n" + prompt + "[" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                retValue = pipe.nextInt();
                pipe.nextLine();
                if(retValue >= low && retValue <= high)
                    done = true;
                else
                    System.out.println("\nNumber is out of range [" + low + " - " + high + "] not: " + retValue);
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not: " + trash);
            }
        } while (!done);

        return retValue;
    }

    /**
     * PART E: getRangedDouble
     * like getRangedInt but with doubles
     */

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double retValue = 0;
        String trash = "";
        boolean done = false;

        do {
            System.out.print("\n" + prompt + "[" + low + " - " + high + "]: ");
            if(pipe.hasNextDouble()) {
                retValue = pipe.nextDouble();
                pipe.nextLine();
                if(retValue >= low && retValue <= high)
                    done = true;
                else
                    System.out.println("\nNumber is out of range [" + low + " - " + high + "] not: " + retValue);
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not: " + trash);
            }
        } while (!done);

        return retValue;
    }

    /**
     *  PART F: getYNConfirm
     *  method that gets a Yes or No [Y/N} returning true for yes and false for no
     *  it should accept YnN as valid responses and loop until it gets one of them
     *  it returns true or false not "Y" or "N"
     */

    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        boolean retVal = true;
        String response = "";
        boolean gotAVal = false;

        do
        {
            System.out.print("\n" + prompt + " [Y/N] ");
            response = pipe.nextLine();
            if(response.equalsIgnoreCase("Y"))
            {
                gotAVal = true;
                retVal = true;
            } else if(response.equalsIgnoreCase("N")) {
                gotAVal = true;
                retVal = false;
            } else {
                System.out.println("\nYou must answer [Y/N]! " + response);
            }
        } while(!gotAVal);

        return retVal;
    }

    /**
     *  PART G: getRegExString
     *  method that prompts the user to input a String that matches a RegEx pattern
     */

    public static String getRegExString(Scanner pipe, String prompt, String regExPattern)
    {
        String value = "";
        boolean gotAValue = false;

        do {
            System.out.println(prompt + ": "); // show the prompt
            value = pipe.nextLine(); // input the data
            if(value.matches(regExPattern))
            {
                gotAValue = true;
            } else {
                System.out.println("\nInvalid input: " + value);
            }
        } while(!gotAValue);

        return value;
    }

    /**
     * PART H AND PROGRAM 06: Pretty Header
     * output always 60 characters wide for each line
     */

    public static void prettyHeader(String msg)
    {
        int lineLength = 0;
        boolean done = false;
        String stars = "";

        int beginSpace = 0;
        int endSpace = 0;

        beginSpace = (54 - msg.length()) / 2;
        endSpace = 54 - msg.length() - beginSpace;

        for(int c = 0; c < 60; c++) {
            System.out.print("*");
        }
        System.out.print("\n***");
        //print first group of spaces
        for(int c = 0; c < beginSpace; c++) {
            System.out.print(" ");
        }
        //message
        System.out.print(msg);
        //print last group of spaces
        for(int c = 0; c < endSpace; c++) {
            System.out.print(" ");
        }

        System.out.print("***");
        System.out.println();

        for(int c = 0; c < 60; c++) {
            System.out.print("*");
        }
    }

}
