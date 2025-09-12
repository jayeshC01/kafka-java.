import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
  private final Socket clientSocket;

  ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void processClient() {
    try {
      DataInputStream input = new DataInputStream(clientSocket.getInputStream());
      DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

      int messageSize = input.readInt(); //message size
      int apiKey = input.readShort(); // apiKey
      int apiVersion = input.readShort();
      int correlationId = input.readInt();

      short errorCode = 35;
      if (apiVersion >= 0 && apiVersion <= 4) {
        errorCode = 0;
      }

      System.out.println("messageSize: " + messageSize);
      System.out.println("apiKey: " + apiKey);
      System.out.println("apiVersion: " + apiVersion);
      System.out.println("correlationId: " + correlationId);
      System.out.println("errorCode: " + errorCode);

      sendV4RequestResponse(output, correlationId, errorCode);
    } catch (IOException e) {
      System.out.printf(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private void sendV4RequestResponse(DataOutputStream output, int correlationId, short errorCode) throws IOException {
    output.writeInt(19); // message size
    output.writeInt(correlationId); // correlationId
    // Response body v4
    output.writeShort(errorCode); // errorCode
    output.write(2);  // Array Length
    // Element 1
    output.writeShort(18); // API key
    output.writeShort(0); // min version
    output.writeShort(4); // max version
    output.write(0); // tag buffer
    //Element ends
    output.writeInt(0); // throttle time
    output.write(0); // tag buffer
    output.flush();
  }
}
