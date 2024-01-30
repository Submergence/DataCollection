import com.DataCollection.Service.Impl.BuildContainersImpl;
import com.DataCollection.Utils.MysqlConfigReader;
import com.DataCollection.Web.Servlet.BuildContainerServlet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        BuildContainersImpl buildContainers = new BuildContainersImpl();
        System.out.println(buildContainers.queryVncIdByRecordId(14));
    }
}