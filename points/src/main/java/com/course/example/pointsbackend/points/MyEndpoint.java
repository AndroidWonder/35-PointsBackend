/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.course.example.pointsbackend.points;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "points.pointsbackend.example.course.com",
                ownerName = "points.pointsbackend.example.course.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "echo")
    public MyBean echo(@Named("name") String name, User user) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);
        return response;
    }

    /**
     * A simple endpoint method that takes a name, reverses the letters and says Hi back
     */
    @ApiMethod(name = "echoRev")
    public MyBean echoRev(@Named("name") String name) {
        StringBuffer str = new StringBuffer(name);
        String revname = str.reverse().toString();
        MyBean response = new MyBean();
        response.setData("Backwards, " + revname);
        return response;
    }

    /**
     * A simple endpoint method that takes a name and calculates how long it is
     */
    @ApiMethod(name = "howLong")
    public MyBean howLong(@Named("name") String name, User user) {
        MyBean response = new MyBean();
        response.setLength(name.length());
        return response;
    }

    /**
     * A simple endpoint method that takes a name and calculates if it's a palindrome
     */
    @ApiMethod(name = "isPalindrome")
    public MyBean isPalindrome(@Named("name") String name, User user) {
        MyBean response = new MyBean();

        StringBuffer str = new StringBuffer(name);
        String reverse = str.reverse().toString();

        if (name.equals(reverse))
            response.setPalindrome(true);
        else
            response.setPalindrome(false);

        return response;
    }

    /**
     * A simple endpoint method that takes a sentence and converts to Pig Latin
     */
    @ApiMethod(name = "pigLatin")
    public MyBean pigLatin(@Named("name") String name, User user) {
        MyBean response = new MyBean();

        String[] tokens = name.split(" +");

        //get rid of trailing.
        int words = tokens.length;
        int letters = tokens[words-1].length();
        if (tokens[words-1].substring(letters-1).equals(".")) {
            tokens[words-1] = tokens[words-1].substring(0, letters-1);
        }

        //loop over all words
        boolean loop = true;
        int pointer = 0;

        for (int i = 0; i < tokens.length; i++) {

            pointer=0;
            loop = true;

            //loop over letters to find first vowel
            while(loop){

                switch (tokens[i].substring(pointer,  pointer+1)){

                    case "a":
                    case "e":
                    case "i":
                    case "o":
                    case "u":
                    case "A":
                    case "E":
                    case "I":
                    case "O":
                    case "U":   {

                        String tail = tokens[i].substring(0,  pointer);
                        String head = tokens[i].substring(pointer);
                        tokens[i] = head + tail + "ay";
                        tokens[i] = tokens[i].toLowerCase();
                        loop = false;
                        break;
                    }

                    default:
                        //what about words like "my"
                        if (pointer >= tokens[i].length() - 1 ){

                            tokens[i] = tokens[i] + "ay";
                            loop = false;
                        }
                        else
                            pointer++;


                }//switch


            }//while

        }//for

        String pigLine = "";

//compose response
        for (int i=0; i<tokens.length;i++){
            pigLine = pigLine + tokens[i] + " ";
        }

        response.setData("PigLatin, " + pigLine);


        return response;
    }

}
