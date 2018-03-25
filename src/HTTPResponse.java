
public class HTTPResponse {

	String Status;
    String Version;
    String Timestamp;
    String Format;
    String Connection;
    Object payload;
    
    public HTTPResponse(
    		String Status,
            String Version,
            String Timestamp,
            String Format,
            String Connection,
            Object payload) {
    	
    	this.Status = Status;
        this.Version = Version;
        this.Timestamp = Timestamp;
        this.Format = Format;
        this.Connection = Connection;
        this.payload = payload;
	}
}