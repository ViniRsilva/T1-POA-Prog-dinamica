import java.util.Arrays;

class LCSMemo {

    /**
     * Função auxiliar para cálculo do LCS
     *
     * @param s1   string 1
     * @param s2   string 2
     * @param m    tamanho string 1
     * @param n    tamanho string 2
     * @param memo matriz de memoização
     * @return tamanho da maior substring gerada por s1 e s2
     */
    int lcsRec(String s1, String s2, int m, int n,
               int[][] memo) {
        // Caso base
        if (m == 0 || n == 0)
            return 0;

        // Já inserido na tabela
        if (memo[m][n] != -1)
            return memo[m][n];

        // Ultimo caractere das strings são iguais
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return memo[m][n]
                    = 1 + lcsRec(s1, s2, m - 1, n - 1, memo);
        }

        // Caracteres não são iguais -> chama lcsRec duas vezes,
        // eliminando o ultimo caractere
        // de cada uma das strings em cada chamada
        return memo[m][n]
                = Math.max(lcsRec(s1, s2, m, n - 1, memo),
                lcsRec(s1, s2, m - 1, n, memo));
    }

    /**
     * Calcula o tamanho da maior substring gerada por s1 e s2
     *
     * @param s1 string 1
     * @param s2 string 2
     * @return tamanho da maior substring
     */
    int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] memo = new int[m + 1][n + 1];

        // Inicializa a tabela de memoização com -1
        for (int i = 0; i <= m; i++) {
            Arrays.fill(memo[i], -1);
        }

        return lcsRec(s1, s2, m, n, memo);
    }
}