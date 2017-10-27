package org.jboss.perf.client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by johara on 20/10/17.
 */
public class Generator {
    String readFileInJar(String filename) {
        try (ByteArrayOutputStream res = new ByteArrayOutputStream()) {
            try (InputStream in = Generator.class.getResourceAsStream(filename)) {
                byte[] buffer = new byte[256];
                while (true) {
                    int len = in.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    res.write(buffer, 0, len);
                }
                return res.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String readFile(String filename) {
        try {
            return new String( Files.readAllBytes( Paths.get(filename)));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
