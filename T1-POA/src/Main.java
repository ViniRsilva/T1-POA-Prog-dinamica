public class Main {
    public static void main(String[] args) {
        // Instâncias das duas abordagens
        LCSForcaBruta lcsForcaBruta = new LCSForcaBruta();
        LCSMemo lcsMemo = new LCSMemo();

        // Strings de teste
        String s1 = "ACBCDCECFC";
        String s2 = "ABCDEF";

        System.out.println("====================================");
        System.out.println("   COMPARAÇÃO: LCS Força Bruta x Memoização");
        System.out.println("====================================");
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("------------------------------------");

        // --- Força Bruta ---
        long inicioFB = System.nanoTime();
        int resultadoFB = lcsForcaBruta.lcs(s1, s2);
        long fimFB = System.nanoTime();
        double tempoFB = (fimFB - inicioFB) / 1_000_000.0;

        System.out.println("Método: Força Bruta");
        System.out.println("Resultado LCS: " + resultadoFB);
        System.out.println("Quantidade de operações: " + lcsForcaBruta.getContadorOperacoesLcsForcaBruta());
        System.out.printf("Tempo de execução: %.4f ms%n", tempoFB);
        System.out.println("------------------------------------");

        // --- Programação Dinâmica (Memoização) ---
        long inicioDP = System.nanoTime();
        int resultadoDP = lcsMemo.lcs(s1, s2);
        long fimDP = System.nanoTime();
        double tempoDP = (fimDP - inicioDP) / 1_000_000.0;

        System.out.println("Método: Programação Dinâmica (Memoização)");
        System.out.println("Resultado LCS: " + resultadoDP);
        System.out.println("Quantidade de operações: " + lcsMemo.getContadorOperacoesLcsMemo());
        System.out.printf("Tempo de execução: %.4f ms%n", tempoDP);
        System.out.println("------------------------------------");

        // --- Comparativo final ---
        System.out.println("Diferença de tempo: "
                + String.format("%.4f", (tempoFB - tempoDP)) + " ms");
        System.out.println("====================================");
    }
}
