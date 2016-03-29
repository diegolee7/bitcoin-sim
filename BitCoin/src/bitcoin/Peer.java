/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoin;

import java.net.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;
import java.util.Arrays;
/**
 *
 * @author diego
 */
public class Peer {
    
    private KeyHolder keyHolder;
    private VerifySignature verifySignature;
    
    public Peer(){
        System.out.println("Peer Constructor");
        keyHolder = new KeyHolder();
        verifySignature = new VerifySignature();
    }
    
    public void test_signature(){
        keyHolder.signFile("test_file.txt");
        verifySignature.verify("publicKey", "sig", "test_file.txt");
        verifySignature.verify("publicKey", "sig", "test_file2.txt");
                
    }
    
    public void start() {
        
        // args give message contents and destination multicast group (e.g.
        // "228.5.6.7")
        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);
            String string = "Hello";
            byte[] m = string.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
            s.send(messageOut);

            byte[] buffer = new byte[1000];
            for (int i = 0; i < 4; i++) { // get messages from others in group
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                System.out.println("Received:" + new String(messageIn.getData()));
            }
            s.leaveGroup(group);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (s != null)
                s.close();
        }
    }    
}