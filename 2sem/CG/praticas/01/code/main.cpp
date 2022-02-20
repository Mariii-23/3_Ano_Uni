#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>
#include <stdio.h>

float test = 0;
float time = 0;

void changeSize(int w, int h) {
  // prevent a divide by zero, when window is too short
  // (you can not make a window with zero width).
  if (h == 0)
    h = 1;
  // compute window's aspect ratio
  float ratio = w * 1.0f / h;

  // set the projection matrix as current
  glMatrixMode(GL_PROJECTION);
  // Load the identity matrix
  glLoadIdentity();
  // set the perspective
  gluPerspective(45.0f, ratio, 1.0f, 1000.0f);
  // return to the model view matrix mode
  glMatrixMode(GL_MODELVIEW);

  // et the viewport to be the entire window
  glViewport(0, 0, w, h);
}

void renderScene(void) {
  // clear buffers
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  // set camera
  glLoadIdentity();
  gluLookAt(0.0f, 0.0f, 5.0f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);

  // put drawing instructions here

  // Resolucao atraves dos frames
  // glutWireTeapot(abs(sin(test)));
  // test += 0.001;

  // Atraves do tempo -> independente dos frames
  float currentTime = glutGet(GLUT_ELAPSED_TIME);
  test += (currentTime - time) / 750;
  time = currentTime;
  glutWireTeapot(abs(sin(test)));

  // End of frame
  glutSwapBuffers();
}

void printInfo() {

  printf("Vendor: %s\n", glGetString(GL_VENDOR));
  printf("Renderer: %s\n", glGetString(GL_RENDERER));
  printf("Version: %s\n", glGetString(GL_VERSION));
}

int main(int argc, char **argv) {
  // put GLUT init here
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(100, 100);
  glutInitWindowSize(800, 800);
  glutCreateWindow("01");

  // put callback registry here
  glutReshapeFunc(changeSize);
  glutIdleFunc(renderScene);
  glutDisplayFunc(renderScene);

  // some OpenGL settings
  glEnable(GL_DEPTH_TEST);
  glEnable(GL_CULL_FACE);
  glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

  // enter GLUTs main cycle
  glutMainLoop();

  return 1;
}
