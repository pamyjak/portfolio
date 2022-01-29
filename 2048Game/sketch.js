let size = 4;
let sqr = 75;
let spc = 5;
let tiles = [];
let numTiles = 0;
let occupied = [];
let score = 0;


let w = spc * (size + 1) + sqr * size;

function setup() {
  createCanvas(w, w + 50);
  for (i = 0; i < size; i++) {
    occupied[i] = [];
    tiles[i] = [];
    for (j = 0; j < size; j++) {
      occupied[i][j] = false;
      tiles[i][j] = new Tile();
      tiles[i][j].occ = false;
      tiles[i][j].value_ = 0;
      tiles[i][j].pos.x = i;
      tiles[i][j].pos.y = j;
    }
  }
  generateTile();
}

function draw() {
  background(130, 130, 125);

  for (i = 0; i < size; i++) {
    for (j = 0; j < size; j++) {
      tiles[i][j].show(spc, sqr);
    }
  }

  noStroke();
  fill(255);
  textAlign(CENTER, CENTER);
  textSize(26);
  text("Tiles: " + numTiles + "  Score: " + score, floor(w / 2), w + 25);

  /*
    for (i = 0; i < 16; i++) {
      let temp = new Tile();
      temp.position(i+1, size); 
      temp.value(i+1);
      temp.show(spc, sqr);
      }
  */
}

function checkSame(arrOld, arrNew, arrSize) {
  let index = 0;
  let doLoop = true;
  let isSame = true;
  while (index < arrSize && doLoop == true) {
    if (arrNew[index].value != arrOld[index].value) {
      isSame = false;
    }
  }
  return isSame;
}

function swipeUp() {
  clearComb();
  let tilesCopy = tiles.copy;
  for (i = 0; i < size; i++) { // cols 1, 2, 3, 4
    for (j = 1; j < size; j++) { // row index 1, 2, 3
      for (let comp = j; comp > 0; comp--) {
        if (tiles[i][comp - 1].combined == false) {
          combine(i, comp, i, comp - 1);
        }
      }
    }
  }
/*
  if (checkSame(tilesCopy, tiles, size * size)) {
    print("No valid move: tiles are all the same");
  }
  */
  generateTile();
}

function swipeDown() {
  clearComb();
  for (i = 0; i < size; i++) { // cols 1, 2, 3, 4
    for (j = 2; j > -1; j--) { // row index 2, 1, 0
      for (let comp = j; comp < size - 1; comp++) {
        if (tiles[i][comp + 1].combined == false) {
          combine(i, comp, i, comp + 1);
        }
      }
    }
  }
  generateTile();
}

function swipeLeft() {
  clearComb();
  for (i = 0; i < size; i++) { // cols 1, 2, 3, 4
    for (j = 1; j < size; j++) { // row index 1, 2, 3
      for (let comp = j; comp > 0; comp--) {
        if (tiles[comp - 1][i].combined == false) {
          combine(comp, i, comp - 1, i);
        }
      }
    }
  }
  generateTile();
}

function swipeRight() {
  clearComb();
  for (i = 0; i < size; i++) { // cols 1, 2, 3, 4
    for (j = 2; j > -1; j--) { // row index 2, 1, 0
      for (let comp = j; comp < size - 1; comp++) {
        if (tiles[comp + 1][i].combined == false) {
          combine(comp, i, comp + 1, i);
        }
      }
    }
  }
  generateTile();
}

