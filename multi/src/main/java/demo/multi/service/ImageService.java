package demo.multi.service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.File;
import java.nio.file.Files;

@Service
public class ImageService {

    private final JdbcTemplate jdbc;

    public ImageService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // =======================
    // INSERT BLOB
    // =======================
    public void inserirBlob(File file) throws Exception {

        byte[] bytes = Files.readAllBytes(file.toPath());

        jdbc.update(
                "INSERT INTO imagens_blob (nome, imagem) VALUES (?, ?)",
                file.getName(),
                bytes
        );
    }

    // =======================
    // INSERT PATH
    // =======================
    public void inserirPath(File file) {

        jdbc.update(
                "INSERT INTO imagens_path (nome, caminho) VALUES (?, ?)",
                file.getName(),
                file.getAbsolutePath()
        );
    }
    public byte[] buscarBlobPorNome(String nome) {

        try {
            return jdbc.queryForObject(
                    "SELECT imagem FROM imagens_blob WHERE nome = ? LIMIT 1",
                    byte[].class,
                    nome
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public String buscarPathPorNome(String nome) {

        return jdbc.queryForObject(
                "SELECT caminho FROM imagens_path WHERE nome = ? LIMIT 1",
                String.class,
                nome
        );
    }
}