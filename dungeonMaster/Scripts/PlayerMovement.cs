using Godot;
using System;

public class PlayerMovement : KinematicBody2D {
    // Exported Variables //
    [Export] public float tweenDuration = 0.125f;
    [Export] public int stepSize = 16;

    // Private //
    private bool canMove = true;
    private bool isFade = false;
    
    // Member Nodes //
    private static Area2D areaU;
    private static Area2D areaD;
    private static Area2D areaL;
    private static Area2D areaR;
    private static Sprite mySprite;
    private static CollisionShape2D handL;
    private static CollisionShape2D handR;
    private static Tween tween;
    private static AnimationTree animTree;
    private static AnimationNodeStateMachinePlayback stateMachine;
    private static AudioStreamPlayer audio;

    // _Ready //
    public override void _Ready()
    {   
        // Initiate Member Nodes
        areaU = (Area2D) GetNode<Area2D>("AreaUp2D");
        areaD = (Area2D) GetNode<Area2D>("AreaDown2D");
        areaL = (Area2D) GetNode<Area2D>("AreaLeft2D");
        areaR = (Area2D) GetNode<Area2D>("AreaRight2D");
        mySprite = (Sprite) GetNode<Sprite>("BodySprite");
        handL = (CollisionShape2D) GetNode<CollisionShape2D>("HandShapeL");
        handR = (CollisionShape2D) GetNode<CollisionShape2D>("HandShapeR");
        tween = (Tween) GetNode<Tween>("Tween");
        animTree = (AnimationTree) GetNode<AnimationTree>("AnimationTree");
        stateMachine = (AnimationNodeStateMachinePlayback)animTree.Get("parameters/playback");
        audio = (AudioStreamPlayer) GetNode<AudioStreamPlayer>("AudioStreamPlayer");

        stateMachine.Start("PlayerIdle");

        // Signals
        SignalManager.self.Connect(nameof(SignalManager.FadeOut), this, nameof(Fade));
        SignalManager.self.Connect(nameof(SignalManager.FadeIn), this, nameof(Fade));
        SignalManager.self.Connect(nameof(SignalManager.FadeOutComplete), this, nameof(FadeComplete));
        SignalManager.self.Connect(nameof(SignalManager.FadeInComplete), this, nameof(FadeComplete));

        // Starting Methods
        animTree.Active = true;
    }
    
    // Player Flip //
    public void playerFlip(Boolean isFlipped) {
        if (isFlipped) {
            mySprite.FlipH = true;
            handL.Position = new Vector2(-2, 0);
            handR.Position = new Vector2(11, 0);
        } else {
            mySprite.FlipH = false;
            handL.Position = new Vector2(-11, 0);
            handR.Position = new Vector2(2, 0);
        }
    }

    // Input Response //
    public override void _Input(InputEvent inputEvent) {
        // Movement
        if (inputEvent.IsActionPressed("ui_up") && !isFade && canMove) movePLayer(Direction.UP);
        if (inputEvent.IsActionPressed("ui_down") && !isFade && canMove) movePLayer(Direction.DOWN);
        if (inputEvent.IsActionPressed("ui_left") && !isFade && canMove) movePLayer(Direction.LEFT);
        if (inputEvent.IsActionPressed("ui_right") && !isFade && canMove) movePLayer(Direction.RIGHT);
        if (inputEvent.IsActionPressed("ui_attack") && !isFade && canMove) ui_attack();
        if (inputEvent.IsActionPressed("ui_use") && !isFade && canMove) ui_use();
        // UI
    }

    // Player Move //
    public void movePLayer(Vector2 dir) {
        if(canMove) {
            if(dir.x > 0) playerFlip(true);
            if(dir.x < 0) playerFlip(false);
            ObsticalType checkObst = isObstical(dir);
            if(checkObst == ObsticalType.NULL) {
                tween.InterpolateProperty(this, "position", Position, (Position + (dir*stepSize)), tweenDuration, Tween.TransitionType.Linear , Tween.EaseType.In);
                canMove = false;
                tween.Start();
                AudioManager.self.Play(Sounds.PLAYER_JUMP, AudioBus.PLAYER);
            } else if(checkObst == ObsticalType.ENEMY) {
                AudioManager.self.Play(Sounds.PLAYER_HURT, AudioBus.PLAYER);
            } else if(checkObst == ObsticalType.DOOR_CLOSED) {
                openDoor(dir);
                AudioManager.self.Play(Sounds.DOOR_OPEN, AudioBus.PLAYER);
            } else {
                stateMachine.Travel("PlayerBump");
                AudioManager.self.Play(Sounds.PLAYER_BUMP, AudioBus.PLAYER);
            }
        }
    }

    // Signals  Functions //
    public void Fade() {
        GD.Print("Player: Fade!");
        isFade = true;
    }

