using Godot;

public static class Utils {
    // Public Variables //
    public static GameState _GameState; 
    public static PlayerState _PlayerState;

    // Int to Roman Numeral O(1) //
    public static string RomanNum(int val) {
        string[] romArr = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", 
                           "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"};

        if(val >= 1 && val <= 20) {
            return romArr[val - 1];
        } else {
            return val.ToString();
        }
    }
}
