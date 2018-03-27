import java.io.Serializable;

public class HTTPRequest implements Serializable {

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

        this.Method = Method.toUpperCase();
        this.URL = URL;
        this.Version = Version;
        this.Host = Host;
        this.AcceptedFormat = AcceptedFormat;
        this.Connection = Connection;
        this.UserName = UserName;
    }
    
    public String toString() {
		return Method+" "+URL+" "+Version+"\n"+
			   Host+"\n"+
			   AcceptedFormat+"\n"+
			   Connection;
    	
    }

//get mo 1.1 chrome:D png close
}
