package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;


import java.util.ArrayList;
import java.util.List;

public class Pronunciation implements IPronunciation {
    List<IPhoneme> pronunList = new ArrayList<>();

    @Override
    public void add(IPhoneme phoneme) {
        //Throw exception if value passed is null
        if (phoneme == null) {
            throw new IllegalArgumentException();
        }
        //Add to the end of the list
        pronunList.add(phoneme);
    }

    @Override
    public List<IPhoneme> getPhonemes() {
        return pronunList;
    }

    @Override
    public int findFinalStressedVowelIndex() {
        //Temporary values
        int index = -1;//holds the index we wish to return
        int currentStress;//will hold the stress value of each item
        boolean flag = false;//flag to stop looking at if statements when met condition
        boolean flag2 = false;

        //Reverse
        for (int i = pronunList.size() - 1; i > -1; i--) {
            currentStress = pronunList.get(i).getStress();
            //We wish to find this as the primary stress
            if (currentStress == 1) {
                //If we find it break out the loop and save index
                index = i;
                i = 0;
            } else if (!flag && currentStress == 2) {
                //Otherwise we want to look for the last 2
                //If found stop looking for 2, only look for updates of 1
                index = i;
                flag = true;
                flag2 = true;

            }//Backup in case there is no stress, but vowels
            else if (!flag2 && currentStress == 0) {
                index = i;
                flag2 = true;
            }
        }
        //Returns -1 if there are no vowels
        return index;
    }

    @Override
public boolean rhymesWith(IPronunciation other) {
    // Capture the other list
    List<IPhoneme> otherList = other.getPhonemes();

    // Find the stressed vowels to compare
    int thisIndex = findFinalStressedVowelIndex();
    int otherIndex = other.findFinalStressedVowelIndex();

    // If either side has no stressed vowels - no rhyme
    if (thisIndex == -1 || otherIndex == -1) {
        //We can stop the function
        return false;
    }

    // Calculate the number of elements from the stressed vowel to the end - this should be the same
    int thisRemaining = pronunList.size() - thisIndex;
    int otherRemaining = otherList.size() - otherIndex;

    // If the number of elements from the stressed vowel to the end is not the same, they cannot rhyme
    if (thisRemaining != otherRemaining) {
        return false;
    }

    // Check if all corresponding phonemes from the stressed vowel to the end are the same
    for (int pos = 0; pos < thisRemaining; pos++) {
        //If the two compared
        if (!pronunList.get(thisIndex + pos).hasSameArpabet(otherList.get(otherIndex + pos))) {
            return false;
        }
    }

    // If all checks passed, the words rhyme
    return true;
}



}



