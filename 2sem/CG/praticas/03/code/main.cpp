#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

static float radiusCamera = 10;
static float alpha = 0;
static float beta = M_PI / 100;
static float look_x = 0.0;
static float look_y = 0.0;
static float look_z = 0.0;
static float x = look_x + radiusCamera * cos(beta) * sin(alpha);
static float z = look_z + radiusCamera * cos(beta) * cos(alpha);
static float y = look_y + radiusCamera * sin(beta);

// true -> fps, false -> Follow
static bool fps = false;

static int slices = 10;
static float radius = 1;
static float height = 2;
static float SCALE = 1;
static bool SHOW_AXIS = false;
static int line = GL_FILL;

struct Polar
{
  float radius;
  float alpha;
  float beta;
};

struct Point
{
  float x;
  float y;
  float z;
};

Point polar_to_point(Polar polar)
{
  const float x_ = polar.radius * cos(polar.beta) * sin(polar.alpha);
  const float y_ = polar.radius * sin(polar.beta);
  const float z_ = polar.radius * cos(polar.beta) * cos(polar.alpha);
  return Point{x_, y_, z_};
}

void changeSize(int w, int h)
{

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

void drawCylinder(float radius, float height, int slices)
{
  // put code to draw cylinder in here

  const float baseH = height / 2;
  const float alpha = (2 * M_PI) / slices;

  Polar baseP = {baseH, 0, -M_PI / 2};
  Point bP = polar_to_point(baseP);

  // BASE
  glBegin(GL_TRIANGLE_FAN);
  glVertex3f(bP.x, bP.y, bP.z);
  for (int i = slices; i >= 0; i--)
  {

    Polar p = {sqrt(float(pow(baseH, 2) + pow(radius, 2))), alpha * i,
               -atan(baseH / radius)};
    glColor3f(0.2f, 0.4f, 1.0f);
    Point p_ = polar_to_point(p);
    glVertex3f(p_.x, p_.y, p_.z);
  }
  glEnd();

  // TOP
  glBegin(GL_TRIANGLE_FAN);
  glVertex3f(bP.x, -bP.y, bP.z);
  for (int i = 0; i <= slices; i++)
  {

    Polar p = {sqrt(float(pow(baseH, 2) + pow(radius, 2))), alpha * i,
               atan(baseH / radius)};
    glColor3f(0.2f, 0.4f, 1.0f);
    Point p_ = polar_to_point(p);
    glVertex3f(p_.x, p_.y, p_.z);
  }
  glEnd();

  // SIDE
  glBegin(GL_TRIANGLE_STRIP);
  for (int i = 0; i <= slices; i++)
  {

    Polar pb = {sqrt(float(pow(baseH, 2) + pow(radius, 2))), alpha * i,
                -atan(baseH / radius)};
    Polar pt = {sqrt(float(pow(baseH, 2) + pow(radius, 2))), alpha * i,
                atan(baseH / radius)};
    if (i % 2 == 0)
      glColor3f(0.2f, 0.44f, 0.54f);
    else
      glColor3f(0.2f, 0.44f, 0.8f);

    Point pb_ = polar_to_point(pb);
    Point pt_ = polar_to_point(pt);
    glVertex3f(pt_.x, pt_.y, pt_.z);
    glVertex3f(pb_.x, pb_.y, pb_.z);
  }
  glEnd();
}

void renderScene(void)
{

  // clear buffers
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  // set the camera
  glLoadIdentity();

  if (fps)
    gluLookAt(x, y, z, x + look_x, y + look_y, z + look_z, 0.0f, 1.0f, 0.0f);

  else
    gluLookAt(x, y, z, look_x, look_y, look_z, 0.0f, 1.0f, 0.0f);

  if (SHOW_AXIS)
  {
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
  }

  glScalef(SCALE, SCALE, SCALE);

  glPolygonMode(GL_FRONT_AND_BACK, line);

  drawCylinder(radius, height, slices);

  // End of frame
  glutSwapBuffers();
}

void processKeys(unsigned char key, int _x, int _y)
{
  // put code to process regular keys in here
  switch (key)
  {
  case '+':
    SCALE += 0.1;
    break;
  case '-':
    SCALE -= 0.1;
    break;

  case '.':
    SHOW_AXIS = !SHOW_AXIS;
    break;

  case 'f':
    fps = !fps;
    break;

  case ',':
    switch (line)
    {
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

  case 'q':
    exit(0);

  // Change radius
  case 'z':
    radius += 0.5;
    break;
  case 'x':
    radius -= 0.5;
    break;

  // Change height
  case 'c':
    height += 0.5;
    break;
  case 'v':
    height -= 0.5;
    break;

  // Change slices
  case 'b':
    slices += 1;
    break;
  case 'n':
    slices -= 1;
    break;
  }
}

void processSpecialKeys(int key, int xx, int yy)
{

  // put code to process special keys in here
  switch (key)
  {
  case GLUT_KEY_LEFT:
    alpha += 0.1;
    break;
  case GLUT_KEY_RIGHT:
    alpha -= 0.1;
    break;
  case GLUT_KEY_UP:
    if (beta + 0.1 < M_PI / 2)
      beta += 0.1;
    break;
  case GLUT_KEY_DOWN:
    if (beta - 0.1 > -M_PI / 2)
      beta -= 0.1;
    break;
  case GLUT_KEY_F1:
    radiusCamera += 0.1;
    break;
  case GLUT_KEY_F2:
    radiusCamera -= 0.1;
    break;
  }

  if (fps)
  {
    look_x = cos(beta) * sin(alpha);
    look_z = cos(beta) * cos(alpha);
    look_y = sin(beta);
  }
  else
  {
    x = look_x + radiusCamera * cos(beta) * sin(alpha);
    z = look_z + radiusCamera * cos(beta) * cos(alpha);
    y = look_y + radiusCamera * sin(beta);
  }
}

int main(int argc, char **argv)
{

  // init GLUT and the window
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(100, 100);
  glutInitWindowSize(800, 800);
  glutCreateWindow("CG@DI-UM");

  // some OpenGL settings
  glPolygonMode(GL_FRONT, GL_FILL);
  glEnable(GL_BLEND);
  glEnable(GL_DEPTH_TEST);
  glEnable(GL_CULL_FACE);

  // Required callback registry
  glutDisplayFunc(renderScene);
  glutReshapeFunc(changeSize);
  glutIdleFunc(renderScene);

  // Callback registration for keyboard processing
  glutKeyboardFunc(processKeys);
  glutSpecialFunc(processSpecialKeys);

  //  OpenGL settings
  glEnable(GL_DEPTH_TEST);
  glEnable(GL_CULL_FACE);

  // enter GLUT's main cycle
  glutMainLoop();

  return 1;
}
