package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class Calculate_Bill extends JFrame  implements ActionListener {
    Choice meterNumChoice, monthCHoice;
    JLabel nameText, addressText;
    TextField unitText;
    JButton submit,cancel;
    Calculate_Bill(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(214,195,247));
        add(panel);

        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(70,10,300,20);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        panel.add(heading);

        JLabel meternum = new JLabel("Meter Number");
        meternum.setBounds(50,80,100,20);
        panel.add(meternum);

        meterNumChoice = new Choice();
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
            while (resultSet.next()){
                meterNumChoice.add(resultSet.getString("meter_no"));
            }
        }catch (Exception E){
            E.printStackTrace();
        }
        meterNumChoice.setBounds(180,80,100,20);
        panel.add(meterNumChoice);

        JLabel name = new JLabel("Name");
        name.setBounds(50,120,100,20);
        panel.add(name);

        nameText = new JLabel("");
        nameText.setBounds(180,120,100,20);
        panel.add(nameText);

        JLabel address = new JLabel("Address");
        address.setBounds(50,160,100,20);
        panel.add(address);

        addressText = new JLabel("");
        addressText.setBounds(180,160,100,20);
        panel.add(addressText);

        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer where meter_no='"+meterNumChoice.getSelectedItem()+"' ");
            while (resultSet.next()){
                nameText.setText(resultSet.getString("name"));
                addressText.setText(resultSet.getString("address"));
            }
        }catch (Exception E){
            E.printStackTrace();
        }

        meterNumChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    database c = new database();
                    ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer where meter_no='"+meterNumChoice.getSelectedItem()+"' ");
                    while (resultSet.next()){
                        nameText.setText(resultSet.getString("name"));
                        addressText.setText(resultSet.getString("address"));
                    }
                }catch (Exception E){
                    E.printStackTrace();
                }
            }
        });

        JLabel unit = new JLabel("Unit Consumed");
        unit.setBounds(50,200,100,20);
        panel.add(unit);

        unitText = new TextField();
        unitText.setBounds(180,200,150,20);
        panel.add(unitText);

        JLabel month = new JLabel("Month");
        month.setBounds(50,240,100,20);
        panel.add(month);

        monthCHoice = new Choice();
        monthCHoice.add("January");
        monthCHoice.add("February");
        monthCHoice.add("March");
        monthCHoice.add("April");
        monthCHoice.add("May");
        monthCHoice.add("June");
        monthCHoice.add("July");
        monthCHoice.add("August");
        monthCHoice.add("September");
        monthCHoice.add("October");
        monthCHoice.add("November");
        monthCHoice.add("December");
        monthCHoice.setBounds(180,240,150,20);
        panel.add(monthCHoice);

        submit = new JButton("Submit");
        submit.setBounds(80,300,100,25);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        panel.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(220,300,100,25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);
        panel.add(cancel);

        setLayout(new BorderLayout());
        add(panel,"Center");
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/budget.png"));
        Image image = imageIcon.getImage().getScaledInstance(250,200,Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon1);
        add(imageLabel,"East");


        setSize(650,400);
        setLocation(400,200);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){
            String smeterno = meterNumChoice.getSelectedItem();
            String sunit = unitText.getText();
            String smonth = monthCHoice.getSelectedItem();
            int totalBill = 0;
            int units = Integer.parseInt(sunit);
            String quer_tax = "select * from Tax";
            try {
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(quer_tax);
                while(resultSet.next()){
                    totalBill += units * Integer.parseInt(resultSet.getString("Cost_per_unit"));
                    totalBill += Integer.parseInt(resultSet.getString("Meter_rent"));
                    totalBill += Integer.parseInt(resultSet.getString("service_Charge"));
                    totalBill += Integer.parseInt(resultSet.getString("Swacch_Bharat"));
                    totalBill += Integer.parseInt(resultSet.getString("Fixed_tax"));
                }

            }catch (Exception E){
                E.printStackTrace();
            }
            String query_total_bill = "insert into Bill values('"+smeterno+"', '"+smonth+"', '"+sunit+"', '"+totalBill+"','Not Paid') ";
            try {
                database c = new database();
                c.statement.executeUpdate(query_total_bill);

                JOptionPane.showMessageDialog(null,"Customer Bill Updated Successfuly");
                setVisible(false);
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Calculate_Bill();
    }
}
