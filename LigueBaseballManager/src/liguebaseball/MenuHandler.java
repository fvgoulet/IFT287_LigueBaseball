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
public class MenuHandler 
{
    LigueBaseballDBManager DBManager;

    public MenuHandler(String[] args) 
    {
        DBManager = new LigueBaseballDBManager(args[0], args[1], args[2]);
    }
    
    void Start() throws Exception
    {
        
    }
    
    
}
