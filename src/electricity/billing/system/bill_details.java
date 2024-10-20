package electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class bill_details extends JFrame implements ActionListener {
    String meter;
    bill_details(String meter){
        super("Bill Details");
        this.meter = meter;
        setSize(700,600);
        setLocation(400,200);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JTable table = new JTable();
        try {
            database c = new database();
            String query_bill = "select * from Bill where meter_no = '"+meter+"' ";
            ResultSet resultSet = c.statement.executeQuery(query_bill);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0,0,700,600);
        add(sp);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new bill_details("");
    }
}
