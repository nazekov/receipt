package v3.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import v3.IInventory;
import v3.IStock;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class Stock implements IStock {

    List<IInventory> inventoryItemList;

    @Override
    public List<IInventory> getInventoryItemList() {
        return inventoryItemList;
    }

    @Override
    public void addInventory(IInventory item) {
        inventoryItemList.add(item);
    }

    @Override
    public IInventory getInventory(int id) {
        return inventoryItemList.stream()
                .filter(inventory -> inventory.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void showAllInfo() {
        System.out.println("\nВсе товары: ");
        inventoryItemList.forEach(IInventory::showInfo);
    }
}
