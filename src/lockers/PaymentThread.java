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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author luishoracio
 */
public class PaymentThread extends SwingWorker<Void, String> {

    private JTextArea textArea;
    private JLabel lblAmount;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isConnected;
    private Logger log = Logger.getLogger(getClass().getName());
    private Timer timer;

    public PaymentThread(Socket socket, JTextArea text, JLabel labelAmount) {
        this.textArea = text;
        this.lblAmount = labelAmount;

        isConnected = true;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error al crear los stream de entrada y salida : {0}", ex.getMessage());
        }
    }

    public void setConectado(boolean set) {
        isConnected = set;
    }

    class PollTask extends TimerTask {

        public void run() {
            out.print("POLL");
            out.flush();
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new PollTask(), 2000, 2000);
    }

    @Override
    protected Void doInBackground() throws Exception {
        String texto;
        String str = SaltoLocker.payment(50);
        startTimer();
        out.println(str);
        out.flush();

        System.out.println(str);

        while (isConnected) {
            if (in.ready()) {
                char[] inputChars = new char[1024];
                in.read(inputChars);
                texto = String.copyValueOf(inputChars);
                publish(texto);
                if (texto.contains("OK")) {
                    out.print("DEPOSITO");
                    out.flush();
                    continue;
                }
                if (texto.contains("DEPOSITO")) {
                    publish(texto);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("fin");

        timer.cancel();
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        String text = chunks.get(0);
        if (text.contains("DEPOSITOF")){
            
        }
        if (text.contains("DEPOSITO")){
            String[] split = text.split(",");
            this.lblAmount.setText(split[1]);
        }else{
            textArea.append(chunks.get(0));
        }
        
    }
}
