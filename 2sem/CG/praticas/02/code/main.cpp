#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <math.h>

float angle = 0;

float translateX = 0.1;
float translateY = 0.1;
float translateZ = 0.1;

float scaleX = 1.0;
float scaleY = 1.0;
float scaleZ = 1.0;

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

  // clear buffers
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  // set the camera
  glLoadIdentity();
  gluLookAt(5.0, 5.0, 5.0, 0.0, 0.0, 0.0, 0.0f, 1.0f, 0.0f);

  // Axis
  glBegin(GL_LINES);
  // X axis in red
  glColor3f(1.0f, 0.0f, 0.0f);
  glVertex3f(-100.0f, 0.0f, 0.0f);
  glVertex3f(100.0f, 0.0f, 0.0f);
  // Y axis in Green
  glColor3f(0.0f, 1.0f, 0.0f);
  glVertex3f(0.0f, -100.0f, 0.0f);
  glVertex3f(0.0f, 100.0f, 0.0f);
  // Z axis in Blue
  glColor3f(0.0f, 0.0f, 1.0f);
  glVertex3f(0.0f, 0.0f, -100.0f);
  glVertex3f(0.0f, 0.0f, 100.0f);
  glEnd();

  // put the geometric transformations here
  glTranslated(translateX, translateY, translateZ);
  glRotated(angle, 0.0, 1.0, 0.0);
  glScalef(scaleX, scaleY, scaleZ);

  // put drawing instructions here
  glBegin(GL_TRIANGLES);

  glColor3f(1.0f, 0.0f, 1.0f);
  glVertex3f(-1.0f, 0.0f, -1.0f);
  glVertex3f(1.0f, 0.0f, -2.0f);
  glVertex3f(1.0f, 0.0f, 1.0f);

  glVertex3f(-1.0f, 0.0f, -1.0f);
  glVertex3f(1.0f, 0.0f, 2.0f);
  glVertex3f(-1.0f, 0.0f, -1.0f);

  glColor3f(1.0f, 0.0f, 0.0f);
  glVertex3f(1.0f, 0.0f, -1.0f);
  glVertex3f(0.0f, 1.0f, 0.0f);
  glVertex3f(1.0f, 0.0f, 1.0f);

  glColor3f(0.0f, 1.0f, 0.0f);
  glVertex3f(-1.0f, 0.0f, 1.0f);
  glVertex3f(1.0f, 0.0f, 1.0f);
  glVertex3f(0.0f, 1.0f, 0.0f);

  glColor3f(0.0f, 0.0f, 1.0f);
  glVertex3f(-1.0f, 0.0f, -1.0f);
  glVertex3f(-1.0f, 0.0f, 1.0f);
  glVertex3f(0.0f, 1.0f, 0.0f);

  glColor3f(1.0f, 1.0f, 0.0f);
  glVertex3f(1.0f, 0.0f, -1.0f);
  glVertex3f(-1.0f, 0.0f, -1.0f);
  glVertex3f(0.0f, 1.0f, 0.0f);

  glEnd();

  glEnable(GL_CULL_FACE);
  // GL_FRONT ou GL_Back
  glCullFace(GL_FRONT);

  // GL_CW ou GL_CCW
  glFrontFace(GL_CW);

  // End of frame
  glutSwapBuffers();
}

// write function to process keyboard events
void controlKeyInput(unsigned char key, int x, int y) {
  switch (key) {
  // change X scale
  case 'q':
    scaleX += 0.1;
    break;
  case 'a':
    scaleX -= 0.1;
    break;

  // change Z scale
  case 'w':
    scaleZ += 0.1;
    break;
  case 's':
    scaleZ -= 0.1;
    break;

  // change y scale
  case 'e':
    scaleY += 0.1;
    break;
  case 'd':
    scaleY -= 0.1;
    break;

  // change angle
  case 'h':
    angle += 0.5;
    break;
  case 'l':
    angle -= 0.5;
    break;

  // translate in X
  case 'z':
    translateX += 0.1;
    break;
  case 'x':
    translateX -= 0.1;
    break;

  // translate in Z
  case 'c':
    translateZ += 0.1;
    break;
  case 'v':
    translateZ -= 0.1;
    break;

  // translate in Y
  case 'b':
    translateY += 0.1;
    break;
  case 'n':
    translateY -= 0.1;
    break;
  }

  glutPostRedisplay();
}

int main(int argc, char **argv) {

  // init GLUT and the window
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(100, 100);
  glutInitWindowSize(800, 800);
  glutCreateWindow("CG@DI-UM");

  // Required callback registry
  glutDisplayFunc(renderScene);
  glutReshapeFunc(changeSize);

  // put here the registration of the keyboard callbacks
  glutKeyboardFunc(controlKeyInput);

  //  OpenGL settings
  glEnable(GL_DEPTH_TEST);
  glEnable(GL_CULL_FACE);

  // enter GLUT's main cycle
  glutMainLoop();

  return 1;
}
