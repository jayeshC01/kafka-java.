package models;

import java.util.Arrays;

public class APIVersionRequestV4 {
  private int messageSize;
  // Fields in Request Header V2
  private short apiKey;
  private short apiVersion;
  private int correlationId;
  private short clientIDLength;
  private byte[] clientIDContent = new byte[9];
  private byte tagBuffer;
  private byte[] responseBodyClient = new byte[10];
  private byte[] clientSoftwareVersion = new byte[4];
  private byte responseBodyTagBuffer;

  public int getMessageSize() {
    return messageSize;
  }

  public void setMessageSize(int messageSize) {
    this.messageSize = messageSize;
  }

  public short getApiKey() {
    return apiKey;
  }

  public void setApiKey(short apiKey) {
    this.apiKey = apiKey;
  }

  public short getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(short apiVersion) {
    this.apiVersion = apiVersion;
  }

  public int getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(int correlationId) {
    this.correlationId = correlationId;
  }

  public byte getTagBuffer() {
    return tagBuffer;
  }

  public void setTagBuffer(byte tagBuffer) {
    this.tagBuffer = tagBuffer;
  }

  public int getClientIDLength() {
    return clientIDLength;
  }

  public void setClientIDLength(short clientIDLength) {
    this.clientIDLength = clientIDLength;
  }

  public byte[] getClientIDContent() {
    return clientIDContent;
  }

  public void setClientIDContent(byte[] clientIDContent) {
    this.clientIDContent = clientIDContent;
  }
  public byte[] getResponseBodyClient() {
    return responseBodyClient;
  }

  public void setResponseBodyClient(byte[] responseBodyClient) {
    this.responseBodyClient = responseBodyClient;
  }

  public byte[] getClientSoftwareVersion() {
    return clientSoftwareVersion;
  }

  public void setClientSoftwareVersion(byte[] clientSoftwareVersion) {
    this.clientSoftwareVersion = clientSoftwareVersion;
  }

  public byte getResponseBodyTagBuffer() {
    return responseBodyTagBuffer;
  }

  public void setResponseBodyTagBuffer(byte responseBodyTagBuffer) {
    this.responseBodyTagBuffer = responseBodyTagBuffer;
  }

  public void printRequestV4() {
    System.out.println("V4 request properties: ");
    System.out.println("messageSize: " + messageSize);
    System.out.println("apiKey: " + apiKey);
    System.out.println("apiVersion: " + apiVersion);
    System.out.println("correlationId: " + correlationId);
    System.out.println("clientIDLength: " + clientIDLength);
    System.out.println("clientIDContent: " + Arrays.toString(clientIDContent));
    System.out.println("tagBuffer: " + tagBuffer);
    System.out.println("responseBodyClient: " + Arrays.toString(responseBodyClient));
    System.out.println("clientSoftwareVersion: " + Arrays.toString(clientSoftwareVersion));
    System.out.println("responseBodyTagBuffer: " + responseBodyTagBuffer);
  }
}
