
package learnjava;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

public class MqttClient {
    private static final String BROKER_URL = "ssl://gmr-dev.ks.com:443";
    private static final String USERNAME = "ks";
    private static final String PASSWORD = "ks123";

    public MqttClient(String brokerUrl, Object clientId) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws MqttException {
        try {
            // Create a new MQTT client
            MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());

            // Create MQTT connection options
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(USERNAME);
            options.setPassword(PASSWORD.toCharArray());
            options.setSocketFactory(javax.net.ssl.SSLSocketFactory.getDefault());
            options.setCleanSession(true);
            options.setConnectionTimeout(30); // Set connection timeout
            options.setKeepAliveInterval(60); // Set keep alive interval

            // Connect to the MQTT broker
            System.out.println("Connecting to broker: " + BROKER_URL);
            client.connect(options);
            System.out.println("Connected successfully");

            // Set up a callback for incoming messages
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Message received:\n\tTopic: " + topic + "\n\tMessage: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // No action needed here for this example
                }
            });

           

            // Keep the client running
            System.out.println("Press Enter to exit...");
            System.in.read();

            // Disconnect from the broker
            client.disconnect();
            System.out.println("Disconnected from broker");
            
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

	private void subscribe(String topic, int i) {
		// TODO Auto-generated method stub
		
	}

	private void disconnect() {
		// TODO Auto-generated method stub
		
	}

	private void setCallback(MqttCallback mqttCallback) {
		// TODO Auto-generated method stub
		
	}

	private void connect(MqttConnectOptions options) {
		// TODO Auto-generated method stub
		
	}

	private static Object generateClientId() {
		// TODO Auto-generated method stub
		return null;
	}
}
