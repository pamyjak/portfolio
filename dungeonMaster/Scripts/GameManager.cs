using Godot;
using System;
using System.Collections.Generic;

public class GameManager : Node
{
    // Variables //
    [Export] public int level;
    [Export] public int levelSize = 15;
    [Export] public int[][] levelMap;
    [Export] public Node slime;
    //  public var slime;


    // Member Nodes //
    public TileMap tileMap;
    public Area2D[] entities = new Area2D[10];
    public List<Area2D> entitiesX = new List<Area2D>();

    // onReady //
    public override void _Ready()
    {
        // Initiate Member Nodes
        tileMap = (TileMap) GetNode<TileMap>("TileMap");

        // Other
        generateLevel();
    }

    public void generateLevel() {
        // Generate TileMap
        for(int i = 0; i < levelSize; i++) {
            for(int j = 0; j < levelSize; j++) {
                if(i == 0 || i == (levelSize-1)) {
                    tileMap.SetCellv(new Vector2(i, j), ((int) ObsticalType.BRICK));
                } else if(j == 0 || j == (levelSize-1)) {
                    tileMap.SetCellv(new Vector2(i, j), ((int) ObsticalType.BRICK));
                } else {
                    tileMap.SetCellv(new Vector2(i, j), ((int) ObsticalType.FLOOR));
                }
            }
        }

        // Instantiate Slimes
        PackedScene slimeScene = GD.Load<PackedScene>("res://Scenes/Slime.tscn");
        Area2D mySlime = (Area2D) slimeScene.Instance();
        mySlime.Position = (new Vector2(5.5f, 5.5f)) * 32;
        tileMap.AddChild(mySlime);

        entities[0] = (Area2D) slimeScene.Instance();
        entities[0].Position = (new Vector2(7.5f, 2.5f)) * 32;
        tileMap.AddChild(entities[0]);

        entitiesX.Add((Area2D) slimeScene.Instance());
        entitiesX[0].Position = (new Vector2(3.5f, 8.5f)) * 32;
        tileMap.AddChild(entitiesX[0]);
    }
}
