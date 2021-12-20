package vn.edu.hcmus.student.sv19127633.review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    ToChucDulieu map=new ToChucDulieu("slang.txt",true);

    Container pane;
    //Search components
    JTextField input;
    JRadioButton bySlang;
    JRadioButton byDef;

    public GUI() throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        pane=this.getContentPane();
        pane.setLayout(new BorderLayout());

        createSearchFrame();
        this.pack();
    }

    public void createSearchFrame()
    {
        JPanel pageStart=new JPanel();
        pageStart.setLayout(new GridLayout(2,1));
        pane.add(pageStart,BorderLayout.PAGE_START);

        JPanel searchFrame=new JPanel();
        searchFrame.setLayout(new BoxLayout(searchFrame,BoxLayout.X_AXIS));
        pageStart.add(searchFrame);

        input=new JTextField();
        input.setPreferredSize(new Dimension(150,20));
        searchFrame.add(input);
        JButton apply=new JButton("Tìm kiếm");
        apply.addActionListener(this::actionPerformed);
        apply.setActionCommand("search");
        apply.setPreferredSize(new Dimension(100,20));
        searchFrame.add(apply);

        JPanel rdFrame=new JPanel();
        rdFrame.setLayout(new BoxLayout(rdFrame,BoxLayout.X_AXIS));
        pageStart.add(rdFrame);


        bySlang=new JRadioButton("theo Slang");
        rdFrame.add(bySlang);
        byDef=new JRadioButton("theo Keyword");
        rdFrame.add(byDef);

        ButtonGroup rdGroup=new ButtonGroup();
        rdGroup.add(bySlang);
        rdGroup.add(byDef);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("search"))
        {
            if (bySlang.isSelected())
                System.out.println(map.findDef(input.getText()));
            else
                System.out.println(map.findSlang(input.getText()));
        }
    }
}
