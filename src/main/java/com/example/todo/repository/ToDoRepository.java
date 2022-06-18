package com.example.todo.repository;

import com.example.todo.model.ToDo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ToDoRepository implements CommonRepository<ToDo> {

    private final Map<String, ToDo> toDos = new HashMap<>();

    @Override
    public ToDo save(ToDo model) {
        ToDo result = toDos.get(model.getId());

        if (result != null) {
            result.setModified(LocalDateTime.now());
            result.setDescription(model.getDescription());
            result.setCompleted(model.isCompleted());
            model = result;
        }
        toDos.put(model.getId(), model);
        return toDos.get(model.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> model) {
        model.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo model) {
        toDos.remove(model.getId());
    }

    @Override
    public ToDo findById(String id) {
        return toDos.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return toDos.entrySet()
                .stream()
                .sorted(entryComparator)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private final Comparator<Map.Entry<String, ToDo>> entryComparator =
            Comparator.comparing(
                    (Map.Entry<String, ToDo> o) -> o.getValue().getCreated());
}
