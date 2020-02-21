package yyl.example.demo.mockito;

import org.mockito.Mockito;

public class MockitoExample {

	public static void main(String[] args) {
		Sample sample = Mockito.mock(Sample.class);
		Mockito.when(sample.get(Mockito.anyInt())).thenReturn("mock");
		Mockito.when(sample.get(0)).thenReturn("hello");
		Mockito.when(sample.get(1)).thenReturn("welcome");
		Mockito.when(sample.get(2)).thenReturn("hi");
		Mockito.when(sample.get(4)).thenReturn(null);
		for (int i = 0; i < 5; i++) {
			System.out.println(i + "->" + sample.get(i));
		}
	}

	public static class Sample {
		public String get(Integer value) {
			throw new UnsupportedOperationException();
		}
	}
}
