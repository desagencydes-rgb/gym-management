package gym.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FileRepository<T extends Serializable> implements Repository<T> {
    protected List<T> items;
    private final String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
        this.items = new ArrayList<>();
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                items = (List<T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                // If load fails, start with empty list.
                items = new ArrayList<>();
            }
        }
    }

    protected void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(T item) {
        items.add(item);
        save();
    }

    @Override
    public void delete(String id) {
        items.removeIf(item -> getId(item).equals(id));
        save();
    }

    @Override
    public T findById(String id) {
        return items.stream()
                .filter(item -> getId(item).equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(items); // Return copy
    }

    @Override
    public void update(T item) {
        // Default update: remove old (by id) and add new.
        // Subclasses can override if needed, but this works for simple objects.
        delete(getId(item));
        add(item);
    }

    // Abstract method to extract ID from local generic type
    protected abstract String getId(T item);
}
