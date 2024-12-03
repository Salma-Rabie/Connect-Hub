/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connect.hub;

import connect.hub.Backend.PasswordHashing;


public class ConnectHub {

   
    public static void main(String[] args) {
        // TODO code application logic here
        String pass="abc123";
        PasswordHashing passhash=new PasswordHashing();
        String pass2=passhash.hashPassword(pass);
        System.out.println(pass2);
    }
    
}
