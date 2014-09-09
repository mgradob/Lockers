package lockers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author luishoracio
 */
public class Utils {
    public static void msgbox(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
    
    public static JSONObject getJSONObjectFromURL(String sURL){
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            JSONParser jp = new JSONParser(); 
            
            InputStream in = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            
            System.out.println(out.toString());
            
            JSONObject root = (JSONObject) jp.parse(out.toString());
            return root;
            
        } catch (java.net.ConnectException ex){
            Logger.getLogger(FrameCobro.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FileNotFoundException ex){
            JSONObject obj = new JSONObject();
            obj.put("Error", "NOT FOUND");
            return obj;
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(FrameCobro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(FrameCobro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONObject();
    }
    
    public static JSONArray getJSONArrayFromURL(String sURL){
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            JSONParser jp = new JSONParser(); 
            
            InputStream in = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            
            System.out.println(out.toString());
            
            Object root = jp.parse(out.toString());
            JSONArray array = (JSONArray)root;
            return array;
            
        } catch (java.net.ConnectException ex){
            Logger.getLogger(FrameCobro.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(FrameCobro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(FrameCobro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new JSONArray();
    }
    
}
