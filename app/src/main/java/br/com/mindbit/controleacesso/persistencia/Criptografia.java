package br.com.mindbit.controleacesso.persistencia;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe responsável pela criptografia de Strings para serem utilizados no QR-Code.
 */
public abstract class Criptografia {
    private static final String ALGORITIMO = "AES";
    private static final String MODO = "ECB";
    private static final String PREENCHIMENTO = "PKCS5Padding";
    public static final String STRING_KEY = "QABG0eSxEO2hzNldjfBHaQ==";
    private static SecretKey secretKey = null;
    private static Cipher cipher = null;

    /**
     * Seta o cifrador a partir do algoritmo, modo e padding.
     *
     * @throws NoSuchAlgorithmException Caso o argumento algoritmo seja inválido.
     * @throws NoSuchPaddingException Caso o argumento Padding seja inválido.
     */
    private static void setupCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance(ALGORITIMO + '/' + MODO + '/' + PREENCHIMENTO);
    }

    /**
     * Decodifica a chave privada a partir do formato <code>String</code> para array de bytes para
     * ser usado na criptografia.
     */
    private static void setPrivateKeyFromString() {
        byte[] byteKey = Base64.decode(STRING_KEY, Base64.DEFAULT);
        secretKey = new SecretKeySpec(byteKey, 0, byteKey.length, ALGORITIMO);
    }

    /**
     * Verifica se os atributos <code>secretKey</code> e <code>cipher</code> se encontram
     * inicializados e os inicializa caso necessário.
     */
    private static void checkKeyAndCipherInitialization() {
        try {
            if (secretKey == null) {
                setPrivateKeyFromString();
            }
            if (cipher == null) {
                setupCipher();
            }
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException except) {
            except.printStackTrace();
        }
    }

    /**
     * Criptografa um <code>String</code>(não é utilizado na aplicação).
     *
     * @param id - String original a ser criptografado.
     * @return - Formatação em String do conteúdo criptografado.
     *
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encryptString(String id) throws UnsupportedEncodingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        checkKeyAndCipherInitialization();

        byte[] plainText = id.getBytes("UTF8");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(plainText);

        return Base64.encodeToString(cipherText, Base64.DEFAULT);
    }

    /**
     * Descriptografa o <code>String</code> fornecido.
     *
     * @param stringCipher - Codificação em UTF-8 do string criptografado.
     * @return - Resultado descriptografado do string de entrada.
     *
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String decryptString(String stringCipher) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        checkKeyAndCipherInitialization();

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] byteCipher = Base64.decode(stringCipher, Base64.DEFAULT);
        byte[] newPlainText = cipher.doFinal(byteCipher);

        return new String(newPlainText, "UTF8");
    }
}