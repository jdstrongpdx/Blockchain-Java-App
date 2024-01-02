package blockchain;

import java.util.concurrent.ExecutionException;

/**
 * This program is an implementation of a blockchain and does the following:
 * 1. Creates a blockchain service that initializes other needed services,
 *      initiates mining processes, stores mined blocks, and validates blocks.
 *      When the needed structures have been generated, the blockchain service will use
 *      a Java ExecutorService to load mining tasks to each non-main core available.
 *      The ExecutorService is executed with the invokeAny method - which kills all threads
 *      once a single thread is successful.  This mining process runs in a loop until the
 *      requested number of blocks have been generated.
 * 2. Creates a message service that creates and validates messages/transactions
 *      between users.
 * 3. Creates users with public/private keys, cryptographic signatures, and VC (virtual coin)
 *      balances.
 * 4. Creates a zero regulation service that increases/decreases the mining complexity
 *      requirements over time.
 *
 * Personal Note: this is the final project for the Hyperskill (JetBrains Academy) developer
 * certificate and is the hardest Java program currently offered.  It is my first multi-threaded
 * Java application. It was a really rewarding challenge to learn how to program.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Blockchain blockchain = new Blockchain();
        blockchain.mineBlocks(15L);
    }
}
