package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class genrate_bill extends JFrame implements ActionListener {
    String meter;
    Choice searchmonthCho;
    JTextArea area;
    JButton bill;
    genrate_bill(String meter){
        this.meter = meter;
        setSize(500,700);
        setLocation(500,30);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel heading = new JLabel("Generate Bill");


        JLabel meter_no = new JLabel(meter);

        searchmonthCho = new Choice();
        searchmonthCho.add("January");
        searchmonthCho.add("February");
        searchmonthCho.add("March");
        searchmonthCho.add("April");
        searchmonthCho.add("May");
        searchmonthCho.add("June");
        searchmonthCho.add("July");
        searchmonthCho.add("August");
        searchmonthCho.add("September");
        searchmonthCho.add("October");
        searchmonthCho.add("November");
        searchmonthCho.add("December");

        area = new JTextArea(50,15);
        area.setText("\n\n\t ----------- Click on the --------------\n\t------------ Generate Bill ------------");
        area.setFont(new Font("Senserif",Font.ITALIC,15));
        JScrollPane pane = new JScrollPane(area);

        bill = new JButton("Generate Bill");
        bill.addActionListener(this);

        add(pane);

        panel.add(heading);
        panel.add(meter_no);
        panel.add(searchmonthCho);
        add(panel,"North");
        add(bill,"South");


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            database c = new database();
            String smonth = searchmonthCho.getSelectedItem();
            area.setText("\n Power Limited \n Electricity Bill for Month of "+smonth+",2023\n\n\n ");
            ResultSet resultSet = c.statement.executeQuery("select * from NewCustomer where meter_no='"+meter+"' ");

            if (resultSet.next()){
                area.append("\n    Customer Name                  : "+resultSet.getString("name"));
                area.append("\n    Customer Meter Number   : "+resultSet.getString("meter_no"));
                area.append("\n    Customer Address             : "+resultSet.getString("address"));
                area.append("\n    Customer City                    : "+resultSet.getString("city"));
                area.append("\n    Customer State                 : "+resultSet.getString("state"));
                area.append("\n    Customer Email             : "+resultSet.getString("email"));
                area.append("\n    Customer Phone Number   : "+resultSet.getString("phone_no"));

            }

            resultSet = c.statement.executeQuery("select * from Meter_Info where Meter_Number ='"+meter+"'");
            if (resultSet.next()){
                area.append("\n    Customer Meter Location  : "+resultSet.getString("Meter_Location"));
                area.append("\n    Customer Meter Type        : "+resultSet.getString("Meter_Type"));
                area.append("\n    Customer Phase Code      : "+resultSet.getString("Phsae_Code"));
                area.append("\n    Customer Bill Type            : "+resultSet.getString("Bill_Type"));
                area.append("\n    Customer Days                  : "+resultSet.getString("Days"));


            }
            resultSet = c.statement.executeQuery("select * from Tax");
            if (resultSet.next()){
                area.append("\n   Cost Per Unit                      : "+resultSet.getString("Cost_per_unit"));
                area.append("\n   Meter Rent                         : "+resultSet.getString("Meter_rent"));
                area.append("\n   Service Charge                 : "+resultSet.getString("service_Charge"));
                area.append("\n   Service Tax                      : "+resultSet.getString("Servece_tax"));
                area.append("\n   Swacch Bharat                 : "+resultSet.getString("Swacch_Bharat"));
                area.append("\n   Fixed Tax                        : "+resultSet.getString("Fixed_tax"));

            }
            resultSet = c.statement.executeQuery("select * from Bill where meter_no = '"+meter+"' and month = '"+searchmonthCho.getSelectedItem()+"' ");
            if (resultSet.next()) {
                area.append("\n   Current Month            : " + resultSet.getString("month"));
                area.append("\n   Units Consumed           : " + resultSet.getString("unit"));
                area.append("\n   Total Charges            : " + resultSet.getString("total_bill"));
                area.append("\n   Total Payable            : "+resultSet.getString("total_bill"));
            }

        }catch (Exception E){
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new genrate_bill("");
    }
}
