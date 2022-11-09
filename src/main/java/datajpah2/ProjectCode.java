package datajpah2;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ProjectCode {
    private final String name;
    private  final  String code;

    public ProjectCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
