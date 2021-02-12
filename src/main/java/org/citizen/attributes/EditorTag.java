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
    public static float DOUBLE_BREAK_LINES = 0.64f;
    public static float THREE_BREAK_LINES = 0.96f;
    public static float FOUR_BREAK_LINES = 1.28f;
    public static float FIVE_BREAK_LINES = 1.6f;

    private final Citizen citizen;
    private List<CitizenTag> tags = new ArrayList<>();

    @Nullable
    public List<CitizenTag> getLines() {
        return tags;
    }

    public CitizenTag getLine(final int index) {
        return tags.get(index == 0 ? 0 : (index -1));
    }

    public void putLine(final String nameTag, float separator) {
        CitizenTag tag = new CitizenTag(citizen);
        tag.setNameTag(nameTag);

        if (size() == 0) {
            tag.setPosition(citizen.getPosition().add(0, 1.8f, 0));
        } else {
            tag.setPosition(tags.get(size() -1).getPosition().add(0, separator, 0));
        }

        tags.add(tag);
    }

    public void putLine(final String nameTag) {
        putLine(nameTag, ONE_BREAK_LINE);
    }

    public int size() {
        return tags.size();
    }
}
