import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
  private Socket clientSocket;
  ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void processClient() {
    try {
      DataInputStream input = new DataInputStream(clientSocket.getInputStream());
      DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

      int message_size = input.readInt(); // 4 bytes
      input.readShort();       // 2 bytes - requestApiKey
      input.readShort();   // 2 bytes - requestApiVersion
      int correlationId = input.readInt();           // 4 bytes

      output.writeInt(4);
      output.writeInt(correlationId);
      output.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
