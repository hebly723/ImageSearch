package com.graduate.entity;

import java.util.Comparator;

public class DVComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        DV dv1 = (DV)o1;
        DV dv2 = (DV)o2;
        double diff = dv1.getD() - dv2.getD();
        if (diff > 0)
            return 1;
        if (diff < 0)
            return -1;
        else
            return 0;
    }
}
