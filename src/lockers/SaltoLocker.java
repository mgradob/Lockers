/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockers;

/**
 *
 * @author luishoracio
 */
public class SaltoLocker {
    public static String payment(int payment){
        String str = "COBRA(" + payment + ")";
        return str;
    }
    public static String openDoorString() {
        String str = "STP/00/196/<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
        str += "<RequestCall>";
        str += "<RequestName>OnlineDoor.Open</RequestName>";
        str += "<Params>";
        str += "<DoorNameList>";
        str += "<DoorID>Lector Mural</DoorID>";
        str += "</DoorNameList>";
        str += "</Params>";
        str += "</RequestCall>";
        return str;
    }
}
