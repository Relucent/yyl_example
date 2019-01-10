package yyl.example.basic.awt.layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 网格包布局<br>
 * 网格包布局是一个十分灵活的布局管理器，它可以将组件垂直、水平或沿它们的基线对齐。(很类似HTML中的TABLE)<br>
 * 每个 GridBagLayout 对象维持一个动态的矩形单元网格，每个组件占用一个或多个这样的单元，该单元被称为显示区域。 <br>
 * 每个由 GridBagLayout 管理的组件都与 GridBagConstraints 的实例相关联。<br>
 * GridBagConstraints 对象指定组件的显示区域在网格中的具体放置位置，以及组件在其显示区域中的放置方式。<br>
 * 包含以下属性：<br>
 * 1. GridBagConstraints.gridx、GridBagConstraints.gridy <br>
 * 组件显示区域的位置 。<br>
 * 2. GridBagConstraints.gridwidth、GridBagConstraints.gridheight <br>
 * 指定组件的显示区域中行或列中的单元数，默认值为 1。 (类似HTML TD元素的 ROWSPAN,CONSPAN属性)<br>
 * 3. GridBagConstraints.fill <br>
 * 当组件的显示区域大于组件的所需大小时，用于确定是否（以及如何）调整组件。可能的值为：<br>
 * (1) GridBagConstraints.NONE（默认值）<br>
 * (2) GridBagConstraints.HORIZONTAL（加宽组件直到它足以在水平方向上填满其显示区域，但不更改其高度）<br>
 * (3) GridBagConstraints.VERTICAL（加高组件直到它足以在垂直方向上填满其显示区域，但不更改其宽度）<br>
 * (4) GridBagConstraints.BOTH（使组件完全填满其显示区域）。<br>
 * 4. GridBagConstraints.ipadx、GridBagConstraints.ipady <br>
 * 指定布局中组件的内部填充，即对组件最小大小的添加量。组件的宽度至少为其最小宽度加上 ipadx 像素。类似地，组件的高度至少为其最小高度加上 ipady 像素。 <br>
 * 5. GridBagConstraints.insets <br>
 * 指定组件的外部填充，即组件与其显示区域边缘之间间距的最小量。 <br>
 * 6. GridBagConstraints.anchor <br>
 * 指定组件应置于其显示区域中何处。可能的值有三种：绝对值、相对于方向的值和相对于基线的值。 <br>
 * 7. GridBagConstraints.weightx、GridBagConstraints.weighty <br>
 * 用于确定分布空间的方式<br>
 */
public class GridBagLayoutExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("FlowLayout");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        container.setLayout(layout);

        cons.weightx = 1.0;

        // (0,0)
        JButton button1 = new JButton("Button1");
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        layout.setConstraints(button1, cons);
        container.add(button1);

        // (0,1)
        JButton button2 = new JButton("Button2");
        cons.gridy = 0;
        cons.gridx = 1;
        cons.fill = GridBagConstraints.BOTH;
        layout.setConstraints(button2, cons);
        container.add(button2);

        // (0,2)
        JButton button3 = new JButton("Button3");
        cons.gridy = 0;
        cons.gridx = 2;
        cons.fill = GridBagConstraints.BOTH;
        layout.setConstraints(button3, cons);
        container.add(button3);

        // (1,0)
        JButton button4 = new JButton("Button4");
        cons.gridy = 1;
        cons.gridx = 0;
        cons.fill = GridBagConstraints.BOTH;
        layout.setConstraints(button4, cons);
        container.add(button4);

        // (1,1) conspan=2
        JButton button5 = new JButton("Button5");
        cons.gridy = 1;
        cons.gridx = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.BOTH;
        layout.setConstraints(button5, cons);
        container.add(button5);

        // (2,0) conspan=3 fill
        JButton button6 = new JButton("Button5");
        cons.gridy = 2;
        cons.gridx = 0;
        cons.gridwidth = 3;
        cons.weighty = 1.0;
        cons.fill = GridBagConstraints.BOTH;
        layout.setConstraints(button6, cons);
        container.add(button6);


        frame.setVisible(true);
    }
}
