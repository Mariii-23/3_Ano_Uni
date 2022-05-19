
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

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos 
 * espaços (as salas) que existem na casa.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CasaInteligente {
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

    /**
     * Constructor for objects of class CasaInteligente
     */
    public CasaInteligente() {
        // initialise instance variables
        this.morada = "";
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    public CasaInteligente(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    public CasaInteligente(Collection<SmartDevice> devices) {
        this.morada = "";
        this.devices =  new HashMap<>();
        devices.forEach(e -> this.devices.put(e.getID(), e.clone()));
        //this.devices = devices.stream().collect(groupingBy(SmartDevice::getID,HashMap::new, mapping(SmartDevice::getID, toSet())));
        this.locations = new HashMap();
    }

    public Consumer<SmartBulb> consumer = e -> e.setTone((int) (e.getTone()*0.25));

    public void alteraInfo(Consumer<SmartBulb> bd){
        this.devices.values().stream().filter(e -> e instanceof SmartBulb).forEach(elem -> bd.accept((SmartBulb) elem));
    }

    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }
    
    public boolean existsDevice(String id) {
        return this.devices.containsKey(id);
    }
    
    public void addDevice(SmartDevice s) {
        this.devices.put(s.getID(), s.clone());
    }
    
    public SmartDevice getDevice(String s) {return this.devices.get(s);}
    
    public void setOn(String s, boolean b) {
        var device = this.devices.get(s);
        if (device != null) {
            device.setOn(b);
        }
    }
    
    public void setAllOn(boolean b) {
        this.devices.values().forEach(e-> e.setOn(b));
    }
    
    public void addRoom(String s) {
        this.locations.putIfAbsent(s, new ArrayList<>());
    }
    
    public boolean hasRoom(String s) {return this.locations.containsKey(s);}
    
    public void addToRoom (String s1, String s2) {
        var room = this.locations.get(s1);
        if (room == null) {
            List<String> list = new ArrayList<>();
            list.add(s2);
            this.locations.put(s1, list);
        } else {
            room.add(s2);
        }
    }
    
    public boolean roomHasDevice (String s1, String s2) {return hasRoom(s1) && this.locations.get(s1).contains(s2);}

    public class DeviceNotFound extends Exception {
        public DeviceNotFound() {}
        public DeviceNotFound(String message) {
            super(message);
        }
    }

    public void remove(String id) throws DeviceNotFound {
        if (this.devices.remove(id) == null)
            throw new DeviceNotFound();

        Iterator<Map.Entry<String, List<String>>> it = this.locations.entrySet().iterator();
        List<String> devices;
        boolean found = false;

        while( it.hasNext() && !found) {
            devices = it.next().getValue();
            found = devices.remove(id);
        }
    }

    public Iterator<SmartDevice> devicesPorConsumoCrescente() {
        Set<SmartDevice> aux = new HashSet<>(this.devices.values());
        return aux.iterator();
    }

    //TODO tou fucking confusa
    public String divisaoMaisEconomica() {
        return "welelel"   ;
    }

    //TODO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasaInteligente that = (CasaInteligente) o;

        // see if all devices are equal
        boolean devices = true;
        for (var dev : this.devices.values()) {
            if (devices) {
                var elem = that.devices.get(dev.getID());
                devices = elem != null && elem.equals(dev);
            } else {
                return false;
            }
        }

        // see if all locations are equal
        boolean locations = true;
        for (var locales : this.locations.entrySet()) {
            if (locations) {
                var name = locales.getKey();
                var that_local = that.locations.get(name);
                locations = that_local != null && that_local.containsAll(locales.getValue());
            } else {
                return false;
            }
        }

        return morada.equals(that.morada) && devices && locations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(morada, devices, locations);
    }
}
