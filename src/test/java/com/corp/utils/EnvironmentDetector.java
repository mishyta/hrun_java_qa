package com.corp.utils;

public class EnvironmentDetector {

    public static final boolean JNKENV = detectJenkins();

    /**
     * @return return true if environment is contains "jenkins"
     */
    private static boolean detectJenkins() {
        return System.getenv()
                .toString()
                .toLowerCase()
                .contains("jenkins");
    }

}

