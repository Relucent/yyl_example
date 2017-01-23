package yyl.example.demo.jacob;



/**
 * World转换为HTML的测试类<br>
 * 用到了jacob包
 * (http://sourceforge.net/projects/jacob-project/?source=typ_redirect)<br>
 * 
 * @author 译朗
 * @version 2014-04-29
 */
public class Word2HtmlTest {


  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) {

    //打开一个WORD应用
    JacobWordEngine word = new JacobWordEngine(true);

    //如果处理多个文件，这里可以写成循环
    {
      // 打开文档
      word.openDocument("E:/测试文档.doc");

      // 另存为HTML
      word.saveAsHtml("E:/测试文档.html");

      // 关闭文档
      word.closeDocumentWithoutSave();
    }

    //最后关闭应用
    word.close();
  }
}
