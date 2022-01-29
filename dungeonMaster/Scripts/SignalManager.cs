using Godot;
using System;

public class SignalManager : Node2D {
    // Signals
    [Signal] public delegate void PrepSceneTrans();
    [Signal] public delegate void StartSceneTrans();
    [Signal] public delegate void FadeOut();
    [Signal] public delegate void FadeOutComplete();
    [Signal] public delegate void FadeIn();
    [Signal] public delegate void FadeInComplete();
    
    // Static reference of self
    public static SignalManager self;

    // Ready
    public override void _Ready() {
        self = (SignalManager) GetNode<SignalManager>("/root/SignalManager");
        OS.LowProcessorUsageMode = true;
    }
}
