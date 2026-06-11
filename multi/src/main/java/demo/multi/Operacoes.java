//import java.io.File;
//import java.nio.file.Files;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//
//public class Operacoes {
//
//    public void inserirBlob(File arquivo) throws Exception {
//
//        Connection con = Conexao.conectar();
//
//        String sql =
//                "INSERT INTO imagens_blob(nome, imagem, hash_imagem) VALUES(?,?,?)";
//
//        PreparedStatement ps = con.prepareStatement(sql);
//
//        byte[] dados = Files.readAllBytes(arquivo.toPath());
//
//        ps.setString(1, arquivo.getName());
//        ps.setBytes(2, dados);
//        ps.setString(3, gerarMD5(dados));
//
//        ps.executeUpdate();
//
//        ps.close();
//        con.close();
//    }
//
//    public void inserirPath(File arquivo) throws Exception {
//
//        Connection con = Conexao.conectar();
//
//        String sql =
//                "INSERT INTO imagens_path(nome, caminho, hash_imagem) VALUES(?,?,?)";
//
//        PreparedStatement ps = con.prepareStatement(sql);
//
//        byte[] dados = Files.readAllBytes(arquivo.toPath());
//
//        ps.setString(1, arquivo.getName());
//        ps.setString(2, arquivo.getAbsolutePath());
//        ps.setString(3, gerarMD5(dados));
//
//        ps.executeUpdate();
//
//        ps.close();
//        con.close();
//    }
//
//    private String gerarMD5(byte[] dados) throws Exception {
//
//        java.security.MessageDigest md =
//                java.security.MessageDigest.getInstance("MD5");
//
//        byte[] hash = md.digest(dados);
//
//        StringBuilder sb = new StringBuilder();
//
//        for (byte b : hash) {
//            sb.append(String.format("%02x", b));
//        }
//
//        return sb.toString();
//    }
//}
