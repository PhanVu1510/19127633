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
    ToChucDulieu map=new ToChucDulieu();

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

    //addEditCard
    JTextField slang_field;
    JTextField def_field;
    JButton add_btn, edit_btn,del_btn;

    //funnyQuestCard
    JLabel question;
    JRadioButton ans1,ans2,ans3,ans4;
    String result="";
    ButtonGroup group;
    long start = 0;
    long end = 0;

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
        funcFrame.setLayout(new GridLayout(5,1));
        pane.add(funcFrame,BorderLayout.LINE_END);

        JButton search_func_btn=new JButton("Tìm kiếm");
        ///search_func_btn.setPreferredSize();
        search_func_btn.setActionCommand("search_func");
        search_func_btn.addActionListener(this::actionPerformed);
        funcFrame.add(search_func_btn);

        JButton add_func_btn=new JButton("Thêm/ Sửa/ Xóa");
        add_func_btn.setActionCommand("add_edit_del_func");
        add_func_btn.addActionListener(this::actionPerformed);
        funcFrame.add(add_func_btn);

        JButton reset_func_btn=new JButton("Reset");
        reset_func_btn.setActionCommand("reset");
        reset_func_btn.addActionListener(this::actionPerformed);
        funcFrame.add(reset_func_btn);

        JButton funny_quest_btn=new JButton("Hãy chọn def đúng");
        funny_quest_btn.setActionCommand("quest1_func");
        funny_quest_btn.addActionListener(this::actionPerformed);
        funcFrame.add(funny_quest_btn);

        JButton funny_quest2_btn=new JButton("Hãy chọn slang đúng");
        funny_quest2_btn.setActionCommand("quest2_func");
        funny_quest2_btn.addActionListener(this::actionPerformed);
        funcFrame.add(funny_quest2_btn);

    }

    public void createMainFrame()
    {
        mainFrame=new JPanel();
        mainFrame.setLayout(c);
        mainFrame.setBorder(new EmptyBorder(20,20,20,20));

        createSearchCard();
        createAddEditCard();
        createFunnyQuestionCard();
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

    public void createAddEditCard()
    {
        addCard=new JPanel();
        addCard.setBorder(new EmptyBorder(5,5,5,5));
        addCard.setLayout(new BoxLayout(addCard,BoxLayout.Y_AXIS));
        mainFrame.add(addCard,"add_edit_del");

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

        add_btn =new JButton("Thêm");
        add_btn.addActionListener(this::actionPerformed);
        add_btn.setActionCommand("add");
        add_new_frame.add((add_btn));

        edit_btn =new JButton("Sửa");
        edit_btn.addActionListener(this::actionPerformed);
        edit_btn.setActionCommand("edit");
        add_new_frame.add((edit_btn));

        del_btn =new JButton("Xóa");
        del_btn.addActionListener(this::actionPerformed);
        del_btn.setActionCommand("del");
        add_new_frame.add((del_btn));

    }

    public void createFunnyQuestionCard()
    {
        JPanel questCard=new JPanel();
        questCard.setLayout(new BoxLayout(questCard,BoxLayout.Y_AXIS));
        questCard.setBorder(new EmptyBorder(5,5,5,5));
        mainFrame.add(questCard,"quest");

        JPanel quest_frame=new JPanel();
        questCard.add(quest_frame);

        JPanel ans_frame=new JPanel();
        ans_frame.setLayout(new GridLayout(2,2));
        questCard.add(ans_frame);

        question = new JLabel();
        question.setBorder(new EmptyBorder(0,0,10,0));
        quest_frame.add(question);

        ans1=new JRadioButton();
        ans1.addActionListener(this::actionPerformed);
        ans1.setActionCommand("ans1");
        ans1.setBorder(new EmptyBorder(10,10,10,10));
        ans_frame.add(ans1);

        ans2=new JRadioButton();
        ans2.addActionListener(this::actionPerformed);
        ans2.setActionCommand("ans2");
        ans2.setBorder(new EmptyBorder(10,10,10,10));
        ans_frame.add(ans2);

        ans3=new JRadioButton();
        ans3.addActionListener(this::actionPerformed);
        ans3.setActionCommand("ans3");
        ans3.setBorder(new EmptyBorder(10,10,10,10));
        ans_frame.add(ans3);

        ans4=new JRadioButton();
        ans4.addActionListener(this::actionPerformed);
        ans4.setActionCommand("ans4");
        ans4.setBorder(new EmptyBorder(10,10,10,10));
        ans_frame.add(ans4);

        group=new ButtonGroup();
        group.add(ans1);
        group.add(ans2);
        group.add(ans3);
        group.add(ans4);

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

        else if (e.getActionCommand()=="add_edit_del_func")
        {
            c.show(mainFrame,"add_edit_del");
        }

        else if (e.getActionCommand()=="quest1_func" || e.getActionCommand()=="quest2_func")
        {
            group.clearSelection();
            c.show(mainFrame,"quest");
            ArrayList<String> strings=new ArrayList<>();

            String quest="";

            if (e.getActionCommand()=="quest1_func") {
                strings = map.funnyQuestion1();
                result=map.getMap().get(strings.get(4));
                quest=String.format("Đâu là nghĩa của slang %s ?",strings.get(4));
            }
            else if (e.getActionCommand()=="quest2_func") {
                strings = map.funnyQuestion2();
                result=map.getRevMap().get(strings.get(4));
                quest=String.format("Đâu là slang của def %s ?",strings.get(4));
            }


                start=System.currentTimeMillis();
                question.setText(quest);
                ans1.setText(strings.get(0));
                ans2.setText(strings.get(1));
                ans3.setText(strings.get(2));
                ans4.setText(strings.get(3));

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

            String res= null;
            try {
                res = map.addSlang(new_slang,new_def);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (res.equals("Duplicate"))
            {
                System.out.println("Duplicate");
                int res2 = JOptionPane.showConfirmDialog(null, "Từ vựng đã tồn tại\nBạn có muốn thêm vào vào dưới dạng duplicate không?\n'Yes' để duplicate, 'No' để ghi đè lên từ đã tồn tại", "Nhắc nhở",
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
                else if (res2==1){
                    try {
                        map.editSlang(new_slang,new_slang,new_def);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    String msg=String.format("Đã ghi đè vào %s thành công",new_slang);
                    JOptionPane.showMessageDialog(null,msg,"Thành công", JOptionPane.INFORMATION_MESSAGE);
                    slang_field.setText("");
                    def_field.setText("");
                    return;
                }
            }
            try {
                map.addSlang(new_slang,new_def);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String msg=String.format("Thêm %s thành công",new_slang);
            JOptionPane.showMessageDialog(null, msg,"Thành công", JOptionPane.INFORMATION_MESSAGE);
            slang_field.setText("");
            def_field.setText("");
        }

        else if (e.getActionCommand().equals("edit"))
        {
            String slang=slang_field.getText();
            String new_def= def_field.getText();

            if (slang.equals("") || new_def.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ thông tin","Nhắc nhở",JOptionPane.WARNING_MESSAGE);
                return;
            }
            Boolean res= null;
            try {
                res = map.editSlang(slang,slang,new_def);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (res) {
                String msg = String.format("Đã cập nhật %s thành công", slang);
                JOptionPane.showMessageDialog(null, msg, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                String msg = String.format("%s không tồn tại", slang);
                JOptionPane.showMessageDialog(null, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getActionCommand().equals("del"))
        {
            String slang=slang_field.getText();

            if (slang.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Vui lòng điền đầy đủ thông tin","Nhắc nhở",JOptionPane.WARNING_MESSAGE);
                return;
            }

            int res2 = JOptionPane.showConfirmDialog(null, "Bạn chắc chứ ?", "Nhắc nhở",
                    JOptionPane.YES_NO_OPTION);

            if(res2==0) {
                Boolean res = null;
                try {
                    res = map.deleteSlang(slang);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (res) {
                    String msg = String.format("Đã xóa %s thành công", slang);
                    JOptionPane.showMessageDialog(null, msg, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String msg = String.format("%s không tồn tại", slang);
                    JOptionPane.showMessageDialog(null, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        else if (e.getActionCommand().equals("reset")) {
            try {
                map.reset();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        else if (e.getActionCommand().equals("ans1"))
        {
            System.out.println(result);
            if(!ans1.getText().equals(result))
            {
                JOptionPane.showMessageDialog(null,
                        "Sai rỒi",
                        "Thật tiếc",
                        JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Đáp án chính xác","Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        else if (e.getActionCommand().equals("ans2"))
        {
            if(!ans2.getText().equals(result))
            {
                JOptionPane.showMessageDialog(null,
                        "Sai rỒi",
                        "Thật tiếc",
                        JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Đáp án chính xác","Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        else if (e.getActionCommand().equals("ans3"))
        {
            if(!ans3.getText().equals(result))
            {
                JOptionPane.showMessageDialog(null,
                        "Sai rỒi",
                        "Thật tiếc",
                        JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Đáp án chính xác","Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        else if (e.getActionCommand().equals("ans4"))
        {
            if(!ans4.getText().equals(result))
            {
                JOptionPane.showMessageDialog(null,
                        "Sai rỒi",
                        "Thật tiếc",
                        JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Đáp án chính xác","Chúc mừng", JOptionPane.INFORMATION_MESSAGE);
            }
        }




    }
}
