function setup() {
  createCanvas(950, 850);

  textAlign(CENTER, CENTER);
  textFont("Times new roman");
  
}

function draw() {
  background(42, 0, 0, 0);

  let hr = hour();
  let mn = minute();
  let sc = second();
  let dy = day();
  let mon = month();
  let yr = year();
  let r = 655;

  //Clock Background *********************************************************************
  noStroke();
  fill(0);
  ellipse(width / 2, height / 2, 735, 735);

  strokeWeight(5)
  stroke(21);
  noFill();
  ellipse(width / 2, height / 2, 447, 447);
  ellipse(width / 2, height / 2, 735, 735);
  ellipse(width / 2, height / 2, 595, 595);
  line(floor(width / 2)-100, floor(height / 2)+70, floor(width / 2)+100, floor(height / 2)+70);
  line(floor(width / 2)-100, floor(height / 2)-70, floor(width / 2)+100, floor(height / 2)-70);
  
  strokeWeight(3)
  ellipse(width / 2, height / 2, 491, 491);
  ellipse(width / 2, height / 2, 538, 538);

  for (i = 0; i < 12; i++) {
    push();
    translate(floor(width / 2), floor(height / 2));
    rotate(i * (PI / 6) + (PI / 4));
    line(0, floor(-735 / 2), 0, floor(-595 / 2));

    pop();

  }

  //clock time, date, weather ************************************************************
  strokeWeight(6);
  stroke(0);
  fill(175, 125, 20);
  textSize(76);
  
  if (mn < 10 && sc < 10)
  {
    text((hr % 12) + ":0" + mn + ":0" + sc + "pm", floor(width / 2) - 0, floor(height / 2) + 0);
  }
  else if (mn < 10)
  {
    text((hr % 12) + ":0" + mn + ":" + sc + "pm", floor(width / 2) - 0, floor(height / 2) + 0);
  }
  else if (sc < 10)
  {
    text((hr % 12) + ":" + mn + ":0" + sc + "pm", floor(width / 2) - 0, floor(height / 2) + 0);
  }
  else
  {
    text((hr % 12) + ":" + mn + ":" + sc + "pm", floor(width / 2) - 0, floor(height / 2) + 0);
  }
  
  textSize(48);
  text(mon + "/" + dy + "/" + yr, floor(width / 2) - 0, floor(height / 2) + 130);
  text("87°   8mph", floor(width / 2) - 0, floor(height / 2) - 130);

  //clock hands **************************************************************************
  stroke(175, 125, 20);
  noFill();

  if (sc != 0) {
    let endsc = ((sc - 15) / (3 * PI));
    let startsc = ((-(PI / 2)) + ((1.5) * (PI / 180)));
    strokeWeight(8);
    arc(width / 2, height / 2, 470, 470, startsc, endsc);
  }

  if (mn != 0) {
    let endmn = ((mn - 15) / (3 * PI));
    let startmn = ((-(PI / 2)) + ((1.75) * (PI / 180)));
    strokeWeight(12);
    arc(width / 2, height / 2, 515, 515, startmn, endmn);
  }

  if ((hr % 12) != 0) {
    let endhr = (((hr % 12) + (mn / 60)) * (PI / 6));
    let starthr = ((-(PI / 2)) + ((2) * (PI / 180)));
    strokeWeight(16);
    arc(width / 2, height / 2, 565, 565, starthr, endhr - (PI / 2));
  }

  //clock numbers ************************************************************************
  strokeWeight(5)
  stroke(0);
  fill(175, 125, 20);
  textSize(64);

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(0);
  text("XII", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(PI / 6.0);
  text("I", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(PI / 3.0);
  text("II", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(HALF_PI);
  text("III", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(2 * PI / 3);
  text("IV", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(5 * PI / 6);
  text("V", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(PI);
  text("VI", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(7 * PI / 6);
  text("VII", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(4 * PI / 3);
  text("VIII", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(3 * PI / 2);
  text("IX", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(5 * PI / 3);
  text("X", 0, floor(-r / 2));
  pop();

  push();
  translate(floor(width / 2), floor(height / 2));
  rotate(11 * PI / 6);
  text("XI", 0, floor(-r / 2));
  pop();
}

function gotWeather(weather) {
  //this.temp = (floor(weather.current.temp_f) + '&deg;');
  //this.windmph = (windmag + " <small>MPH</small>");
}