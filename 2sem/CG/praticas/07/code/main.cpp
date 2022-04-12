// #include <GL/freeglut_std.h>
#include <GL/glew.h>
#include <cmath>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>

#define _USE_MATH_DEFINES
#include <math.h>
#include <vector>

#include <IL/il.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

// unsigned char imagePath[1024] = "terreno.jpg";
unsigned char imagePath[1024] = "terreno.jpg";
// "/home/mari/3_Ano_Uni/2sem/CG/praticas/06/code/terreno.jpg";
// "/home/mari/wallpaper/aenami-mountains.jpg";
//
//

GLuint buffers[1];

float camX = 00, camY = 0, camZ = 0;
int startX, startY, tracking = 0;

int alpha = 0, beta = M_PI / 4, r = 50;

ILubyte *imageData;
ILuint t = 0;
unsigned int th, tw;

void changeSize(int w, int h) {

  // Prevent a divide by zero, when window is too short
  // (you cant make a window with zero width).
  if (h == 0)
    h = 1;

  // compute window's aspect ratio
  float ratio = w * 1.0 / h;

  // Reset the coordinate system before modifying
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();

  // Set the viewport to be the entire window
  glViewport(0, 0, w, h);

  // Set the correct perspective
  gluPerspective(45, ratio, 1, 1000);

  // return to the model view matrix mode
  glMatrixMode(GL_MODELVIEW);
}

float h(int i, int j) {
  if (i < 0)
    i = -i;
  if (j < 0)
    j = -j;
  int a = int((imageData[i * th + j]));

  return (a * 30 / 255.f);
}

void drawTrees() {
  srand(1);
  for (int i = 0; i < 200; i++) {
    int coord_x = rand() % tw - tw / 2;
    int coord_z = rand() % th - th / 2;
    glPushMatrix();
    glTranslatef(coord_x, h(coord_x, coord_z), coord_z);
    glRotatef(90, -1, 0, 0);
    glColor3f(0.4, 0.2, 0.2);
    glutSolidCone(0.5, 5, 10, 10);

    glTranslatef(0, 0, 2.5);
    glColor3f(0.1, 0.5, 0.2);
    glutSolidCone(1.5, 3.5, 10, 10);
    glPopMatrix();
  }
}

void drawTerrain() {

  // colocar aqui o código de desnho do terreno usando VBOs com TRIANGLE_STRIPS
  for (int i = 0; i < th - 1; i++) {
    glBegin(GL_TRIANGLE_STRIP);

    for (int j = 0; j < tw - 1; j++) {
      float color = imageData[i * tw + j];
      glColor3d(color / 255, color / 255, color / 255);
      glVertex3f(i - th / 2.f, h(i, j), j - tw / 2.f);
      glVertex3f(i + 1 - th / 2.f, h(i + 1, j), j - tw / 2.f);
    }

    glEnd();
  }
}

void renderScene(void) {

  float pos[4] = {-1.0, 1.0, 1.0, 0.0};

  glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  glLoadIdentity();

  gluLookAt(camX, camY, camZ, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);
  // gluLookAt(camX, h(camX, camZ) + 10, camZ, camX + sin(alpha),
  //           h(camX, camZ) + 10, camZ + cos(alpha), 0.0f, 1.0f, 0.0f);

  drawTerrain();
  drawTrees();
  glutSwapBuffers();
}

void processKeys(unsigned char key, int xx, int yy) {
  switch (key) {
  case 'q':
    exit(0);
  }
}

void processMouseButtons(int button, int state, int xx, int yy) {

  if (state == GLUT_DOWN) {
    startX = xx;
    startY = yy;
    if (button == GLUT_LEFT_BUTTON)
      tracking = 1;
    else if (button == GLUT_RIGHT_BUTTON)
      tracking = 2;
    else
      tracking = 0;
  } else if (state == GLUT_UP) {
    if (tracking == 1) {
      alpha += (xx - startX);
      beta += (yy - startY);
    } else if (tracking == 2) {

      r -= yy - startY;
      if (r < 3)
        r = 3.0;
    }
    tracking = 0;
  }
}

void processMouseMotion(int xx, int yy) {

  int deltaX, deltaY;
  int alphaAux, betaAux;
  int rAux;

  if (!tracking)
    return;

  deltaX = xx - startX;
  deltaY = yy - startY;

  if (tracking == 1) {

    alphaAux = alpha + deltaX;
    betaAux = beta + deltaY;

    if (betaAux > 85.0)
      betaAux = 85.0;
    else if (betaAux < -85.0)
      betaAux = -85.0;

    rAux = r;
  } else if (tracking == 2) {

    alphaAux = alpha;
    betaAux = beta;
    rAux = r - deltaY;
    if (rAux < 3)
      rAux = 3;
  }
  camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
  camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
  camY = rAux * sin(betaAux * 3.14 / 180.0);
}

void init() {

  // 	Load the height map "terreno.jpg"
  ilInit();
  ilGenImages(1, &t);
  ilBindImage(t);
  ilLoadImage((ILstring)imagePath);
  ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);
  imageData = ilGetData();
  tw = ilGetInteger(IL_IMAGE_WIDTH);
  th = ilGetInteger(IL_IMAGE_HEIGHT);
}

int main(int argc, char **argv) {

  // init GLUT and the window
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(100, 100);
  glutInitWindowSize(320, 320);
  glutCreateWindow("CG@DI-UM");

  // Required callback registry
  glutDisplayFunc(renderScene);
  glutIdleFunc(renderScene);
  glutReshapeFunc(changeSize);

  // Callback registration for keyboard processing
  glutKeyboardFunc(processKeys);
  // glutMouseFunc(processMouseButtons);
  // glutPassiveMotionFunc(processMouseMotion);
  glutMouseFunc(processMouseButtons);
  glutMotionFunc(processMouseMotion);

  init();
  // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
  // glEnable(GL_CULL_FACE);

  // enter GLUT's main cycle
  glutMainLoop();

  return 0;
}
