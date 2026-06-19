package demo.multi.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
public class FtpService {

    private static final String HOST = "ftp";
    private static final int PORT = 21;

    private static final String USER = "ftpuser";
    private static final String PASS = "ftppass";

    public String enviarArquivo(File file) throws Exception {

        FTPClient ftp = new FTPClient();

        ftp.connect(HOST, PORT);
        ftp.login(USER, PASS);

        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);

        try (FileInputStream fis = new FileInputStream(file)) {

            boolean enviado =
                    ftp.storeFile(file.getName(), fis);

            if (!enviado) {
                throw new RuntimeException("Falha ao enviar arquivo");
            }
        }

        ftp.logout();
        ftp.disconnect();

        return "/uploads/" + file.getName();
    }
}