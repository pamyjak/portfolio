using Godot;
using System;

// Direction //
public static class Direction {
    public static readonly Vector2 UP = new Vector2(0, -1);
    public static readonly Vector2 DOWN = new Vector2(0, 1);
    public static readonly Vector2 LEFT = new Vector2(-1, 0);
    public static readonly Vector2 RIGHT = new Vector2(1, 0); 
}

// GameState //
public enum GameState : Byte {
    MENU = 0,
    PLAY = 1,
    UI = 2,
    PAUSE = 3
}

// PlayerState //
public enum PlayerState : int {
    IDLE = 0,
    ATTACK = 1,
    MOVE = 2,
    JUMP = 3
}

// ObsticalType //
public enum ObsticalType : int {
    NULL = -1,
    FLOOR = 0,
    BRICK = 1,
    DOOR_CLOSED = 2,
    DOOR_OPEN = 3,
    BRICK_1 = 4,
    BRICK_2 = 5,
    ENEMY = 10
}

// Enchantment //
public enum Enchantment {
    UNBREAKING,
    EFFICIENCY,
    SHARPNESS,
    SMITE
}

// Material //
public enum Material : byte {
    // 0: default, 1: placeable, 2: consumable, 3: tool
    NULL = 0,
    POTION = 2,
    COOKED_CHICKEN = 2,
    DIRT = 1,
    IRON_SWORD = 3
}