    public void FadeComplete() {
         GD.Print("Player: Fade In done!");
        isFade = false;
    }

    public void _OnTweenCompleted() {
        canMove = true;
    }

    public void OnAreaEntered(Area area) {

    }

    public void OnBodyEntered(Node body) {
        //  GD.Print("Body Entered");
        //  if(body is TileMap) {
            //  GD.Print("TileMap: " + body.GetType());
        //  }
    }

    public void OnBodyExit(Node body) {
        //  GD.Print("Body Exit");
    }

    // UI Functions //
    public void ui_attack() {
        GD.Print("Attack:");
    }

    public void ui_use() {
        //  GD.Print("_up: Bodies = " + areaU.GetOverlappingBodies() + " Areas = " + areaU.GetOverlappingAreas());
        //  GD.Print("_down: Bodies = " + areaD.GetOverlappingBodies() + " Areas = " + areaD.GetOverlappingAreas());
        //  GD.Print("_left: Bodies = " + areaL.GetOverlappingBodies() + " Areas = " + areaL.GetOverlappingAreas());
        //  GD.Print("_right: Bodies = " + areaR.GetOverlappingBodies() + " Areas = " + areaR.GetOverlappingAreas());
        //  if(areaU.GetOverlappingBodies().Count == 0) GD.Print("up: no items");

        //  GD.Print("Player: Signal-FadeToBlack");
        //  SignalManager.self.EmitSignal(nameof(SignalManager.FadeOut));

        AudioManager.self.Play(Sounds.DUNGEON_MASTER, AudioBus.MUSIC);
        //  audio.Stream = Sounds.PLAYER_JUMP;
        //  audio.Play();
    }

    // Misc Functions //    
    public ObsticalType isObstical(Vector2 moveDirection) {
        Area2D[] areas = {areaU, areaD, areaL, areaR};
        int index = 0;

        if(moveDirection == Direction.UP) index = 0;
        else if(moveDirection == Direction.DOWN) index = 1;
        else if(moveDirection == Direction.LEFT) index = 2;
        else if(moveDirection == Direction.RIGHT) index = 3;
        
        if(areas[index].GetOverlappingBodies().Count == 0 && areas[index].GetOverlappingAreas().Count == 0) {
            //  GD.Print("Check: Clear");
            return ObsticalType.NULL;

        } else {
            if(areas[index].GetOverlappingBodies().Count != 0 && areas[index].GetOverlappingBodies()[0] is TileMap) {
                //  GD.Print("Check: Tile");
                TileMap myMap = (TileMap) areas[index].GetOverlappingBodies()[0];
                Vector2 myTilePos = (myMap.WorldToMap(areas[index].GlobalPosition) / 2 )  + moveDirection;
                int tileId = myMap.GetCellv(myTilePos);
                //  GD.Print("Map: " + myMap + ", TilePos: " + myTilePos + ", ID: " + tileId + " " + myMap.TileSet.TileGetName(tileId));

                if(tileId == (int) ObsticalType.DOOR_CLOSED) return ObsticalType.DOOR_CLOSED;
                if(tileId == (int) ObsticalType.BRICK) return ObsticalType.BRICK;
                if(tileId == (int) ObsticalType.BRICK_1) return ObsticalType.BRICK;
                if(tileId == (int) ObsticalType.BRICK_2) return ObsticalType.BRICK;

            } else if(areas[index].GetOverlappingAreas().Count != 0 && areas[index].GetOverlappingAreas()[0] is Area2D) {
                //  GD.Print("Check: Area");                
                return ObsticalType.ENEMY;

            }
        }
        return ObsticalType.BRICK;
    }

    public Boolean openDoor(Vector2 moveDirection) {
        Area2D[] areas = {areaU, areaD, areaL, areaR};
        int index = 0;

        if(moveDirection == Direction.UP) index = 0;
        else if(moveDirection == Direction.DOWN) index = 1;
        else if(moveDirection == Direction.LEFT) index = 2;
        else if(moveDirection == Direction.RIGHT) index = 3;
        
        if(areas[index].GetOverlappingBodies().Count != 0 && areas[index].GetOverlappingBodies()[0] is TileMap) {
            TileMap myMap = (TileMap) areas[index].GetOverlappingBodies()[0];
            Vector2 myTilePos = (myMap.WorldToMap(areas[index].GlobalPosition) / 2 )  + moveDirection;
            int tileId = myMap.GetCellv(myTilePos);

            if(tileId == (int) ObsticalType.DOOR_CLOSED) {
                //  GD.Print("Open Door");
                myMap.SetCellv(myTilePos, ((int) ObsticalType.DOOR_OPEN));

                return true;
            }
        }
        return false;
    }
}

