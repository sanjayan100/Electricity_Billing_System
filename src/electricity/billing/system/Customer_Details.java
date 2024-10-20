package electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Customer_Details extends JFrame implements ActionListener {
    Choice searchMeterChoice, searchNameCHoice;
    JTable table;
    JButton search, print, close;
    Customer_Details(){
        super("Customer Details");
        getContentPane().setBackground(new Color(192,186,254));
        setSize(700,500);
        setLocation(400,200);
        setLayout(null);

        JLabel searchmeter = new JLabel("Search By Meter Number");
        searchmeter.setBounds(20,20,150,20);
        add(searchmeter);

        searchMeterChoice = new Choice();
        searchMeterChoice.setBounds(180,20,150,20);
        add(searchMeterChoice);

        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
            while (resultSet.next()){
                searchMeterChoice.add(resultSet.getString("meter_no"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        JLabel searchName = new JLabel("Search By Name");
        searchName.setBounds(400,20,100,20);
        add(searchName);

        searchNameCHoice = new Choice();
        searchNameCHoice.setBounds(520,20,150,20);
        add(searchNameCHoice);

        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
            while (resultSet.next()){
                searchNameCHoice.add(resultSet.getString("name"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        table = new JTable();
        try {
            database c =new database();
            ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        }
        catch (Exception e){
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,100,700,500);
        scrollPane.setBackground(Color.white);
        add(scrollPane);

        search = new JButton("Search");
        search.setBackground(Color.white);
        search.setBounds(20,70,80,20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBackground(Color.white);
        print.setBounds(120,70,80,20);
        print.addActionListener(this);
        add(print);

        close = new JButton("Close");
        close.setBackground(Color.white);
        close.setBounds(600,70,80,20);
        close.addActionListener(this);
        add(close);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==search){
            String query_search = "select * from NewCustomer where meter_no= '"+searchMeterChoice.getSelectedItem()+"' and name = '"+searchNameCHoice.getSelectedItem()+"' ";
            try {
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(query_search);
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        else if (e.getSource()==print) {
            try {
                table.print();
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Customer_Details();
    }
}
