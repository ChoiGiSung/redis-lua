# Redis Lua Script Master Execution Investigation

This project was created to investigate an issue where Lua scripts were not being executed on the **master** node in a Redis Cluster.

**Related issue:** [redis/lettuce#1244](https://github.com/redis/lettuce/issues/1244)

---

## ✅ Summary

After verifying the behavior in the current version, we found that Lettuce classifies Lua scripts as `Write` operations.  
As a result, it assigns a **write connection**, which ensures that the script is executed on the **master** node.

---

## 🐞 Debugging

To trace how the connection is determined, start debugging from the following line:  
[ClusterDistributionChannelWriter.java#L179](https://github.com/redis/lettuce/blob/c898733464a8dba0ff13bb718901a347cac22bc5/src/main/java/io/lettuce/core/cluster/ClusterDistributionChannelWriter.java#L179)

---

## 📄 Blog

A summary of the investigation and flow is available in the following blog post:  
[https://gisungcu.tistory.com/753](https://gisungcu.tistory.com/753)
