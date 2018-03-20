import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ComplaintAPI {
	String address;
	
	public void serialize() {
		
	}

    // public JsonElement GET(String endpoint) throws IOException {
    //     HttpsURLConnection connector = this.connectToSecureServer("GET", endpoint);
    //     JsonElement payload = this.getPayloadFromServer(connector);
    //     return payload;
    // }

    public String GET(String urlToRead) throws IOException {
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

 //    /**
 //     * This method establishes the connection to the server, by setting the type of request
 //     * and the path.
 //     *
 //     * @param requestMethod the type of request that will be sent to the server.
 //     * @param endpoint      path to the server's endpoint.
 //     * @return the object responsible for setting up the connection to the server.
 //     * @throws IOException
 //     */
	// private HttpsURLConnection connectToSecureServer(String requestMethod, String endpoint) throws IOException {
 //        String path = this.getDefaultPath() + endpoint;
 //        URL url = new URL(path);
 //        Log.i(TAG, "Attempting to connect to server:" + url);
 //        HttpsURLConnection connector = (HttpsURLConnection) url.openConnection();
 //        connector.setReadTimeout(2000);
 //        connector.setConnectTimeout(2000);
 //        connector.setRequestMethod(requestMethod);
 //        connector.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
 //        return connector;
 //    }

 //    /**
 //     * Returns the payload from the server as a JsonElement. If the response code is successful,
 //     * it will get the input stream from the connector. Otherwise, it will get the error stream.
 //     *
 //     * @param connector the object responsible for setting up the connection to the server.
 //     * @return the payload received from the server.
 //     * @throws IOException IOException
 //     */
 //    private JsonElement getPayloadFromServer(HttpsURLConnection connector) throws IOException {
 //        GsonBuilder gsonBuilder = new GsonBuilder();
 //        Gson gson = gsonBuilder.create();

 //        Integer responseCode = connector.getResponseCode();
 //        Log.d(TAG, "Response code: " + responseCode);
 //        BufferedReader reader;
 //        if (this.isSuccessfulRequest(responseCode)) {
 //            reader = new BufferedReader(new InputStreamReader(connector.getInputStream()));
 //        } else {
 //            reader = new BufferedReader(new InputStreamReader(connector.getErrorStream()));
 //        }

 //        Type returnType = new TypeToken<JsonElement>() {
 //        }.getType();
 //        JsonElement payload = gson.fromJson(gson.newJsonReader(reader), returnType);
 //        reader.close();

 //        return payload;
 //    }
	
	/* Getters and setters.*/
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
