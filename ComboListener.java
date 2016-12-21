package photo_renamer;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The listener for the drop down menu.
 * This class implements the observer/DEM design pattern. 
 * Its role is to be an observer object.
 */
public class ComboListener implements ItemListener{
	/**The item selected in the JComboBax*/
	private static String comboitem;
    
	/**
	 * The action listener for the JComboBox. Sets comboitem to the item selected lest
	 * in the JComboBox.
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
	          Object item = event.getItem();
	          comboitem = item.toString();
		}
		
	}   
	/**
	 * returns the item last selected in the JComboBox.
	 * @return selected item from the JComboBox.
	 */
	public static String getComboItem(){
		return comboitem;
	}
}
