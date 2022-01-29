using Godot;
using System;

// Sound Paths //
public static class Sounds {
    //  public static readonly string PLAYER_WALK = "res://Assets/Sounds/beep.ogg";
    public static readonly AudioStreamOGGVorbis PLAYER_WALK = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Sounds/beep.ogg");
    public static readonly AudioStreamOGGVorbis PLAYER_JUMP = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Sounds/jump.ogg");
    public static readonly AudioStreamOGGVorbis PLAYER_BUMP = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Sounds/hit2.ogg");
    public static readonly AudioStreamOGGVorbis PLAYER_HURT = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Sounds/buzz_hit.ogg");
    public static readonly AudioStreamOGGVorbis DOOR_OPEN = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Sounds/click.ogg");
    public static readonly AudioStreamOGGVorbis DUNGEON_MASTER = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Music/dungon_master_theme.ogg");
    public static readonly AudioStreamOGGVorbis RIBOLT = (AudioStreamOGGVorbis) ResourceLoader.Load("res://Assets/Music/ribolt_main_theme.ogg");
}

// Signal Manager //
public class AudioManager : Node2D {
    // Variables
    public static AudioManager self;
    public static AudioStreamPlayer audio;
    public static AudioStreamPlayer player;
    public static AudioStreamPlayer music;

    // Ready
    public override void _Ready() {
        self = (AudioManager) GetNode<AudioManager>("/root/AudioManager");
        audio = new AudioStreamPlayer();
        GetTree().Root.CallDeferred("add_child", audio);

        player = new AudioStreamPlayer();
        music = new AudioStreamPlayer();
        GetTree().Root.CallDeferred("add_child", player);
        GetTree().Root.CallDeferred("add_child", music);
    }

    // Play Sound
    public void Play(AudioStream sound, String bus) {
        if(bus == AudioBus.PLAYER) {
            GD.Print("Bus: Player");
            player.Stream = sound;
            player.Bus = bus;
            player.Play();
        } else if(bus == AudioBus.MUSIC) {
            GD.Print("Bus: Music");
            music.Stream = sound;
            music.Bus = bus;
            music.Play();
        } else {
            GD.Print("Bus: Misc");
            audio.Stream = sound;
            audio.Bus = bus;
            audio.Play();
        }
    }

}