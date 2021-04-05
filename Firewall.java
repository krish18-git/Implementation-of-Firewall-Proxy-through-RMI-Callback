import java.util.Arrays; 
public class Firewall {
    private String blocked_words[]={"fail","defeat","loser","nonsense","idiot"};
    String checkWord(String word){
        int n=word.length();
        String star="*";
        if(Arrays.asList(blocked_words).contains(word)){
            return star.repeat(n);
        }
        return word;
    }
}