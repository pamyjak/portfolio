using Godot;
using System;

public class LightController : Light2D
{
    [Export] public Color colorHigh = Color.Color8(220, 185, 35, 255);
    [Export] public Color colorNormal = Color.Color8(190, 165, 15, 255);
    [Export] public Color colorLow = Color.Color8(180, 155, 10, 255);
    [Export] public float scale = 1.0f;
    [Export] public float energy = 1.0f;
    [Export] public float offset = 0.05f;

    public void LightNormal() {
        this.Color = colorNormal;
        this.TextureScale = scale;
        this.Energy = energy;
    }
    public void LightHigh() {
        this.Color = colorHigh;
        this.TextureScale = scale + offset;
        this.Energy = energy + offset;
    }
    public void LightHalfHigh() {
        this.Color = colorNormal.Blend(colorHigh);
        this.TextureScale = scale + (offset*0.5f);
        this.Energy = energy + (offset*0.5f);
    }
    public void LightLow() {
        this.Color = colorLow;
        this.TextureScale = scale - offset;
        this.Energy = energy - offset;
    }
    public void LightHalfLow() {
        this.Color = colorNormal.Blend(colorLow);
        this.TextureScale = scale - (offset*0.5f);
        this.Energy = energy - (offset*0.5f);
    }
}
