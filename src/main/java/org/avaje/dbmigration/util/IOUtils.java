package org.avaje.dbmigration.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Utilities for IO.
 */
public class IOUtils {

  /**
   * Reads the entire contents of the specified input stream and returns them
   * as UTF-8 string.
   */
  public static String readUtf8(InputStream in) throws IOException {
    return bytesToUtf8(read(in));
  }

  /**
   * Reads the entire contents of the specified input stream and returns them
   * as a byte array.
   */
  private static byte[] read(InputStream in) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    pump(in, buffer);
    return buffer.toByteArray();
  }

  /**
   * Returns the UTF-8 string corresponding to the specified bytes.
   */
  private static String bytesToUtf8(byte[] data) {
    try {
      return new String(data, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Support for UTF-8 is mandated by the Java spec", e);
    }
  }

  /**
   * Reads data from the specified input stream and copies it to the specified
   * output stream, until the input stream is at EOF. Both streams are then
   * closed.
   */
  private static void pump(InputStream in, OutputStream out) throws IOException {

    if (in == null) throw new IOException("Input stream is null");
    if (out == null) throw new IOException("Output stream is null");
    try {
      try {
        byte[] buffer = new byte[4096];
        for (; ; ) {
          int bytes = in.read(buffer);
          if (bytes < 0) {
            break;
          }
          out.write(buffer, 0, bytes);
        }
      } finally {
        in.close();
      }
    } finally {
      out.close();
    }
  }
}
