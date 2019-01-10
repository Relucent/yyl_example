package yyl.example.demo.mock;

import java.util.List;

import org.mockito.Mockito;


/**
 * 在实际项目中写单元测试的过程中我们会发现需要测试的类有很多依赖，这些依赖项又会有依赖，导致在单元测试代码里几乎无法完成构建，尤其是当依赖项尚未构建完成时会导致单元测试无法进行。<br>
 * 为了解决这类问题我们引入了Mock的概念，简单的说就是模拟这些需要构建的类或者资源，提供给需要测试的对象使用。<br>
 */
public class HelloMock {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<String> list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(5);
        Mockito.when(list.get(Mockito.anyInt())).thenReturn("other");
        Mockito.when(list.get(0)).thenReturn("zero");
        Mockito.when(list.get(1)).thenReturn("one");
        Mockito.when(list.get(2)).thenReturn("two");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
