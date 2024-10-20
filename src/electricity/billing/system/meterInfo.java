package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class meterInfo extends JFrame implements ActionListener {
    Choice meterlocChoice, meterTypeChoice, phaseCodeChoice, billTypeChoice;
    JButton submit;
    String meternumber;
    meterInfo(String meternumber){
        this.meternumber = meternumber;
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(101, 183, 197));
        add(panel);

        JLabel heading = new JLabel("Meter Information");
        heading.setBounds(180,10,200,20);
        heading.setFont(new Font("Tahome",Font.BOLD,20));
        panel.add(heading);

        JLabel meterNumber = new JLabel("Meter Number");
        meterNumber.setBounds(50,80,100,20);
        panel.add(meterNumber);

        JLabel meternumberText = new JLabel(meternumber);
        meternumberText.setBounds(180,80,150,20);
        panel.add(meternumberText);

        JLabel meterloc = new JLabel("Location");
        meterloc.setBounds(50,120,100,20);
        panel.add(meterloc);

        meterlocChoice = new Choice();
        meterlocChoice.add("Outside");
        meterlocChoice.add("Inside");
        meterlocChoice.setBounds(180,120,150,20);
        panel.add(meterlocChoice);

        JLabel metertype = new JLabel("Meter Type");
        metertype.setBounds(50,160,100,20);
        panel.add(metertype);

        meterTypeChoice = new Choice();
        meterTypeChoice.add("Electric Meter");
        meterTypeChoice.add("Solar Meter");
        meterTypeChoice.add("Smart Meter");
        meterTypeChoice.setBounds(180,160,150,20);
        panel.add(meterTypeChoice);


        JLabel phaseCode = new JLabel("Phase Code");
        phaseCode.setBounds(50,200,100,20);
        panel.add(phaseCode);

        phaseCodeChoice = new Choice();
        phaseCodeChoice.add("011");
        phaseCodeChoice.add("022");
        phaseCodeChoice.add("033");
        phaseCodeChoice.add("044");
        phaseCodeChoice.add("055");
        phaseCodeChoice.add("066");
        phaseCodeChoice.add("077");
        phaseCodeChoice.add("088");
        phaseCodeChoice.add("099");
        phaseCodeChoice.setBounds(180,200,150,20);
        panel.add(phaseCodeChoice);


        JLabel billType = new JLabel("Bill Type");
        billType.setBounds(50,240,100,20);
        panel.add(billType);

        billTypeChoice = new Choice();
        billTypeChoice.add("Normal");
        billTypeChoice.add("Industrial");
        billTypeChoice.setBounds(180,240,150,20);
        panel.add(billTypeChoice);

        JLabel day = new JLabel("30 Days Billing Time...");
        day.setBounds(50,280,150,20);
        panel.add(day);

        JLabel note = new JLabel("Note :");
        note.setBounds(50,320,100,20);
        panel.add(note);

        JLabel note1 = new JLabel("By default bill is calculated for 30 days only");
        note1.setBounds(50,340,300,20);
        panel.add(note1);

        submit = new JButton("Submit");
        submit.setBounds(220,390,100,25);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        panel.add(submit);

        setLayout(new BorderLayout());
        add(panel,"Center");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/details.png"));
        Image i2 = i1.getImage().getScaledInstance(230,200,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imgLabel = new JLabel(i3);
        add(imgLabel,"East");


        setSize(700,500);
        setLocation(400,200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){
            String smeternum = meternumber;
            String smeterLoc = meterlocChoice.getSelectedItem();
            String smeterType = meterTypeChoice.getSelectedItem();
            String sphasecode = phaseCodeChoice.getSelectedItem();
            String sbilltype = billTypeChoice.getSelectedItem();
            String sday= "30";

            String query_meterInfo = "insert into Meter_Info values('"+smeternum+"', '"+smeterLoc+"', '"+smeterType+"', '"+sphasecode+"','"+sbilltype+"','"+sday+"')";
            try {
                database c = new database();
                c.statement.executeUpdate(query_meterInfo);

                JOptionPane.showMessageDialog(null,"Meter Information Submited Successfully");
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
        new meterInfo("");
    }
}
