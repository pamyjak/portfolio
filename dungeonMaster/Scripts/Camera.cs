using Godot;
using System;

public class Camera : Camera2D
{   
    // Variables
    [Export] public float fadeTime = 0.5f;
    [Export] public byte lightValue = 128;
    public static Tween tween;
    public static ColorRect fadeRect;
    public static ColorRect lightRect;
    private static bool isFadeOut = true;

    // Ready
    public override void _Ready() {
        // Static Members
        tween = (Tween) GetNode<Tween>("Tween");
        fadeRect = (ColorRect) GetNode<ColorRect>("ColorRect");
        
        // Starting Configurations
        fadeRect.Color = Color.Color8(0, 0, 0, 255);
        fadeRect.Visible = true;

        // Signals
        SignalManager.self.Connect(nameof(SignalManager.FadeOut), this, nameof(FadeOut));
        SignalManager.self.Connect(nameof(SignalManager.FadeIn), this, nameof(FadeIn));

        FadeIn();
    }

    // Fade To Black
    public void FadeOut() {
        GD.Print("Camera: FadeOut");
        isFadeOut = true;
        tween.InterpolateProperty(fadeRect, "color", Color.Color8(0, 0, 0, lightValue), Color.Color8(0, 0, 0, 255), fadeTime, Tween.TransitionType.Linear, Tween.EaseType.In);
        tween.Start();
    }

    // Fade To clear
    public void FadeIn() {
        GD.Print("Camera: FadeIn");
        isFadeOut = false;
        tween.InterpolateProperty(fadeRect, "color", Color.Color8(0, 0, 0, 255), Color.Color8(0, 0, 0, lightValue), fadeTime, Tween.TransitionType.Linear, Tween.EaseType.In);
        tween.Start();
    }

    // Tween Complete
    public void OnTweenComplete() {
        //TODO: fix this. Will break if SetLightValue tween. Will send unintended signal
        if(isFadeOut) {
            GD.Print("Camera: Complete-FadeOut");
            SignalManager.self.EmitSignal(nameof(SignalManager.FadeOutComplete));
        } else {
            GD.Print("Camera: Complete-FadeIn");
            SignalManager.self.EmitSignal(nameof(SignalManager.FadeInComplete));
        } 
    }

    public void SetLightValue(byte value) {
        lightValue = value;
        fadeRect.Color = Color.Color8(0, 0, 0, lightValue);
    }

    public void SetLightValue(byte value, float duration) {
        lightValue = value;
        tween.InterpolateProperty(fadeRect, "color", fadeRect.Color, Color.Color8(0, 0, 0, lightValue), duration, Tween.TransitionType.Linear, Tween.EaseType.In);
        tween.Start();
    }
}
