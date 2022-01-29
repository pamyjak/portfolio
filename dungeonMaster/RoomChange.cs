using Godot;
using System;

public class RoomChange : Area2D
{   
    public static bool isRoom1 = true;

    public override void _Ready() {
        SignalManager.self.Connect(nameof(SignalManager.FadeOutComplete), this, nameof(TransScene));
    }

    public void onAreaEntered(Area area) {
        GD.Print("RoomChange: Signal-FadeToBlack");
        SignalManager.self.EmitSignal(nameof(SignalManager.FadeOut));
    }

    public void TransScene() {
        GD.Print("RoomChange: Recived-FadeOutComplete");
        if(isRoom1) {
            GetTree().ChangeScene(Scenes.ROOM_2);
            isRoom1 = false;
        } else {
            GetTree().ChangeScene(Scenes.ROOM_1);
            isRoom1 = true;
        }

        //  SignalManager.self.EmitSignal(nameof(SignalManager.FadeIn));
    }
}
