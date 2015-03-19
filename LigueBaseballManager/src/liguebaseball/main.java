/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liguebaseball;

/**
 *
 * @author fvgou_000
 */
public class main 
{

    private static MenuHandler menu;
    public static void main(String[] args) 
    {
        try
        {
            menu = new MenuHandler(args);
            menu.Start();
        }
        catch(Exception ex)
        {
            System.out.println("Unhandled exception occurred: " + "\r\n" + ex.toString());
        }
    }
    
}
