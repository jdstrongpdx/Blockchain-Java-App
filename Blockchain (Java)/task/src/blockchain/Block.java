package blockchain;

import java.util.Date;
import java.util.List;

/**
 * Represents a mined block as part of a Blockchain.  Stores unmodifiable information about the block at the time of
 * creation, get methods, toString method, and getCurrentHash - which is used to verify the validity of the Block current_hash.
 */
public class Block {
    private final int id;
    private final String miner;
    private final Long timestamp;
    private final String previous_hash;
    private final String current_hash;
    private final List<Message> messages;
    private final String messageString;
    private final String authinticationMessageString;
    private final String magic_number;
    private int duration;

    public Block(int id, String miner, Long timestamp, String previous_hash, String current_hash, List<Message> messages, String messageString, String authinticationMessageString, String magic_number) {
        this.id = id;
        this.miner = miner;
        this.timestamp = timestamp;
        this.previous_hash = previous_hash;
        this.current_hash = current_hash;
        this.magic_number = magic_number;
        this.messages = messages;
        this.messageString = messageString;
        this.authinticationMessageString = authinticationMessageString;
    }

    public int getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previous_hash;
    }

    public String getCurrentHash() {
        return current_hash;
    }

    public String getMagicNumber() {return magic_number;}

    public int getDuration() {
        return duration;
    }


    public String getMiner() {
        return miner;
    }

    /**
     * ONLY setter field - all others are final for security reasons
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * DO NOT MODIFY - returns the fields used at the time the block was mined to validate an accurate hash of this and successive blocks..
     */
    public String getHashString() {
        return this.id + this.miner + this.timestamp + this.magic_number + this.previous_hash + this.authinticationMessageString;
    }

    @Override
    public String toString() {
        return "Block:\n" +
        "Created by " + this.miner + "\n" +
        this.miner + " gets 100 VC\n" +
        "Id: " + this.id + "\n" +
        "Timestamp: " + this.timestamp + "\n" +
        "Magic number: " + this.magic_number + "\n" +
        "Hash of the previous block:\n" + this.previous_hash + '\n' +
        "Hash of the block:\n" + this.current_hash + '\n' +
        this.messageString;
    }
}
