import models.APIVersionRequestV4;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler implements Runnable {
  private final Socket clientSocket;

  ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    try {
      DataInputStream input = new DataInputStream(clientSocket.getInputStream());
      DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
      while (true) {
        APIVersionRequestV4 request = deserializeV4Request(input);

        short errorCode = 35;
        if (request.getApiVersion() >= 0 && request.getApiVersion() <= 4) {
          errorCode = 0;
        }

        sendV4RequestResponse(output, request, errorCode);
      }
    } catch (EOFException e) {
      System.out.println("Client connection closed");
    } catch (IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }

  private APIVersionRequestV4 deserializeV4Request(DataInputStream input) throws IOException {
    APIVersionRequestV4 requestV4 = new APIVersionRequestV4();

    // Header
    requestV4.setMessageSize(input.readInt());
    requestV4.setApiKey(input.readShort());
    requestV4.setApiVersion(input.readShort());
    requestV4.setCorrelationId(input.readInt());

    // Client ID section
    requestV4.setClientIDLength(input.readShort());
    requestV4.setClientIDContent(input.readNBytes(9));
    requestV4.setTagBuffer(input.readByte());

    // Response body section
    requestV4.setResponseBodyClient(input.readNBytes(10));
    requestV4.setClientSoftwareVersion(input.readNBytes(4));
    requestV4.setResponseBodyTagBuffer(input.readByte());

    requestV4.printRequestV4();
    return requestV4;
  }

  private void sendV4RequestResponse(DataOutputStream output, APIVersionRequestV4 request, short errorCode) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    DataOutputStream payload = new DataOutputStream(buffer);

    payload.writeInt(request.getCorrelationId());
    // Response body v4
    payload.writeShort(errorCode); // errorCode
    payload.write(2);  // Array Length
    // Element 1
    payload.writeShort(request.getApiKey()); // API key
    payload.writeShort(0); // min version
    payload.writeShort(4); // max version
    payload.write(0); // tag buffer
    //Element ends
    payload.writeInt(0); // throttle time
    payload.write(0); // tag buffer

    //Writing the output to socket output stream
    output.writeInt(payload.size());
    output.write(buffer.toByteArray());

    System.out.println("Response :\n" + Arrays.toString(buffer.toByteArray()));
    output.flush();
  }
}
