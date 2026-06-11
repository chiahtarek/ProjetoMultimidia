package demo.multi;
import demo.multi.service.ImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MultiApplication implements CommandLineRunner {

    private final ImageService service;

    public MultiApplication(ImageService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(MultiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        File pasta = new File("imagens");
        File[] imagens = pasta.listFiles();

        if (imagens == null || imagens.length == 0) {
            System.out.println("Nenhuma imagem encontrada!");
            return;
        }

        System.out.println("Total imagens: " + imagens.length);

        // ================= BLOB =================
        long iniBlob = System.currentTimeMillis();

        for (File img : imagens) {
            service.inserirBlob(img);
        }

        long fimBlob = System.currentTimeMillis();

        // ================= PATH =================
        long iniPath = System.currentTimeMillis();

        for (File img : imagens) {
            service.inserirPath(img);
        }

        long fimPath = System.currentTimeMillis();

        System.out.println("\n===== RESULTADO DE INSERÇÃO DE 10 IMAGENS =====");
        System.out.println("BLOB: " + (fimBlob - iniBlob) + " ms");
        System.out.println("PATH: " + (fimPath - iniPath) + " ms");

        if ((fimPath - iniPath) < (fimBlob - iniBlob)) {
            System.out.println("PATH foi mais rápido");
        } else {
            System.out.println("BLOB foi mais rápido");
        }
        System.out.println("\n===== TESTE DE BUSCA =====");

        String nomeTeste = imagens[0].getName();

// ================= PATH =================
        long iniPathBusca = System.currentTimeMillis();

        String caminho = service.buscarPathPorNome(nomeTeste);

        long fimPathBusca = System.currentTimeMillis();

        long tempoPathBusca = fimPathBusca - iniPathBusca;

        System.out.println("PATH encontrado: " + caminho);
        System.out.println("Tempo busca PATH: " + tempoPathBusca + " ms");

// ================= BLOB =================
        long iniBlobBusca = System.currentTimeMillis();

        byte[] imagemBlob = service.buscarBlobPorNome(nomeTeste);

        long fimBlobBusca = System.currentTimeMillis();

        long tempoBlobBusca = fimBlobBusca - iniBlobBusca;

        if (imagemBlob != null) {
            System.out.println("BLOB encontrado: " + imagemBlob.length + " bytes");
        } else {
            System.out.println("BLOB não encontrado");
        }

        System.out.println("Tempo busca BLOB: " + tempoBlobBusca + " ms");

// ================= COMPARAÇÃO =================

        System.out.println("\n===== RESULTADO BUSCA =====");

        if (tempoPathBusca < tempoBlobBusca) {
            System.out.println("PATH foi mais rápido na busca.");
        } else if (tempoBlobBusca < tempoPathBusca) {
            System.out.println("BLOB foi mais rápido na busca.");
        } else {
            System.out.println("Empate na busca.");
        }
    }
}