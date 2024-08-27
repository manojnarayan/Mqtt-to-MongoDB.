package mqttdatabase;
import org.eclipse.paho.client.mqttv3.*;
import com.mongodb.client.*;
import org.bson.Document;


public class MqttData {

	    private static MongoClient mongoClient;
	    private static MongoDatabase database;
	    private static MongoCollection<Document> collection;

	    public static void main(String[] args) {
	        // MQTT broker connection details
	        String brokerUrl = "tcp://fleet-backend.evrides.in:2883";
	        String clientId = "clientmyrjava";
	        String topic = "gmr/data";
	        String username = "gmr";
	        String password = "Tride@123";

	        // MongoDB connection details
	        String mongoUri = "mongodb://localhost:27017"; // MongoDB URI
	        String dbName = "mqttDatabase"; // Database name
	        String collectionName = "messages"; // Collection name

	        try {
	            // Initialize MongoDB connection
	            mongoClient = MongoClients.create(mongoUri);
	            database = mongoClient.getDatabase(dbName);
	            collection = database.getCollection(collectionName);

	            // Create an MQTT client
	            MqttClient client = new MqttClient(brokerUrl, clientId);

	            // Create connection options
	            MqttConnectOptions connOpts = new MqttConnectOptions();
	            connOpts.setCleanSession(false);
	            connOpts.setUserName(username);
	            connOpts.setPassword(password.toCharArray());

	            // Define callback to handle incoming messages
	            client.setCallback(new MqttCallback() {
	                @Override
	                public void connectionLost(Throwable cause) {
	                    System.err.println("Connection lost: " + cause.getMessage());
	                    cause.printStackTrace();
	                }

	                @Override
	                public void messageArrived(String topic, MqttMessage message) throws Exception {
	                    try {
	                        System.out.println("Message arrived on topic " + topic);
	                        String payload = new String(message.getPayload());
	                        System.out.println("Payload: " + payload);

	                        // Process the payload and save it to MongoDB
	                        processMessage(payload);
	                    } catch (Exception e) {
	                        System.err.println("Error processing message: " + e.getMessage());
	                        e.printStackTrace();
	                    }
	                }

	                @Override
	                public void deliveryComplete(IMqttDeliveryToken token) {
	                    // Not needed for subscriber
	                }
	            });

	            // Connect to the broker
	            System.out.println("Connecting to broker: " + brokerUrl);
	            client.connect(connOpts);
	            System.out.println("Connected");

	            // Subscribe to the topic
	            System.out.println("Subscribing to topic: " + topic);
	            client.subscribe(topic, 1); // Subscribe with QoS 1
	            System.out.println("Subscribed");

	            // Keep the client running indefinitely
	            while (true) {
	                // Do nothing, just keep the client running
	                Thread.sleep(1000); // Sleep to reduce CPU usage
	            }

	        } catch (MqttException e) {
	            System.err.println("MQTT exception: " + e.getMessage());
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            System.err.println("Interrupted exception: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            // Clean up resources
	            if (mongoClient != null) {
	                mongoClient.close();
	            }
	        }
	    }

	    // Method to process and save incoming messages to MongoDB
	    private static void processMessage(String message) {
	        System.out.println("Processing message: " + message);

	        // Create a new MongoDB document with the message payload
	        Document doc = new Document("message", message)
	                           .append("length", message.length())
	                           .append("timestamp", System.currentTimeMillis());

	        // Insert the document into the collection
	        try {
	            collection.insertOne(doc);
	            System.out.println("Message saved to MongoDB. Document: " + doc.toJson());
	        } catch (Exception e) {
	            System.err.println("Error saving document to MongoDB: " + e.getMessage());
	            e.printStackTrace();    
	        }
	    }
	}
