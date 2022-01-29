class Snake {

  constructor() {
    this.body = [];
    this.body[0] = createVector(0, 0);
    this.length = 1;
    this.vx = 0;
    this.vy = 0;
    this.rainbow = false;

    this.dead = false;
  }

  show(scl) {
    strokeWeight(1);
    stroke(21);
    for (let i = this.length; i > 0; i--) {
      if (this.rainbow) {
        fill(random(50, 200), random(100, 255), random(50, 200));
      } else {
        fill(0, 255, 0);
      }
      rect(this.body[i - 1].x * scl + 1, this.body[i - 1].y * scl + 1, scl, scl);
    }
  }

  grow() {
    let head = this.body[this.length - 1].copy();
    this.length++;
    this.body.push(head);
  }

  move(scl) {
    this.checkDeath(scl);

    let head = this.body[this.length - 1].copy();
    this.body.shift();
    head.x += this.vx;
    head.y += this.vy;
    this.body.push(head);
    this.show(scl);

    this.inBounds(scl);


  }

  inBounds(scl) {
    if (this.body[this.length - 1].x == -1) {
      this.body[this.length - 1].x = 0;
      this.dead = true;
    }
    if (this.body[this.length - 1].x == floor(width / scl)) {
      this.body[this.length - 1].x = floor(width / scl) - 1;
      this.dead = true;
    }
    if (this.body[this.length - 1].y == -1) {
      this.body[this.length - 1].y = 0;
      this.dead = true;
    }
    if (this.body[this.length - 1].y == floor(height / scl)) {
      this.body[this.length - 1].y = floor(height / scl) - 1;
      this.dead = true;
    }
  }


  checkDeath(scl) {
    if ((this.body[this.length - 1].x == 0 && this.vx == -1) ||
      (this.body[this.length - 1].x == floor(width / scl) - 1 && this.vx == 1)) {
      this.dead = true;
    } else if ((this.body[this.length - 1].y == 0 && this.vy == -1) ||
      (this.body[this.length - 1].y == floor(height / scl) - 1 && this.vy == 1)) {
      this.dead = true;
    } else {
      this.dead = false;
    }

    let head = this.body[this.length - 1].copy();
    for (let i = 0; i < this.length - 2; i++) {
      if (head.x == this.body[i].x && head.y == this.body[i].y) {
        this.dead = true;
      }
    }

  }

  isDead() {
    if (this.dead) {
      print("DEAD");
    }
    return this.dead;
  }


}