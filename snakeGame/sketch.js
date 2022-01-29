let row = 25;
let col = 35;
let scl = 20;
let frame = 10;
let play = true;
let snake;
let food;
let tempX = 0;
let tempY = 0;

function setup() {
  createCanvas(col * scl + 2, row * scl + 2);
  food = new Food();
  snake = new Snake();
  food.update(scl);

}

function draw() {
  frameRate(frame);
  background(31);

  noFill();
  strokeWeight(1);
  stroke(0);
  for (let i = 0; i < col + 1; i++) {
    line(i * scl + 1, 0, i * scl + 1, row * scl);
  }
  for (let i = 0; i < row + 1; i++) {
    line(0, i * scl + 1, col * scl, i * scl + 1);
  }

  if (play) {
    food.show(scl);
    snake.move(scl);

    if (snake.isDead()) {
      noLoop();
      print("you died");
      fill(0, 255, 0);
      textAlign(CENTER, CENTER);
      textSize(52);
      stroke(21);
      strokeWeight(5);
      text("Game Over", width / 2, height / 2 - 25);
      textSize(32);
      text("Score: " + ((snake.length * 5) - 5), width / 2, height / 2 + 20);
    }

    if (snake.body[snake.length - 1].x == food.x && snake.body[snake.length - 1].y == food.y) {
      snake.grow();
      //print("Score: " + snake.length);
      food.update(scl);
    }
  } else {
    food.show(scl);
    snake.show(scl);
    fill(0, 255, 0);
    textAlign(CENTER, CENTER);
    textSize(52);
    stroke(21);
    strokeWeight(5);
    text("Pause", width / 2, height / 2 - 25);
  }
}

function keyPressed() {
  if (keyCode === UP_ARROW) {
    snake.vy = -1;
    snake.vx = 0;
  }
  if (keyCode === DOWN_ARROW) {
    snake.vy = 1;
    snake.vx = 0;
  }
  if (keyCode === LEFT_ARROW) {
    snake.vx = -1;
    snake.vy = 0;
  }
  if (keyCode === RIGHT_ARROW) {
    snake.vx = 1;
    snake.vy = 0;
  }
  if (key == "w") {
    snake.vy = -1;
    snake.vx = 0;
  }
  if (key == "s") {
    snake.vy = 1;
    snake.vx = 0;
  }
  if (key == "a") {
    snake.vx = -1;
    snake.vy = 0;
  }
  if (key == "d") {
    snake.vx = 1;
    snake.vy = 0;
  }
  if (key === ' ') {
    if (snake.rainbow == true) {
      snake.rainbow = false;
      frame = 10;
    } else {
      snake.rainbow = true;
      frame = 15;
    }
  }
  if (key == 'p') {
    if (play == true) {
      play = false;
      tempX = snake.vx;
      tempY = snake.vy;
      snake.vx = 0;
      snake.vy = 0;
    } else {
      play = true;
      snake.vx = tempX;
      snake.vy = tempY;
    }
  }
}