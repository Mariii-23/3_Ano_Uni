

package smartDevices;
/*********************************************************************************/
/** DISCLAIMER: Este código foi criado e alterado durante as aulas práticas      */
/** de POO. Representa uma solução em construção, com base na matéria leccionada */ 
/** até ao momento da sua elaboração, e resulta da discussão e experimentação    */
/** durante as aulas. Como tal, não deverá ser visto como uma solução canónica,  */
/** ou mesmo acabada. É disponibilizado para auxiliar o processo de estudo.      */
/** Os alunos são encorajados a testar adequadamente o código fornecido e a      */
/** procurar soluções alternativas, à medida que forem adquirindo mais           */
/** conhecimentos de POO.                                                        */
/*********************************************************************************/


import java.util.Comparator;
import java.util.Objects;

/**
 * A classe SmartDevice é um contactor simples.
 * Permite ligar ou desligar circuitos. 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SmartDevice implements Comparable<SmartDevice> {

    private String id;
    private boolean on;

    /**
     * Constructor for objects of class SmartDevice
     */
    public SmartDevice() {
        this.id = "";
        this.on = false;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.on = false;
    }

    public SmartDevice(String s, boolean b) {
        this.id = s;
        this.on = b;
    }

    public SmartDevice(SmartDevice that) {
        this.id = that.id;
        this.on = that.on;
    }

    public void turnOn() {
        this.on = true;
    }
    
    public void turnOff() {
        this.on = false;
    }
    
    public boolean getOn() {return this.on;}
    
    public void setOn(boolean b) { this.on = b;}
    
    public String getID() {return this.id;}

    public SmartDevice clone() {
        return new SmartDevice(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SmartDevice)) return false;
        SmartDevice that = (SmartDevice) o;
        System.out.println(on + "  " + that.on);
        System.out.println(id + "  " + that.id);
        return on == that.on && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, on);
    }

    @Override
    public int compareTo(SmartDevice o) {
        return this.id.length() - o.id.length();
    }
}
