class Food {
  constructor() {
    this.x = 0
    this.y = 0;
  }


  show(scl) {
    fill(255, 0, 0);
    strokeWeight(1);
    stroke(21);
    rect(this.x * scl + 1, this.y * scl + 1, scl, scl);
  }

  update(scl) {
    this.x = floor(random(0, (width - 2) / scl));
    this.y = floor(random(0, (height - 2) / scl));
  }

}