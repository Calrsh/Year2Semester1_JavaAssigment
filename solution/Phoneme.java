package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;

public class Phoneme implements IPhoneme {


    private final int stress;
    private final Arpabet phoneme;

    /**
     * Defualt constructor - will throw IllegalArgumentException for multiple cases.
     *
     * @param phoneme the enum value for the Arpabet.
     * @param stress an integer that should be in range -1 to 2
     */
    public Phoneme(Arpabet phoneme, int stress) {
        if(phoneme == null){
            throw new IllegalArgumentException();
        }
        //Throw an exception for these cases:
        //The stress is not in the range -1 to 2 inclusive
        if(!(stress >= -1 && stress <= 2)){
            throw new IllegalArgumentException();
        }
        //if the stress is not -1 and the phoneme is not a vowel
        else if (stress != -1 && !phoneme.isVowel()){
            throw new IllegalArgumentException();
        }
        //if the stress is -1 and phoneme is a vowel
        else if (stress == -1 && phoneme.isVowel()){
            throw new IllegalArgumentException();
        }

        // We can set if all clear.
        this.phoneme = phoneme;
        this.stress = stress;
    }


    @Override
    public Arpabet getArpabet() {
        return phoneme;
    }

    @Override
    public int getStress() {

        return stress;
    }

    @Override
    public boolean hasSameArpabet(IPhoneme other) {
        //If the value passed is null throw exception
        if(other == null){
            throw new IllegalArgumentException();
        }
        //Check if the objects are equal and return true if so
        if ((other.getArpabet()).equals(phoneme)){
            return true;
        }
        //else false
        return false;
    }
}
