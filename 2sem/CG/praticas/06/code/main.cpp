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
#include <GL/glew.h>
#include <GL/glut.h>
#endif

static int line = GL_FILL;
static bool SHOW_AXIS = false;

float camX = 00, camY = 30, camZ = 40;
int startX, startY, tracking = 0;

int alpha = 0, beta = 45, r = 50;

GLuint *vertex;
double vertexCount;

unsigned int t, tw, th;
unsigned char *imageData;
// unsigned char imagePath[1024] = "terreno.jpg";
unsigned char imagePath[1024] = "terreno.jpg";
// "/home/mari/3_Ano_Uni/2sem/CG/praticas/06/code/terreno.jpg";
// "/home/mari/wallpaper/aenami-mountains.jpg";

float h(int i, int j) {
  if (i < 0)
    i = -i;
  if (j < 0)
    j = -j;
  int a = int((imageData[i * th + j]));

  return (a * 30 / 255.f);
}

double h1(int i, int j) {
  double x = imageData[i * tw + j];
  return x / 255 * 30;
}

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

void drawTerrain() {

  // sem vbos
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

  // colocar aqui o código de desenho do terreno usando VBOs com TRIANGLE_STRIPS
  for (int strip = 0; strip < th - 1; strip++) {
    glBindBuffer(GL_ARRAY_BUFFER, vertex[strip]);
    glVertexPointer(3, GL_DOUBLE, 0, 0);
  }

  glDrawArrays(GL_TRIANGLE_STRIP, 0, vertexCount);
}

void drawTrees() {
  srand(1);
  for (int i = 0; i < 200; i++) {
    int coord_x = rand() % tw - tw / 2;
    int coord_z = rand() % th - th / 2;
    // std::cout << sqrt(coord_x * coord_x + coord_z * coord_z) << "\n";
    // if(sqrt(coord_x * coord_x + coord_z * coord_z) > 50){
    glPushMatrix();
    glTranslatef(coord_x, h(coord_x, coord_z), coord_z);
    // std::cout << h(coord_x, coord_z) <<"\n";
    glRotatef(90, -1, 0, 0);
    glColor3f(0.4, 0.2, 0.2);
    glutSolidCone(0.5, 5, 10, 10);

    glTranslatef(0, 0, 2.5);
    glColor3f(0.1, 0.5, 0.2);
    glutSolidCone(1.5, 3.5, 10, 10);
    glPopMatrix();
    //}
  }
}

void renderScene(void) {

  float pos[4] = {-1.0, 1.0, 1.0, 0.0};

  glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  glLoadIdentity();
  gluLookAt(camX, camY, camZ, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);

  glPolygonMode(GL_FRONT_AND_BACK, line);

  // if (SHOW_AXIS)
  // {
  //   glBegin(GL_LINES);
  //   glColor3f(1.0f, 0.0f, 0.0f);
  //   glVertex3f(-100.0f, 0.0f, 0.0f);
  //   glVertex3f(100.0f, 0.0f, 0.0f);
  //   glColor3f(0.0f, 1.0f, 0.0f);
  //   glVertex3f(0.0f, -100.0f, 0.0f);
  //   glVertex3f(0.0f, 100.0f, 0.0f);
  //   glColor3f(0.0f, 0.0f, 1.0f);
  //   glVertex3f(0.0f, 0.0f, -100.0f);
  //   glVertex3f(0.0f, 0.0f, 100.0f);
  //   glEnd();
  // }

  drawTerrain();
  // drawTrees();

  // just so that it renders something before the terrain is built
  // to erase when the terrain is ready
  glutWireTeapot(2.0);

  // End of frame
  glutSwapBuffers();
}

void processKeys(unsigned char key, int xx, int yy) {
  switch (key) {
  case 'q':
    exit(0);

  case ',':
    switch (line) {
    case GL_POINT:
      line = GL_LINE;
      break;
    case GL_FILL:
      line = GL_POINT;
      break;
    default:
      line = GL_FILL;
      break;
    }
    break;
  case '.':
    SHOW_AXIS = !SHOW_AXIS;
    break;
  }
  // put code to process regular keys in here
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

void processSpecialKeys(int key, int xx, int yy) {

  switch (key) {

  case GLUT_KEY_RIGHT:
    alpha -= 0.1;
    break;

  case GLUT_KEY_LEFT:
    alpha += 0.1;
    break;

  case GLUT_KEY_UP:
    beta += 0.1f;
    if (beta > 1.5f)
      beta = 1.5f;
    break;

  case GLUT_KEY_DOWN:
    beta -= 0.1f;
    if (beta < -1.5f)
      beta = -1.5f;
    break;

  case GLUT_KEY_PAGE_DOWN:
    r -= 0.1f;
    if (r < 0.1f)
      r = 0.1f;
    break;

  case GLUT_KEY_PAGE_UP:
    r += 0.1f;
    break;
  }
  camX = r * cos(beta) * sin(alpha);
  camY = r * sin(beta);
  camZ = r * cos(beta) * cos(alpha);
  glutPostRedisplay();
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
  ilGenImages(1, &t);
  ilBindImage(t);
  // image containing our height map
  ilLoadImage((ILstring)imagePath);
  // ilLoadImage(
  //     (ILstring)
  //     "/home/mari/3_Ano_Uni/2sem/CG/praticas/06/code/terreno.jpg");
  // convert the image to single channel per pixel
  // with values ranging between 0 and 255
  ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);

  tw = ilGetInteger(IL_IMAGE_WIDTH);
  th = ilGetInteger(IL_IMAGE_HEIGHT);
  // imageData is a LINEAR array with the pixel values
  imageData = ilGetData();
  // std::cout << imageData << "\n";
  // std::cout << tw << " " << th << "\n";

  // 	Build the vertex arrays
  vertex = (GLuint *)calloc(th - 1, sizeof(GLuint));
  glEnableClientState(GL_VERTEX_ARRAY);
  glGenBuffers(th - 1, vertex);
  int half_Width = tw / 2.f;
  int half_Height = th / 2.f;
  vertexCount = 2 * tw;

  for (int num_strip = 0; num_strip < th - 1; num_strip++) {
    // glDrawArrays(GL_TRIANGLE_STRIP, (tw)*2 * num_strip, (tw)*2);
    std::vector<double> strip;
    for (int j = 0; j < tw; j++) {

      // float color = imageData[num_strip * tw + j];
      // glColor3d(color / 255, color / 255, color / 255);

      strip.push_back(num_strip - half_Height);
      strip.push_back(h(num_strip, j));
      strip.push_back(j - half_Width);

      strip.push_back(num_strip + 1 - half_Height);
      strip.push_back(h(num_strip + 1, j));
      strip.push_back(j - half_Width);
    }

    glGenBuffers(num_strip, vertex);
    glBindBuffer(GL_ARRAY_BUFFER, vertex[num_strip]);
    glBufferData(GL_ARRAY_BUFFER, strip.size() * sizeof(double), strip.data(),
                 GL_STATIC_DRAW);
  }

  // 	OpenGL settings
  // glEnable(GL_DEPTH_TEST);
  glEnable(GL_CULL_FACE);
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
  glutSpecialFunc(processSpecialKeys);
  glutMouseFunc(processMouseButtons);
  glutMotionFunc(processMouseMotion);

  glewInit();
  glEnableClientState(GL_VERTEX_ARRAY);

  ilInit();
  init();

  // enter GLUT's main cycle
  glutMainLoop();

  return 0;
}
