# DataStructureComprehension

A repository dedicated to breaking down, implementing, and optimizing data structures for real-world production scenarios.

## Problem 1: Time-Based Transaction Log Analyzer
* **Core Concept:** Sliding Window (Time-Bound) & Compound Key Hashing
* **Language:** Java 17

### The Challenge
Process a stream of financial transactions to calculate unique settled revenue and filter out duplicate charges that occur within a moving 60-second window.

### Key Takeaways
* **Data Structure Choice:** Used a `HashMap` to achieve $O(1)$ average time complexity lookups, avoiding a costly $O(N^2)$ nested loop scan.
* **Compound Key Design:** Constructed a unique string key combining `userId:amount:merchantId` to safely prevent collisions between different users at the same merchant.
* **State Control:** Structured the conditional checks to handle edge cases where a third transaction must be evaluated against the last *valid* transaction, ensuring data integrity in continuous streams.
