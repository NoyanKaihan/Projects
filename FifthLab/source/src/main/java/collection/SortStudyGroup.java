package collection;

import modules.StudyGroup;

import java.util.Comparator;

public class SortStudyGroup implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup o1, StudyGroup o2) {
        if (o1.getId() > o2.getId())
            return 1;
        else if (o1.getId() < o2.getId())
            return -1;
        return 0;
    }
}
