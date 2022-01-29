using Godot;
using System;


// Scenes // 
public static class Scenes {
    public static readonly string TEST_SCENE = "res://source/scenes/scene_1.tscn";
    public static readonly string RIVER_NORTH = "res://source/scenes/northern_river.tscn";
    public static readonly string CAMP_2 = "res://source/scenes/camp_2.tscn";
    public static readonly string ROOM_1 = "res://Scenes/level_1.tscn";
    public static readonly string ROOM_2 = "res://Scenes/level_2.tscn";
}

// Sprites //
public static class Sprites {
    public static readonly string DIRT = "res://assets/textures/dirt.png";
}

// Sound Busses //
public static class AudioBus {
    public static readonly string MASTER = "Master";
    public static readonly string SFX = "SFX";
    public static readonly string MUSIC = "Music";
    public static readonly string PLAYER = "Player";
    public static readonly string HOSTILE = "Hostile";
    public static readonly string AMBIENT = "Ambient";
    public static readonly string WEATHER = "Weather";
}
