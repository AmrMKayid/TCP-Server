

public class HTTPRequest {

    String Method;
    String URL;
    String Version;
    String Host;
    String AcceptedFormat;
    String Connection;
    String UserName;

    public HTTPRequest(
            String Method,
            String URL,
            String Version,
            String Host,
            String AcceptedFormat,
            String Connection, String UserName) {

        this.Method = Method;
        this.URL = URL;
        this.Version = Version;
        this.Host = Host;
        this.AcceptedFormat = AcceptedFormat;
        this.Connection = Connection;
        this.UserName = UserName;
    }


}
