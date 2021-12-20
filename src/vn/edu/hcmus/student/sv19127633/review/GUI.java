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
    CardLayout c=new CardLayout();
    JPanel mainFrame;
    JPanel searchCard;
    JPanel addCard;

    //searchCard
    String[] columnNames = {"Slang", "Nghĩa"};
    String [][] data={{"Empty","Empty"}};
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table=new JTable();

    //addCard
    JTextField slang_field;
    JTextField def_field;
    JButton add_new_btn;

    public GUI() throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        pane=this.getContentPane();
        pane.setLayout(new BorderLayout());

        createSearchFrame();
        createMainFrame();
        createFuncFrame();
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

    public void createFuncFrame()
    {
        //Switch function Card
        JPanel funcFrame=new JPanel();
        funcFrame.setLayout(new GridLayout(2,1));
        pane.add(funcFrame,BorderLayout.LINE_END);

        JButton search_func_btn=new JButton("Tìm kiếm");
        ///search_func_btn.setPreferredSize();
        search_func_btn.setActionCommand("search_func");
        search_func_btn.addActionListener(this::actionPerformed);
        funcFrame.add(search_func_btn);

        JButton add_func_btn=new JButton("Thêm slang");
        add_func_btn.setActionCommand("add_func");
        add_func_btn.addActionListener(this::actionPerformed);
        funcFrame.add(add_func_btn);
    }

    public void createMainFrame()
    {
        mainFrame=new JPanel();
        mainFrame.setLayout(c);
        mainFrame.setBorder(new EmptyBorder(20,20,20,20));

        createSearchCard();
        createAddCard();
    }

    public void createSearchCard()
    {
        searchCard=new JPanel();
        searchCard.setBorder(new EmptyBorder(5,5,5,5));
        searchCard.setLayout(new BoxLayout(searchCard,BoxLayout.Y_AXIS));

        JLabel result=new JLabel("Kết quả");
        result.setBorder(new EmptyBorder(0,0,10,0));
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchCard.add(result);

        table=new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        searchCard.add(table);

        mainFrame.add(searchCard,"search");
        pane.add(mainFrame,BorderLayout.CENTER);
    }

    public void createAddCard()
    {
        addCard=new JPanel();
        addCard.setBorder(new EmptyBorder(5,5,5,5));
        addCard.setLayout(new BoxLayout(addCard,BoxLayout.Y_AXIS));
        mainFrame.add(addCard,"add");

        JPanel new_slang_frame=new JPanel();
        new_slang_frame.setLayout(new FlowLayout());
        addCard.add(new_slang_frame);

        JLabel slang_lb=new JLabel("Slang");
        new_slang_frame.add(slang_lb);
        slang_field=new JTextField();
        slang_field.setPreferredSize(new Dimension(100,20));
        new_slang_frame.add(slang_field);

        JPanel new_def_frame=new JPanel();
        new_def_frame.setLayout(new FlowLayout());
        addCard.add(new_def_frame);

        JLabel def_lb=new JLabel("Nghĩa");
        new_def_frame.add(def_lb);
        def_field=new JTextField();
        def_field.setPreferredSize(new Dimension(100,20));
        new_def_frame.add(def_field);

        JPanel add_new_frame=new JPanel();
        add_new_frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        addCard.add(add_new_frame);

        add_new_btn=new JButton("Xác nhận");
        add_new_btn.addActionListener(this::actionPerformed);
        add_new_btn.setActionCommand("add");
        add_new_frame.add((add_new_btn));

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
        else if (e.getActionCommand().equals("search_func"))
        {
            c.show(mainFrame,"search");
        }

        else if (e.getActionCommand()=="add_func")
        {
            c.show(mainFrame,"add");
            //map.addSlang()
        }

        else if(e.getActionCommand().equals("add"))
        {
            String new_slang=slang_field.getText();
            String new_def=def_field.getText();

            if (new_slang.equals("") || def_field.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Vui lòng điền đầy đủ các thông tin",
                        "Nhắc nhở",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String res=map.addSlang(new_slang,new_def);
            if (res.equals("Duplicate"))
            {
                System.out.println("Duplicate");
                int res2 = JOptionPane.showConfirmDialog(null, "Từ vựng đã tồn tại\nBạn có muốn thêm vào vào dưới dạng duplcate không ?", "Nhắc nhở",
                        JOptionPane.YES_NO_OPTION);
                if(res2==0)
                {
                    int i=1;
                    while (true)
                    {
                        if (!map.getMap().containsKey(new_slang+" ("+i+")")) {
                            new_slang=new_slang+" ("+i+")";
                            break;
                        }
                        i++;
                    }
                }
                else return;
            }
            map.addSlang(new_slang,new_def);
            String msg=String.format("Thêm %s thành công",new_slang);
            JOptionPane.showMessageDialog(null, msg,"Thành công", JOptionPane.INFORMATION_MESSAGE);
            slang_field.setText("");
            def_field.setText("");
        }
    }
}
