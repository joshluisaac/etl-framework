package com.kollect.etl.entity;

public class DataProfiler {
    Integer id;
    String name, base_path, prefix, cloneAs, cloneBeforeUnique, cloneFile, uniqueKeyFields, uniqueKeyIndex, generateHash,
    regex, replacement;
    Integer expectedLength;

    public DataProfiler(String name, String base_path, String prefix, String cloneAs, boolean cloneBeforeUnique,
                        boolean cloneFile, String uniqueKeyFields, String uniqueKeyIndex,
                        boolean generateHash, String regex, String replacement, Integer expectedLength) {
    }
}
