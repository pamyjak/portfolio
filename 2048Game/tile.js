class Tile {
  constructor() {
    this.value_ = 1;
    this.pos = createVector(0, 0);
    this.posNum = 1;
    this.occ = true;
    this.combined = false;


    this.palette = [];
    this.palette[0] = color(165, 165, 160); //default
    this.palette[1] = color(200, 200, 200); //2
    this.palette[2] = color(200, 200, 180); //4
    this.palette[3] = color(245, 187, 118); //8
    this.palette[4] = color(245, 160, 64); //16
    this.palette[5] = color(255, 100, 60); //32
    this.palette[6] = color(255, 75, 25); //64
    this.palette[7] = color(255, 229, 90); //128
    this.palette[8] = color(255, 219, 70); //256
    this.palette[9] = color(255, 199, 50); //512
    this.palette[10] = color(255, 189, 50); //1024
    this.palette[11] = color(255, 179, 40); //2048
    this.palette[12] = color(0, 0, 0); //Unknown
  }


  position(val, numRow) {
    let x = (val - 1) % numRow;
    let y = floor((val - 1) / numRow);
    this.pos.x = x;
    this.pos.y = y;
    this.posNum = val;
  }

  show(offset, size) {
    let x = this.pos.x;
    let y = this.pos.y;
    if (this.occ == true) {
      noStroke();
      if (this.value_ <= 11) {
        fill(this.palette[this.value_]);
      } else {
        fill(this.palette[12]);
      }
      rect(offset + (x * size + x * offset), offset + (y * size + y * offset), size, size);

      textAlign(CENTER, CENTER);
      textSize(26);
      if (this.value_ < 3) {
        fill(0);
      } else {
        fill(255);
      }
      let cen = floor(size / 2);
      text(pow(2, this.value_), offset + (x * size + x * offset) + cen,
        offset + (y * size + y * offset) + cen);
    } else {
      noStroke();
      fill(this.palette[0]);
      rect(offset + (x * size + x * offset), offset + (y * size + y * offset), size, size);

    }
  }


  value(val) {
    this.value_ = val;
  }

  update() {
    if (this.occ == false) {
      this.value_ = 0;
    }
  }



}