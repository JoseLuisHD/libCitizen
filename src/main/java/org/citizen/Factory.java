package org.citizen;

import com.sun.istack.internal.Nullable;
import lombok.NonNull;
import org.citizen.entity.Citizen;

import java.util.HashMap;
import java.util.Map;

public class Factory {
    private final Map<Long, Citizen> citizens = new HashMap<>();

    public Map<Long, Citizen> getCitizens() {
        return citizens;
    }

    public void add(@NonNull Citizen citizen) {
        citizens.put(citizen.getEntityId(), citizen);
    }

    public void remove(long id) {
        citizens.remove(id);
    }

    @Nullable
    public Citizen getCitizen(long id) {
        return citizens.get(id);
    }
}
