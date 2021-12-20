package vn.edu.hcmus.student.sv19127633.review;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener {
    ToChucDulieu map=new ToChucDulieu("slang.txt",true);

    Container pane;

    //Search components
    JTextField input;
    JRadioButton bySlang;
    JRadioButton byDef;

    //Main Components
    JPanel mainFrame;

    String[] columnNames = {"Slang", "Nghĩa"};
    String [][] data={{"Empty","Empty"}};
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table=new JTable();

    public GUI() throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        pane=this.getContentPane();
        pane.setLayout(new BorderLayout());

        createSearchFrame();
        createMainFrame();
        this.pack();
    }

    public void createSearchFrame()
    {
        JPanel pageStart=new JPanel();
        pageStart.setLayout(new GridLayout(2,1));
        pageStart.setBorder(new EmptyBorder(5,5,5,5));
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

    public void createMainFrame()
    {
        mainFrame=new JPanel();
        mainFrame.setLayout(new CardLayout());


        JPanel searchCard=new JPanel();
        searchCard.setBorder(new EmptyBorder(20,20,20,20));
        searchCard.setLayout(new BoxLayout(searchCard,BoxLayout.Y_AXIS));

        JLabel result=new JLabel("Kết quả");
        result.setBorder(new EmptyBorder(0,0,10,0));
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchCard.add(result);

        table=new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        searchCard.add(table);

        mainFrame.add(searchCard);
        pane.add(mainFrame,BorderLayout.CENTER);


    }

    public void clearTable()
    {
        int totalRow=table.getRowCount();
        for(int i=0;i<totalRow;i++)
            model.removeRow(i);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("search"))
        {
            if (bySlang.isSelected())
            {
                String slang=input.getText();
                if (slang.equals(""))
                    return;
                clearTable();
                String def=map.findDef(slang);
                if (def !=null)
                    model.addRow(new String[]{slang,def});
                else
                    model.addRow(new String[]{"Empty","Empty"});
            }

            else {
                String def=input.getText();
                if (def.equals(""))
                    return;
                clearTable();

                ArrayList<String> slangs=map.findSlang(def);

                int totalRow=slangs.size();

                if (totalRow>0) {
                    for (int i=0;i<totalRow;i++) {
                        String slang=slangs.get(i);
                        model.addRow(new String[]{slang,map.findDef(slang)});
                    }
                }
                else
                    model.addRow(new String[]{"Empty","Empty"});
            }
        }
    }
}
