package ru.nevsky_company.HuffmanZip;

import java.util.LinkedList;

class myDataList extends LinkedList {

    public Object clone() {
        myDataList cp = new myDataList();
        data mydata;

        for (int i = 0; i < size(); i++) {
            mydata = (data) get(i);
            cp.add(new data(mydata.getChar(), mydata.get_probability(), mydata.isFlag()));
        }
        return cp;
    }
}
