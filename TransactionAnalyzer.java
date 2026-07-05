import java.util.*;

public class TransactionAnalyzer {

    public static class Transaction {
        public String transactionId;
        public String userId;
        public int amount; // in cents (e.g., 1000 = $10.00)
        public String merchantId;
        public long timestamp; // Unix timestamp in seconds

        public Transaction(String transactionId, String userId, int amount, String merchantId, long timestamp) {
            this.transactionId = transactionId;
            this.userId = userId;
            this.amount = amount;
            this.merchantId = merchantId;
            this.timestamp = timestamp;
        }
    }

    public static class AnalysisResult {
        public int totalRevenue; // Sum of unique transactions
        public int duplicateCount; // Number of duplicates detected

        public AnalysisResult(int totalRevenue, int duplicateCount) {
            this.totalRevenue = totalRevenue;
            this.duplicateCount = duplicateCount;
        }
    }

    /**
     * Processes a list of transactions to find duplicates and calculate settled
     * revenue.
     * Assume the transactions list is guaranteed to be sorted by timestamp in
     * ascending order.
     */
    public static AnalysisResult processTransactions(List<Transaction> transactions) {
        Map<String, Transaction> lastTransactionMap = new HashMap<>();
        int totalRevenue = 0;
        int duplicateCount = 0;

        for (Transaction tx : transactions) {
            String key = tx.userId + ":" + tx.amount + ":" + tx.merchantId;
            // Check if it does contain it and it is less than 60 second which mean it is a
            // duplicate
            if (lastTransactionMap.containsKey(key)) {
                if (tx.timestamp - lastTransactionMap.get(key).timestamp <= 60) {
                    duplicateCount++;
                    continue;
                }
            }
            // If it does contain it and it more than 60 second, then it no longer a
            // duplicate but instead a transaction
            else {
                totalRevenue = tx.amount + totalRevenue;
                lastTransactionMap.put(key, tx);
            }
        }
        return new AnalysisResult(totalRevenue, duplicateCount);
    }

    public static void main(String[] args) {
        // You can use this to test your logic locally
        List<Transaction> txList = new ArrayList<>();
        txList.add(new Transaction("tx1", "userA", 5000, "merchX", 1000));
        txList.add(new Transaction("tx2", "userA", 5000, "merchX", 1030)); // Duplicate (within 30s)
        txList.add(new Transaction("tx3", "userB", 2500, "merchY", 1040));
        txList.add(new Transaction("tx4", "userA", 5000, "merchX", 1120)); // Not a duplicate (> 60s from tx2)

        AnalysisResult result = processTransactions(txList);
        System.out.println("Total Revenue: " + result.totalRevenue); // Expected: 7500 (tx1 + tx3)
        System.out.println("Duplicates: " + result.duplicateCount); // Expected: 1 (tx2)
    }
}