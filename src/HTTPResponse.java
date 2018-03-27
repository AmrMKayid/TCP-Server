import java.io.FileInputStream;
import java.io.Serializable;

public class HTTPResponse implements Serializable {

	String Status;
    String Version;
    long Timestamp;
    String Format;
    String Connection;
    FileInputStream payload;
    
    public HTTPResponse(
    		String Status,
            String Version,
            long Timestamp,
            String Format,
            String Connection,
            FileInputStream payload) {
    	
    	this.Status = Status;
        this.Version = Version;
        this.Timestamp = Timestamp;
        this.Format = Format;
        this.Connection = Connection;
        this.payload = payload;
	}
    
    public String toString() {
		return Status+" "+Version+"\n"+
			   Timestamp+"\n"+
			   Format+"\n"+
			   Connection;
    	
    }
}