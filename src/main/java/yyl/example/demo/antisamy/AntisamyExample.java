package yyl.example.demo.antisamy;

import java.io.InputStream;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

/**
 * _Antisamy是OWASP（open web application security project）的一个开源项目，其能够对用户输入的html/css/javascript 脚本进行过滤，确保输入满足规范，无法提交恶意脚本。<br>
 * Antisamy被应用在web服务中对存储型和反射性的xss防御，尤其是在存在富文本输入的场景，antisamy能够很好的解决用户输入体验和安全要求之间的冲突。<br>
 */
public class AntisamyExample {
    public static void main(String[] args) throws Exception {
        AntiSamy as = new AntiSamy();
        try (InputStream input = AntisamyExample.class.getResourceAsStream("./antisamy-slashdot.xml")) {
            Policy policy = Policy.getInstance(input);
            CleanResults cr = as.scan("<div>hello<script>alert('hello');</script></div>", policy);
            System.out.print(cr.getCleanHTML());
        }
    }
}
