package net.craftersland.crate.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class DataSerializer {
	
	public static String itemsToBase64(ItemStack[] items) {
    	try {  
    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            // Write the size
            dataOutput.writeInt(items.length);
            
            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }
            
            // Serialize that array
            dataOutput.close();
            String data = Base64Coder.encodeLines(outputStream.toByteArray());
            outputStream.close();
            return data;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to serialize data.", e);
        }
    }
	
	public static ItemStack[] itemsFromBase64(String data) {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            
            int lenght = dataInput.readInt();
            List<ItemStack> rawList = new ArrayList<ItemStack>();
    
            // Read the serialized
            for (int i = 0; i < lenght; i++) {
            	rawList.add((ItemStack) dataInput.readObject());
            }
            ItemStack[] items = rawList.toArray(new ItemStack[lenght]);
            
            dataInput.close();
            inputStream.close();
            return items;
        } catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }

}
