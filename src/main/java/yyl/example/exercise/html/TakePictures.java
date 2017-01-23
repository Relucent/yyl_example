package yyl.example.exercise.html;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

public class TakePictures {

	public TakePictures(String url) throws Exception {
		JFrame frame = new JFrame();
		frame.setLayout(null);

		JButton button = new JButton();
		button.setBounds(160, 2, 100, 20);
		button.setText("生成图片");
		frame.add(button);

		final JTextField text = new JTextField();
		text.setBounds(3, 2, 150, 20);
		text.setText("D:/file.jpg");
		frame.add(text);

		final JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);
		editorPane.setPage(url);

		editorPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					JEditorPane pane = (JEditorPane) e.getSource();
					if (e instanceof HTMLFrameHyperlinkEvent) {
						HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
						HTMLDocument doc = (HTMLDocument) pane.getDocument();
						doc.processHTMLFrameHyperlinkEvent(evt);
					} else {
						try {
							pane.setPage(e.getURL());
						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(editorPane);
		frame.add(scrollPane);
		scrollPane.setBounds(0, 25, 793, 562);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//Thread.sleep(5 * 1000);//延时，等待下载图片
		initBounds(frame);
		frame.setVisible(true);
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				BufferedImage image = new BufferedImage(editorPane.getWidth(), editorPane.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics2D = image.createGraphics();
				editorPane.paint(graphics2D);
				BufferedImage image1 = resize(image);
				try {
					ImageIO.write(image1, "jpg", new File(text.getText()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initBounds(JFrame frame) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int fw = 800;
		int fh = 600;
		int fx = (screenSize.width - fw) / 2;
		int fy = (screenSize.height - fh) / 2;
		frame.setBounds(fx, fy, fw, fh);
	}

	public static BufferedImage resize(BufferedImage source) {
		int type = source.getType();
		BufferedImage target = null;
		int width = source.getWidth();
		int height = source.getHeight();

		if (type == BufferedImage.TYPE_CUSTOM) {//自定义图片类型
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			target = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
		} else
			target = new BufferedImage(width, height, type);
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(1.0, 1.0));//缩放
		g.dispose();
		return target;
	}

	public static void main(String[] args) throws Exception {
		new TakePictures("http://www.google.com");
	}
}
