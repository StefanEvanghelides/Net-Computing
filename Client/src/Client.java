import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Client {
	
	public Client() {
		
	}

	public String GET(String urlToRead) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}
	
	public static void main(String[] args) {
		Client A = new Client();

		String result;
		if(args.length == 0) {
			result = "nothing to show";
			return;
		}

		try {
			result = A.GET(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail!";
		}
		
		System.out.println("Result: " + result);

	}

}
