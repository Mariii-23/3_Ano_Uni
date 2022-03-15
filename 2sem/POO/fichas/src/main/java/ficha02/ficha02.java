package ficha02;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ficha02 {
    static Scanner sc =new Scanner(System.in);

    int[] readArray() {
        System.out.println("Length of array: ");
        int len = sc.nextInt();
        System.out.println("Write one element at one time:");
        int[] r = new int[len];
        for (int i = 0; i<len; i++) {
            r[i] = sc.nextInt();
        }
        return r;
    }

    void print(int[] array){
        assert array.length > 0;
        for (int j : array) {
            System.out.print(j + " , ");
        }
        System.out.println("");
    }

    int maxElem(int[] array){
        assert array.length > 0;
        int max = array[0];
        for (int i = 1; i<array.length; i++) {
            if (max < array[i])
                max = array[i];
        }
        return  max;
    }


    /** Exercise 1 **/

    void ex1_1() {
        System.out.println("Ex1 a :: Max");
        System.out.println("Array::");
        int[] array = readArray();
        print(array);
        System.out.println("MaX :: "+ maxElem(array));
    }

    void ex1_2() {
        System.out.println("Ex1 b :: between");
        System.out.println("Array::");
        int[] array = readArray();
        print(array);
        System.out.println("Initial");
        int ii = sc.nextInt();

        System.out.println("End");
        int iF = sc.nextInt();
        int[] r = new int[iF-ii];
        System.arraycopy(array,ii,r,0 , ii- iF);
        System.out.println("Result");
        print(r);
    }

    int[] elemsInCommon(int[] a1, int[] a2) {
        var r = Arrays.stream(a1).filter(e -> Arrays.stream(a2).anyMatch(e2-> e == e2));
        return r.toArray();
    }

    void ex1_3() {
        System.out.println("Ex1 c :: Elems in common");
        System.out.println("Array1::");
        int[] array1 = readArray();
        print(array1);

        System.out.println("Array2::");
        int[] array2 = readArray();
        print(array2);

        int[] r = elemsInCommon(array1, array2);
        System.out.println("Result");
        print(r);
    }

    /* Exercise 2 **/

    /**
     * This is the exercise number 2 a)
     * @return Grades of all students
     */
    int[][] buildGrades() {
        System.out.println("Number of students:");
        int n_students = sc.nextInt();
        System.out.println("Number of curricular units:");
        int n_u = sc.nextInt();

        int[][] r = new int[n_students][n_u];
        for(int i=0; i<n_students; i++) {
            System.out.println("Grades Student :: " +  i);
            for (int j=0; j<n_u; j++)
                r[i][j] = sc.nextInt();
        }
        return r;
    }

    int sumGrades(int[][] grades) {
        return Arrays.stream(grades).mapToInt(e-> Arrays.stream(e).sum()).sum();
    }

    void ex2_b() {
        System.out.println("EX 2 b");
        System.out.println("Read grades::");
        var grades = buildGrades();
        System.out.println("SUM :: "+ sumGrades(grades));
    }

    double average(int[][] grades, int i) {
        assert grades.length > i;
        return Arrays.stream(grades[i]).average().orElse(0);
    }

    void ex2_c() {
        System.out.println("EX 2 c");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Average of student :: ");
        int n = sc.nextInt();

        System.out.println("Average:: " + average(grades, n));
    }

    double average_curricular(int[][] grades, int n) {
        assert  grades != null;
        assert  grades[0].length > n;
        return Arrays.stream(grades).mapToDouble(e-> e[n]).sum();
    }

    void ex2_d() {
        System.out.println("EX 2 d");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Average of curricular unit :: ");
        int n = sc.nextInt();

        System.out.println("Average:: " + average_curricular(grades, n));
    }

    int max_curricular(int[][] grades, int N) {
        int max = grades[0][N];
        for (int[] elem : grades)
            if (max < elem[N])
                max = elem[N];
        return max;
    }

    int[] max_curricular_units(int[][] grades) {
        int[] r = new int[grades[0].length];

        for (int i = 0; i< grades[0].length; i++) {
            r[i] = max_curricular(grades, i);
        }
        return r;
    }

    void ex2_other() {
        System.out.println("EX 2 e");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Max of all curricular unit :: ");
        System.out.println("Result");
        print(max_curricular_units(grades));
    }

    int[] max_grades_students(int[][] grades) {
        int[] r = new int[grades.length];
        for (int i = 0; i< grades.length; i++)
            r[i] = Arrays.stream(grades[i]).max().orElse(0);
        return r;
    }

    int[] min_grades_students(int[][] grades) {
        int[] r = new int[grades.length];
        for (int i = 0; i< grades.length; i++)
            r[i] = Arrays.stream(grades[i]).min().orElse(0);
        return r;
    }

    void ex2_e() {
        System.out.println("EX 2 e");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Max of students grade :: ");
        System.out.println("Result");
        print(max_grades_students(grades));
    }

    void ex2_f() {
        System.out.println("EX 2 f");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Min of students grade :: ");
        System.out.println("Result");
        print(min_grades_students(grades));
    }

    int[] grades_above(int[][] grades, int value) {
        int[] r = new int[grades[0].length + grades.length];
        int n = 0;
        for (int[] elems: grades) {
            for (int elem : elems) {
                if (elem >= value)  {
                    r[n] = elem;
                    n++;
                }
            }
        }
       return r;
    }

    void ex2_g() {
        System.out.println("EX 2 f");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Grades above :: ");
        int above = sc.nextInt();
        System.out.println("Result");
        print(grades_above(grades, above));
    }

    void print_grades_students(int[][] grades, int N) {
        for (int elem : grades[N])
            System.out.print(elem);
    }

    void ex2_h() {
        System.out.println("EX 2 h");
        System.out.println("Read grades::");
        var grades = buildGrades();

        System.out.println("Student number :: ");
        int number = sc.nextInt();
        System.out.println("Result");
        print_grades_students(grades, number);
    }

    int max_indices(int[] array) {
        int max = array[0];
        int n = 0;
        for (int i = 1; i< array.length; i++) {
            if (max < array[i]) {
                max = array[i];
                n = i;
            }
        }
        return n;
    }

    void ex2_i() {
        System.out.println("EX 2 i");
        System.out.println("Read grades::");
        var grades = buildGrades();
        int[] best = max_curricular_units(grades);
        System.out.println("Result :: " +  max_indices(best));
    }

    /* Exercise 3 **/

    private class ex3 {
        private final LocalDate[] localDates;
        private int used; // How many elems we have
        public final int size; // size of the array

        public ex3(int size) {
            this.localDates = new LocalDate[size];
            this.size = size;
            this.used = 0;
        }

        public void insert_date(LocalDate date) {
            assert used < size;
            localDates[used] = date;
            used++;
        }

        public  LocalDate closerDate(LocalDate date) {
            assert used > 0;
            LocalDate r =  localDates[0];
            int closer = date.compareTo(r);
            for (int j = 1; j < used; j++){
                int other = date.compareTo(localDates[j]);
                if (other < closer) {
                    closer = other;
                    r = localDates[j];
                }
            }
            return r;
        }

        @Override
        public String toString() {
            return "localDates=" + Arrays.toString(localDates) + "\n";
        }
    }

    /* Exercise 4 **/
    //TODO

    /* Exercise 5 **/
    //TODO

    /* Exercise 6 **/
    //TODO

    /* Exercise 7 **/
    void ex7() {
        int[] euromilhoes = new int[5];
        int[] estrelas = new int[2];
        Random random = new Random();
        for (int i = 0; i<5; i++)
            euromilhoes[i] = random.nextInt(1 - 50) + 1;

        for (int i = 0; i<2; i++)
            estrelas[i] = random.nextInt(1 - 9) + 1;

        System.out.println("Give 5 number, one by one: ");
        int[] e1 = new int[5];
        int[] e2 = new int[2];
        for (int i = 0; i<5; i++)
            e1[i] = sc.nextInt();

        for (int i = 0; i<2; i++)
            e2[i] = sc.nextInt();
        System.out.println("EUROMILHOES: ");
        print(euromilhoes);
        System.out.println("STARS");
        print(estrelas);
        System.out.println("OURS: ");
        print(e1);
        System.out.println("STARS");
        print(e2);

        //FIXME change equals to other method
        if (euromilhoes.equals(e1) && estrelas.equals(e2)  ) {
            int i = 0;
            for (int j=0; j<i; j++)
                System.out.print(" ");
            System.out.println("GANHAS TEEE");
        }
    }

    public static void main(String[] _args) {
        ficha02 f = new ficha02();
        f.ex1_1();
        f.ex1_2();
        f.ex1_3();

        f.ex2_b();
        f.ex2_c();
        f.ex2_d();
        f.ex2_e();
        f.ex2_f();
        f.ex2_g();
        f.ex2_h();
        f.ex2_i();
        //f.ex2_other(); /* Other */
    }
}
