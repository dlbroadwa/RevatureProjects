package auction.dataaccess;
import java.util.List;

public interface DAO <T, ID> {
    /***
     * Saves the current object to persistent storage.
     * @param obj the object to save
     * @return <code>true</code> on success, <code>false</code> otherwise.
     */
    boolean save(T obj);

    /***
     * This method returns the all the objects of type T from persistent storage.
     * @return a list of objects
     */
    List<T> retrieveAll();

    /***
     * This method retrieves an object of type T from persistent storage identified by the value passed which is of type ID.
     * @param id the primary key of the object being retrieved
     * @return the object with that ID, or <code>null</code> if no such object was found.
     */
    T retrieveByID(ID id);

    /***
     * This method deletes all matching objects in persistent storage.
     * @param obj the object to be deleted.
     * @return <code>true</code> on success, <code>false</code> otherwise.
     */
    boolean delete(T obj);

    /***
     * This method updates the given object in persistent storage.
     * @param newObj the new object data to store.
     * @return <code>true</code> on success, <code>false</code> otherwise.
     */
    boolean update(T newObj);

}
