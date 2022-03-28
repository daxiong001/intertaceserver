package testsuits;

import com.swxy.common.CaseMeta;
import com.swxy.utils.CommonUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestDemo {


    @Test(dataProvider = "testData")
    @CaseMeta("测试")
    public void queryWaybillByIdTest(String inputJson){
        System.out.println(inputJson);
    }

    @Test(dataProvider = "testData")
    @CaseMeta("测试")
    public void queryOrderListPageTest(String inputJson){
        System.out.println(inputJson);
    }

    @DataProvider
    public Object[][] testData(Method method){
        return CommonUtils.getTestNGData("OrderTest",method);
    }


}
