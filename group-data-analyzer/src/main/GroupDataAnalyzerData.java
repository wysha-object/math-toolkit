/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package main;

import data.AbstractWrittenData;
import math.GroupData;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wysha
 */
public class GroupDataAnalyzerData extends AbstractWrittenData {
    public static GroupDataAnalyzerData groupDataAnalyzerDate = new GroupDataAnalyzerData();
    final public List<GroupData> groupData = new ArrayList<>();

    protected GroupDataAnalyzerData() {
        super("GroupDataAnalyzerData");
        groupDataAnalyzerDate = this;
    }

    @Override
    public void read() throws Throwable {
        if (file.exists()) {
            groupDataAnalyzerDate = (GroupDataAnalyzerData) new ObjectInputStream(
                    Files.newInputStream(file.toPath())
            ).readObject();
        }
    }
}