function generateTile() {
  if (numTiles < size * size) {
    let doLoop = true;
    let tryCount = 0;
    while (doLoop) {
      let tempX = floor(random(0, size));
      let tempY = floor(random(0, size));
      tryCount++;
      if (tiles[tempX][tempY].occ || tempX == size || tempY == size) {
        doLoop = true;
      } else {
        doLoop = false;
        numTiles++;
        tiles[tempX][tempY].occ = true;
        let tempVal = floor(random(0, 10));
        if (tempVal < 9) {
          tiles[tempX][tempY].value_ = 1;
        } else {
          tiles[tempX][tempY].value_ = 2;
        }
        tiles[tempX][tempY].pos.x = tempX;
        tiles[tempX][tempY].pos.y = tempY;
        //occupied[tempX][tempY] = true;
        //print("NEW TILE: (" + tempX + ", " + tempY + ")  ATTEMPT(S): " + tryCount + "  VALUE: " + pow(2, tiles[size - 1][size - 1].value_));
      }
    }
  } else {
    //print("ERROR: cannot generate tile out of bounds");
  }

}

function combine(x1, y1, x2, y2) {
  //checks two tiles against each other. Moves 1 to 2, combines 1 and 2, or ignores.
  if (tiles[x1][y1].combined == true || tiles[x2][y2].combined == true) {
    //one of tiles is combined
  } else {
    if (tiles[x2][y2].occ == true) {
      if (tiles[x1][y1].value_ == tiles[x2][y2].value_ && tiles[x1][y1].value_ != 0) {
        tiles[x2][y2].value_ += 1;
        tiles[x2][y2].combined = true;
        score += pow(2, tiles[x2][y2].value_);
        removeTile(x1, y1);
      } else {
        //ignored: tiles cannot move combine
      }
    } else if (tiles[x2][y2].occ == false && tiles[x1][y1].value_ != 0) {
      tiles[x2][y2].value_ = tiles[x1][y1].value_;
      tiles[x2][y2].occ = true;
      tiles[x1][y1].value_ = 0;
      tiles[x1][y1].occ = false;
      tiles[x1][y1].combined = false;
    }
  }
}

function posit(val) {
  let x = (val - 1) % size;
  let y = floor((val - 1) / size);
  let vect = new createVector(x, y);
  return vect;
}

function removeTile(x, y) {
  tiles[x][y].occ = false;
  tiles[x][y].value_ = 0;
  tiles[x][y].combined = false;
  numTiles--;
}

function clearTiles() {
  for (let g = 0; g < size * size; g++) {
    if (tiles[posit(g + 1).x][posit(g + 1).y].occ == true) {
      tiles[posit(g + 1).x][posit(g + 1).y].occ = false;
      tiles[posit(g + 1).x][posit(g + 1).y].value_ = 0;
      numTiles--;
    }
  }
}

function clearComb() {
  for (let g = 0; g < size * size; g++) {
    tiles[posit(g + 1).x][posit(g + 1).y].combined = false;
  }
}

function keyPressed() {
  if (keyCode === UP_ARROW) {
    swipeUp();
  }
  if (keyCode === DOWN_ARROW) {
    swipeDown();
  }
  if (keyCode === LEFT_ARROW) {
    swipeLeft();
  }
  if (keyCode === RIGHT_ARROW) {
    swipeRight();
  }
  /*
  if (key === ' ') {
    clearComb();
    generateTile();
  }
  if (key === 'z') {
    clearTiles();

    for (let g = 0; g < 3; g++) {
      tiles[g][1].occ = true;
      tiles[g][1].value_ = 1;
      numTiles++;
    }
    tiles[0][0].occ = false;
    tiles[0][0].value_ = 0;

    tiles[1][0].occ = true;
    tiles[1][0].value_ = 1;
    numTiles++;

    tiles[2][0].occ = true;
    tiles[2][0].value_ = 2;
    numTiles++;

    tiles[3][0].occ = true;
    tiles[3][0].value_ = 2;
    numTiles++;

  }
  if (key === 'x') {
    clearComb();
    combine(0, 1, 0, 0);
    combine(1, 1, 1, 0);
    combine(2, 1, 2, 0);
    combine(3, 1, 3, 0);
  }
  if (key === 'c') {
    clearComb();
    clearTiles();
  }
  if (key === 'v') {
    clearComb();
    clearTiles();
    for (let g = 0; g < size * size; g++) {
      generateTile();
    }
  }
  */
}