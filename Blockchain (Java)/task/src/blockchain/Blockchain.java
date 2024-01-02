package blockchain;

import java.util.*;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;


public class Blockchain {
    static int id = 1;
    // stores generated blocks
    static ArrayList<Block> blockchains;
    // below variables store static information for threads to pull the same values
    static MessageService messageService;
    static ZeroService zeroService;
    static List<Message> messageList;
    static String messageString;
    static String messageValidationString;
    static String previousHash;

    public Blockchain() {
        blockchains = new ArrayList<>();
        messageService = new MessageService();
        zeroService = new ZeroService();
    }

    /**
     * Initiates the services and processed needed to mine blocks. For each block to mine,
     * the program will create threads for the number of cores - 1 and use the ExecutorService
     * invokeAny method to run all threads simultaneously - exiting after the first successful
     * return.
     *
     * @param quantity - the number of blocks to mine
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void mineBlocks(long quantity) throws ExecutionException, InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors() - 1;

        for (int i = 0; i < quantity; i++) {
            // capture variables to share same values across threads
            long startTime = System.currentTimeMillis();
            messageList = messageService.generateMessages();
            messageString = messageService.messagesToString(messageList);
            messageValidationString = messageService.messagesValidationString(messageList);
            if (id == 1) {
                previousHash = String.valueOf(0L);
            } else {
                previousHash = blockchains.get(blockchains.size() - 1).getCurrentHash();
            }

            // load thread tasks for cores - 1
            ExecutorService executorService = Executors.newFixedThreadPool(cores);
            Set<Callable<Block>> callables = new HashSet<>();
            for (int j = 0; j < cores; j++) {
                callables.add(this::blockMiner);
            }

            // execute all thread tasks stopping other threads after first successful return
            Block result = executorService.invokeAny(callables);

            // shutdown all remaining threads before next iteration
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(10, SECONDS)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(60, SECONDS)) {
                        //System.err.println("Pool did not terminate");
                    }
                }
            } catch (InterruptedException ex) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000;

            // save the block and print block info
            result.setDuration((int) duration);
            blockchains.add(result);
            id++;
            System.out.println(result);

            // add funds to miner account
            String miner = result.getMiner();
            messageService.addFunds(miner);

            // regulate zeros based on processing time
            zeroService.regulateZeros((int) duration);
            System.out.println();

            boolean valid = this.validate();
            if (!valid) {
                System.err.println("Error: invalid block has been added to the chain.");
                break;
            }
        }
    }

    /**
     * This method is used by multiple threads to competitively mine blocks of a dynamically
     * controlled complexity. Each thread will attempt to mine a block with the specified
     * number of leading zeros using randomization and SHA256 hash strings.
     * @return Block - a successfully mined block
     */
    public Block blockMiner() {
        String threadName = Thread.currentThread().getName();
        threadName = "miner" + threadName.charAt(threadName.length() - 1);
        String threadPreviousHash = previousHash;
        String threadMessageString = messageString;
        List<Message> threadMessageList = messageList;
        String threadMessageValidationString = messageValidationString;
        int threadZeros = zeroService.getZeros();
        int threadId = id;
        String threadCheck = "0".repeat(threadZeros);
        Random rand = new Random();
        Long timestamp = new Date().getTime();
        boolean magic = true;
        String hash = "", current_hash = "", substring = "", magic_number = "";

        while (magic) {
            magic_number = Integer.toString(rand.nextInt(99999999));
            hash = threadId + threadName + timestamp + magic_number + threadPreviousHash + threadMessageValidationString;
            current_hash = ApplySha256.encode(hash);
            substring = current_hash.substring(0, threadZeros);
            if (threadCheck.equals(substring)) magic = false;
        }
        return new Block(threadId, threadName, timestamp, threadPreviousHash, current_hash, threadMessageList, threadMessageString, threadMessageValidationString, magic_number);
    }

    /**
     *  Validates the validity of each block in the entire Blockchain
     */
    public boolean validate() {
        for (Block block: blockchains) {
            String hash = block.getHashString();
            if (!Objects.equals(block.getCurrentHash(), ApplySha256.encode(hash))) return false;
        }
        return true;
    }


}
