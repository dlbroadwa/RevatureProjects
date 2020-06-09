package company.apps;

import java.util.List;
import java.util.Optional;

public class InventoryDAO implements DAO<Item, Integer>{
      @Override
    public boolean save(Item obj) {
        return false;
    }

    @Override
    public List<Item> retrieveAll() {
        return null;
    }

    @Override
    public Item retrieveByID(Integer integer) {
        return null;
    }

    @Override
    public boolean delete(Item obj) {
        return false;
    }

    @Override
    public boolean update(Item newObj) {
        return false;
    }
}
