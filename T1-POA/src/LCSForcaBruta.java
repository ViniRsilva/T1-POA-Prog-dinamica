class LCSForcaBruta {

    int lcsRec(String s1, String s2, int m, int n) {
        if (m == 0 || n == 0)
            return 0;

        if (s1.charAt(m - 1) == s2.charAt(n - 1))
            return 1 + lcsRec(s1, s2, m - 1, n - 1);

        return Math.max(lcsRec(s1, s2, m - 1, n),
                lcsRec(s1, s2, m, n - 1));
    }


    int lcs(String s1, String s2) {
        return lcsRec(s1, s2, s1.length(), s2.length());
    }
}
