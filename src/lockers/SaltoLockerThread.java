/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author luishoracio
 */
public class SaltoLockerThread extends SwingWorker <Void,String> {
    
    private BufferedReader in;
    private PrintWriter out;
    private boolean isConnected;
    private Logger log = Logger.getLogger(PaymentThread.class.getName());
    
    public SaltoLockerThread(Socket socket){
        isConnected = true;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error al crear los stream de entrada y salida : {0}", ex.getMessage());
        }
    }
    public void sendData(String str){
        out.print(str);
        out.flush();
    }
    
    public void disconnect(boolean disconnect){
        isConnected = disconnect;
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        String texto;
        out.print(SaltoLocker.openDoorString());
        out.flush();
        while (isConnected) {
            if (in.ready()) {
                
                char[] inputChars = new char[1024];
                in.read(inputChars);
                texto = String.copyValueOf(inputChars);
                publish(texto);
                
                System.out.println(texto);
            }
            texto = "hola\n";
            publish(texto);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
}
