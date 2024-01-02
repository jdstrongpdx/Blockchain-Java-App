package blockchain;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a user with a randomly generated public/private keys for simplicity in leiu of secure key generation and
 * storing methods.  Each user has a balance of virtualCoins, which is used in the MessageService messages/transactions.
 */
public class User {
    private static long counter = 0;
    long userId;
    String name;
    private String privateKey;
    String publicKey;
    String signature;
    long virtualCoins;

    public User(String name) {
        this.userId = counter++;
        this.name = name;
        setPrivateKey();
        setPublicKeyKey();
        this.signature = generateSignature();
        this.virtualCoins = 100;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public String getSignature() {
        return this.signature;
    }

    private void setPrivateKey () {
        this.privateKey = generateKey();
    }

    private void setPublicKeyKey () {
        this.privateKey = generateKey();
    }

    public long getVirtualCoins() {
        return virtualCoins;
    }

    public void adjustVirtualCoins(long virtualCoins) {
        this.virtualCoins += virtualCoins;
    }

    public boolean validateSignature(User user, String signature) {
        return Objects.equals(user.getSignature(), user.generateSignature()) && Objects.equals(user.generateSignature(), signature);
    }

    public String generateSignature() {
        String content = "User: " + '\n' +
                "userId: " + + this.userId + '\n' +
                "Name: " + this.name + '\n' +
                "Public Key: " + this.publicKey + '\n' +
                "Private Key: " + this.privateKey;
        return ApplySha256.encode(content);
    }

    private String generateKey() {
        Random r = new Random();
        String key = "";
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 8; i++) {
            key += alphabet.charAt(r.nextInt(alphabet.length()));
        }
        return key;
    }
}

