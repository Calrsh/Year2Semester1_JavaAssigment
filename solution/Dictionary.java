package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary implements IDictionary {
    HashMap<String, IWord> wordDictionary = new HashMap<>();

    @Override
    public IWord getWord(String word) {
        return wordDictionary.get(word);
    }

    @Override
    public void addWord(IWord word) {
        if (wordDictionary.containsKey(word.getWord())) {
            throw new IllegalArgumentException();
        }
        wordDictionary.put(word.getWord(), word);
    }

    @Override
    public int getWordCount() {
        //Can just return the amount of elements in the hash
        return wordDictionary.size();
    }

    @Override
    public int getPronunciationCount() {
        //Initialise a count
        int count = 0;
        //Iterate for each of the values
        for (IWord i : wordDictionary.values()) {
            //Add the amount of pronunciations to the size
            count += i.getPronunciations().size();
        }

        return count;
    }

    @Override
    public void parseDictionaryLine(String line) {
        //Declarations
        String[] wordArray;
        Phoneme tempPhoneme;
        Pronunciation tempPronunciation = new Pronunciation();
        int stress;

        //Remove the (n) and comments from the line
        line = line.replaceAll("#.*|(\\([0-9]\\))", "");


        //Split the string into string array
        String[] myStrings = line.split("\s"); // split between whitespaces

        //ignore the first element of the array
        for (int i = 1; i < myStrings.length; i++) {
            //split based on digit if there is 1
            wordArray = myStrings[i].split("((?<=[0-9])|(?=[0-9]))");
            //Regex expression to remove
            stress = myStrings[i].replaceAll("[^0-9]", "").isEmpty() ? -1 :
                    Integer.parseInt(myStrings[i].replaceAll("[^0-9]", ""));
            tempPhoneme = new Phoneme(Arpabet.valueOf(wordArray[0]), stress);
            tempPronunciation.add(tempPhoneme);
        }

        //Check if the word already exists
        if (wordDictionary.containsKey(myStrings[0])) {
            //Get the string and set it as our word
            Word current4 = (Word) wordDictionary.get(myStrings[0]);
            //Add pronunciation to that word
            current4.addPronunciation(tempPronunciation);
            //Update it
            wordDictionary.replace(myStrings[0], current4);
        } else {
            Word current4 = new Word(myStrings[0]);
            current4.addPronunciation(tempPronunciation);
            wordDictionary.put(myStrings[0], current4);
        }

    }

    @Override
    public void loadDictionary(String fileName) {
        //Try catch - in case file is not found
        try {
            //Create a new instance of our file
            File theFile = new File("cmudict.dict.txt");
            //Create a scanner to read each line
            Scanner myReader = new Scanner(theFile);
            //While the file still has lines
            while (myReader.hasNextLine()) {
                //Read the line
                String line = myReader.nextLine();
                //Parse the line
                parseDictionaryLine(line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    @Override
    public Set<String> getRhymes(String word) {
        //Create a new set
        Set<String> returnSet = new LinkedHashSet<>();
        //The set should always return 1 thing at the very least
        returnSet.add(word);

        //Get the word object from the dictionary
        IWord iWord = wordDictionary.get(word);
        //Get the set of pronunciations for that word - each of these must be checked
        Set<IPronunciation> wordPronunciations = iWord.getPronunciations();
        //Getting the entry sets - make a stream - iterate for each of those entries in the
        wordDictionary.entrySet().parallelStream().forEach(entry -> {
            //Make a new set for our current dictionary word where we get its pronunciation list
            Set<IPronunciation> entryPronunciations = entry.getValue().getPronunciations();
            //For each way to say in our set of pronunciations
            for (IPronunciation pronunciation : entryPronunciations) {
                //For each pronunciation in the word we are looking at
                for (IPronunciation p : wordPronunciations) {
                    //Check if it rhymes with the other one
                    if (p.rhymesWith(pronunciation)) {
                        //For each to write to shared variable
                        synchronized (returnSet) {
                            //Add that entry - the string to our set
                            returnSet.add(entry.getKey());
                        }
                        //Acts as a break if a rhyme is added - this is because there is no point in checking every
                        //pronunciation if we found 1 that works
                        return;

                    }

                }

            }
        });
        //Return that set of strings - every word that rhymes with our word
        return returnSet;
    }
}
