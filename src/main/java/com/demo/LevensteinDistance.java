package com.demo;

public class LevensteinDistance {
    public static int min(int a, int b , int c) {
        return Math.min(a, Math.min(b,c));
    }
    public static int distance(String a, String b) {
        int m = a.length(), n = b.length();
        int dp [][] = new int[m+1][n+1];
        for(int i =0;i<=m;i++) {
            dp[i][0] = i;
        }
        for(int j =0;j<=n;j++) {
            dp[0][j]= j;
        }
        for(int i =1;i<=m;i++) {
            for(int j=1;j<=n;j++) {
                if( a.charAt(i-1) !=b.charAt(j-1)) {
                    dp[i][j] = min( 1 + dp[i-1][j], //deletion,
                            1+ dp[i][j-1] , //insertion
                            1+ dp[i-1][j-1]
                    );


                }
                else {
                    dp[i][j] = dp[i-1][j-1];
                }
            }
        }
        return dp[m][n];
    }
}
