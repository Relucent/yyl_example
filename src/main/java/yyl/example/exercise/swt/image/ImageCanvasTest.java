package yyl.example.exercise.swt.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class ImageCanvasTest {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		ImageViewer ic = new ImageViewer(shell);

		shell.setLayout(new FillLayout());
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("Open an image file or cancel");
		String string = dialog.open();

		ImageLoader loader = new ImageLoader();
		ImageData[] imageDatas = loader.load(string);
		if (imageDatas.length == 0)
			return;
		else if (imageDatas.length == 1) {
			ic.setImage(imageDatas[0]);
		} else {
			ic.setImages(imageDatas, loader.repeatCount);
		}

		ic.pack();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}