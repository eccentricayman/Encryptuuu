/* Equips is a java class that makes */

import java.util.ArrayList;

public class Equips {
    
    public static final String[] equip_desc = {"Savage", "Smart","Forntunate","Fast"}
    public static final String[] equip_type = {"Power", "Intelligence", "Luck", "Speed"};
    public static final String[] typeList = {"Helm", "Armor", "Boots", "Amulet", "Ring"};
    private int[] effects = new int[4];
    private String name;
    private String type;
    private int strength = 0;
    private int intelligence = 0;
    private int dexterity = 0;
    private int luck = 0;

    public Equip(int level) {
    	type = typeList[(int)(Math.random(4))]
    	Int num = (int)(Math.random(3))
    	
    	if (num == 0) {
    		strength += 2*level;
    	}
    	if (num == 1) {
    		intelligence += 2 * level;
    	}
    	if (num == 2) { 
    		luck += 2 * level;
    	}
    	else dexterity += 2 * level;
    	name = equip_desc[num] + " " + type + " of ";
    	num = (int)(Math.random(3));
    	if (num == 0) {
    		strength += 2 * level;
    	}
    	if (num == 1) {
    		intelligence += 2 * level;
    	}
    	if (num == 2) {
			luck += 2 * level;
    	}
    	else  {
    		dexterity += 2 * level;
    	}
    	name += equip_type[num];
/*get rid of dis and comment stuff please
        name =  + " " + type + " of " + equip_type[(int)(Math.random(3))];
        this = level;
        if 
        */
    }
   
}