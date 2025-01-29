import java.util.ArrayList;
import java.util.regex.Pattern;

public class CaesarCipher extends Cipher {

    private int key;

    public CaesarCipher(int k) {
        key = k;
        alphabet = getAlphabet(new String[] {"lower"});
    }

    public CaesarCipher(int k, String[] names) {
        key = k;
        alphabet = getAlphabet(names);
    }

    public String encrypt(String plaintext) {
        char[] chars = plaintext.toCharArray();
        StringBuilder ciphertext = new StringBuilder();
        for (char c : chars) {
            if (!validate(c)) {
                ciphertext.append(c);
                continue;
            }
            int cIdx = alphabet.indexOf(c);
            int kIdx = cIdx + key;
            while (kIdx >= alphabet.size()) {
                kIdx -= alphabet.size();
            }
            ciphertext.append(alphabet.get(kIdx));
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        char[] chars = ciphertext.toCharArray();
        StringBuilder plaintext = new StringBuilder();
        for (char c : chars) {
            if (!validate(c)) {
                plaintext.append(c);
                continue;
            }
            int cIdx = alphabet.indexOf(c);
            int kIdx = cIdx - key;
            while (kIdx < 0) {
                kIdx += alphabet.size();
            }
            plaintext.append(alphabet.get(kIdx));
        }
        return plaintext.toString();
    }

    public void crack(String ciphertext) {
        Dictionary d = new Dictionary();
        String bestPlaintext = "";
        int bestWordCount = 0;
        int bestKey = 0;
        for (int i = 0; i < alphabet.size(); i++) {
            key = i;
            String plaintext = decrypt(ciphertext);
            String[] words = plaintext.split(Pattern.quote(" "));
            int validWordCount = 0;
            for (String word: words) {
                if (d.isWord(word)) {
                    validWordCount += 1;
                }
            }
            if (validWordCount > bestWordCount) {
                bestPlaintext = plaintext;
                bestWordCount = validWordCount;
                bestKey = key;
            }
        }
        System.out.printf("%d:\t%s %d%n", bestKey, bestPlaintext, bestWordCount);
    }

    public static void main(String[] args) {
        //int k = (int)(Math.random() * 30 + 13);
        CaesarCipher cipher = new CaesarCipher(0, new String[] {"lower", "upper", "punctuation", "whitespace", "symbols"});
        //String plaintext = "The quick brown fox jumped over the lazy dogs.";
        //System.out.println(plaintext);
        //String ciphertext = cipher.encrypt(plaintext);
        String ciphertext = "]31iO=3OP27\"T6vO]31iO=3OP27\"T6vO`WP8o7OV32TO\"X8WO8WP8OQ3$tO.O\"32ST6CO|39O]><iO=3OP27\"T6v";//"hxd fruu ansxrln cx qnja cqjc wx mrbjbcna qjb jllxvyjwrnm cqn lxvvnwlnvnwc xo jw nwcnayarbn fqrlq hxd qjen anpjamnm frcq bdlq neru oxankxmrwpb.";
        System.out.println(ciphertext);
        //String decrypt = cipher.decrypt(ciphertext);
        //System.out.println(decrypt);
        cipher.crack(ciphertext);
    }
}
