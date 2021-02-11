package util;
public enum EnvironmentType {


    MobileAutomation("", ""); //

    private final String host, env;

    EnvironmentType(String host, String env) {
        this.host = host;
        this.env = env;
    }

    public String getHost() {
        return host;
    }

    public String getEnv() {
        return env;
    }

}


