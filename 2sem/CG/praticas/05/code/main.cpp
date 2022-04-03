#include <stdio.h>
#include <time.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

float alfa = 0.0f, beta = 0.5f, radius = 100.0f;
float camX, camY, camZ;

void spherical2Cartesian() {

  camX = radius * cos(beta) * sin(alfa);
  camY = radius * sin(beta);
  camZ = radius * cos(beta) * cos(alfa);
}

static int treeRand = 1;

static const int r = 50;
static const int ri = 35;
static const int rc = 15;

static const int n = 500;
static const int ni = 10;
static const int nc = 5;

static float rotate = 0;
static float speed = 0.1;

void changeSize(int w, int h) {

  // Prevent a divide by zero, when window is too short
  // (you cant make a window with zero width).
  if (h == 0)
    h = 1;

  // compute window's aspect ratio
  float ratio = w * 1.0 / h;

  // Set the projection matrix as current
  glMatrixMode(GL_PROJECTION);
  // Load Identity Matrix
  glLoadIdentity();

  // Set the viewport to be the entire window
  glViewport(0, 0, w, h);

  // Set perspective
  gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

  // return to the model view matrix mode
  glMatrixMode(GL_MODELVIEW);
}

void renderScene(void) {

  srand(3);

  // clear buffers
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  // set the camera
  glLoadIdentity();
  gluLookAt(camX, camY, camZ, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);

  glColor3f(0.2f, 0.8f, 0.2f);
  glBegin(GL_TRIANGLES);
  glVertex3f(100.0f, 0, -100.0f);
  glVertex3f(-100.0f, 0, -100.0f);
  glVertex3f(-100.0f, 0, 100.0f);

  glVertex3f(100.0f, 0, -100.0f);
  glVertex3f(-100.0f, 0, 100.0f);
  glVertex3f(100.0f, 0, 100.0f);
  glEnd();
  // End of frame

  // put code to draw scene in here

  // marcar o centro
  glPushMatrix();
  glColor3f(1.0f, 0.0f, 0.0f);
  glutSolidTorus(1, 2, 30, 30);
  glPopMatrix();

  // circulo pequeno
  for (int i = 0; i < nc; i++) {
    glPushMatrix();
    glColor3f(0.2f, 0.4f, 1.0f);
    glRotatef(-rotate + i * 360 / nc, 0, 1, 0);
    glTranslated(rc, 2, 0);
    glutSolidTeapot(2);
    glPopMatrix();
  }

  // medio
  for (int i = 0; i < ni; i++) {
    glPushMatrix();
    glColor3f(0.2f, 0.44f, 0.54f);
    glRotatef(rotate + i * 360 / ni, 0, 1, 0);
    glTranslated(ri, 2, 0);
    glutSolidTeapot(2);
    glPopMatrix();
  }

  // arvores
  for (int i = 0; i < n; i++) {
    // vamos garantir q as arvores sao criadas a um raio de pelo menos 50
    // x² + z² < r²
    double x = 0, z = 0;
    // srand(treeRand + i);
    while (pow(x, 2) + pow(z, 2) < pow(r, 2)) {
      x = (rand() % (r * 4)) - (r * 2);
      z = (rand() % (r * 4)) - (r * 2);
    }

    glPushMatrix();
    glTranslated(x, 1, z);
    glRotated(-90, 1, 0, 0);
    // construir a arvore
    // tronco
    glColor3ub(153, 0, 76);
    glutSolidCone(1, 3, 30, 30);
    // cima
    glColor3ub(204, 102, 0);
    glTranslated(0, 0, 2);
    glutSolidCone(2, 4, 30, 30);

    glPopMatrix();
  }

  rotate += speed;
  glutSwapBuffers();
  glutPostRedisplay();
}

void processKeys(unsigned char c, int xx, int yy) {

  // put code to process regular keys in here
  switch (c) {
  case 'q':
    exit(0);

  case 's':
    treeRand += rand();

  case '+':
    radius -= 1.0f;
    if (radius < 1.0f)
      radius = 1.0f;
    break;

  case '-':
    radius += 1.0f;
    break;

  case 'z':
    speed += 0.1f;
    break;

  case 'x':
    speed -= 0.1f;
    break;
  }

  spherical2Cartesian();
  glutPostRedisplay();
}

void processSpecialKeys(int key, int xx, int yy) {

  switch (key) {

  case GLUT_KEY_RIGHT:
    alfa -= 0.1;
    break;

  case GLUT_KEY_LEFT:
    alfa += 0.1;
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
    radius -= 1.0f;
    if (radius < 1.0f)
      radius = 1.0f;
    break;

  case GLUT_KEY_PAGE_UP:
    radius += 1.0f;
    break;
  }
  spherical2Cartesian();
  glutPostRedisplay();
}

void printInfo() {

  printf("Vendor: %s\n", glGetString(GL_VENDOR));
  printf("Renderer: %s\n", glGetString(GL_RENDERER));
  printf("Version: %s\n", glGetString(GL_VERSION));

  printf("\nUse Arrows to move the camera up/down and left/right\n");
  printf("Home and End control the distance from the camera to the origin");
}

int main(int argc, char **argv) {

  // start the randow number sequence
  // srand(time(NULL));

  // init GLUT and the window
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(100, 100);
  glutInitWindowSize(800, 800);
  glutCreateWindow("CG@DI-UM");

  // Required callback registry
  glutDisplayFunc(renderScene);
  glutReshapeFunc(changeSize);

  // Callback registration for keyboard processing
  glutKeyboardFunc(processKeys);
  glutSpecialFunc(processSpecialKeys);

  //  OpenGL settings
  glEnable(GL_DEPTH_TEST);
  // glEnable(GL_CULL_FACE);

  spherical2Cartesian();

  printInfo();

  // enter GLUT's main cycle
  glutMainLoop();

  return 1;
}
