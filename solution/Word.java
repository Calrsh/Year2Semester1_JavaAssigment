package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IWord;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;


import java.util.HashSet;
import java.util.Set;

public class Word implements IWord {
    //The word that is declared
    private String word;
    //This holds the ways to pronounce
    private Set<IPronunciation> waysToSay;

    /**
     * Default constructor
     * @param word a string, the word we want to analyse
     */
    public Word (String word){
        this.word = word;
        waysToSay=new HashSet<>();
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public void addPronunciation(IPronunciation pronunciation) {
        //Null checker
        if (pronunciation == null){
            throw new IllegalArgumentException();
        }
        //
        waysToSay.add(pronunciation);

    }

    @Override
    public Set<IPronunciation> getPronunciations() {
        return waysToSay;
    }
}
