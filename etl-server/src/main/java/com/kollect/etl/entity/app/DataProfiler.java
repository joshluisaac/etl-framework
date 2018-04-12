package com.kollect.etl.entity.app;

public class DataProfiler {
    Integer id;
    String name, base_path, prefix, cloneAs;
    boolean cloneBeforeUnique, cloneFile;
    String uniqueKeyFields, uniqueKeyIndex;
    boolean generateHash;
    String regex, replacement;
    Integer expectedLength;

    public DataProfiler(String name, String base_path, String prefix, String cloneAs, boolean cloneBeforeUnique,
                        boolean cloneFile, String uniqueKeyFields, String uniqueKeyIndex,
                        boolean generateHash, String regex, String replacement, Integer expectedLength) {
        this.name = name;
        this.base_path = base_path;
        this.prefix=prefix;
        this.cloneAs=cloneAs;
        this.cloneBeforeUnique=cloneBeforeUnique;
        this.cloneFile=cloneFile;
        this.uniqueKeyFields=uniqueKeyFields;
        this.uniqueKeyIndex=uniqueKeyIndex;
        this.generateHash=generateHash;
        this.regex=regex;
        this.replacement=replacement;
        this.expectedLength=expectedLength;
    }

    public void setId(int id) {
        this.id = id;
    }
}
