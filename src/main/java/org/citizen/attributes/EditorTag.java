package org.citizen.attributes;

import com.sun.istack.internal.Nullable;
import lombok.RequiredArgsConstructor;
import org.citizen.entity.Citizen;
import org.citizen.entity.CitizenTag;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EditorTag {
    public static float ONE_BREAK_LINE = 0.32f;

    private final Citizen citizen;
    private List<CitizenTag> tags = new ArrayList<>();

    @Nullable
    public List<CitizenTag> getLines() {
        return tags;
    }

    public CitizenTag getLine(final int index) {
        return tags.get(index == 0 ? 0 : (index -1));
    }

    public void putLine(final String nameTag, int separator) {
        CitizenTag tag = new CitizenTag(citizen);
        tag.setNameTag(nameTag);

        if (size() == 0) {
            tag.setPosition(citizen.getPosition().add(0, 1.8f, 0));
        } else {
            tag.setPosition(tags.get(size() -1).getPosition().add(0, (ONE_BREAK_LINE * separator), 0));
        }

        tags.add(tag);
    }

    public void putLine(final String nameTag) {
        putLine(nameTag, 1);
    }

    public int size() {
        return tags.size();
    }
}
