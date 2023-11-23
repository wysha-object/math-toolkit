/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package GroupDataAnalyzerMain;

import date.WrittenDate;
import GroupDataAnalyzerMath.GroupDate;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GroupDataAnalyzerDate extends WrittenDate {
    public List<GroupDate> groupDates=new ArrayList<>();
    public static GroupDataAnalyzerDate groupDataAnalyzerDate=new GroupDataAnalyzerDate();
    protected GroupDataAnalyzerDate() {super("GroupDataAnalyzerDate");groupDataAnalyzerDate=this;}

    public void read() throws Throwable {
        if (file.exists()){
            groupDataAnalyzerDate= (GroupDataAnalyzerDate) new ObjectInputStream(
                    Files.newInputStream(file.toPath())
            ).readObject();
        }
    }
}
