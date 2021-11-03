/*
 * Author : @NahliZayd
 * created on: 03/11/2021
 */

package zn.passwordgenerator;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.util.concurrent.*;

public class PasswordGenerator extends JFrame {


    private JButton copy;
    private JButton generate;
    private JCheckBox letters;
    private JCheckBox numbers;
    private JTextField passwordField;
    private JSlider size;
    private JCheckBox symbols;

    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    String number = "012345689";
    String symbol = "#?!:;?%*£€$=+@{}[]&()";

    public PasswordGenerator() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        generate.addActionListener(e -> {
            if(checkOptions()) { //checking if at least one option is selected
            String password = generatePassword(letters.isSelected(),numbers.isSelected(),symbols.isSelected(),size.getValue());
            System.out.println(password); //we write the password in the console for debugging purposes
            while(!check(password)) { //as long as the password does not correspond to a certain strength another one is generated
               password = generatePassword(letters.isSelected(),numbers.isSelected(),symbols.isSelected(),size.getValue());
               System.out.println(password);//we write the password in the console for debugging purposes
           }
            passwordField.setText(password); // if the password matches, it is displayed in the passwordField in the user's view
        } else {
                JOptionPane.showMessageDialog(null,"Please select at least one option.","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }); /*fired when you click on the generate button*/

        copy.addActionListener(e -> {
            if (checkText(passwordField.getText())) {//we check that the password exists
                //we put it in the clipboard
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(passwordField.getText()), null);
                JOptionPane.showMessageDialog(null,"The password has been copied to the clipboard!","INFO",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"Please generate a password first.","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        });/*fired when you click on the copy button*/

        size.addChangeListener(l -> {
            //we update the text of the slider
            size.setBorder(BorderFactory.createTitledBorder(null, "Password Size : " + size.getValue(), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(240, 240, 240))); // NOI18N
        });/*fired when you change the slider value */
    }

    private boolean checkText(String text) {
        for(char c : text.toCharArray()) {
            if(Character.isWhitespace(c) || Character.isSpaceChar(c) || !Character.isDefined(c))  {
                //we just check if the characters are valid
                return false;
            }
        }
        return !text.equals(""); //if yes we check if the password is not empty
    }

    private boolean checkOptions() {
        return letters.isSelected() || numbers.isSelected() || symbols.isSelected(); //  we check if at least one option is selected
    }

    private boolean check(String password) {
        //we declare our booleans
        boolean hasDigit = false;
        boolean hasSymbol = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean shouldHaveDigit = numbers.isSelected();
        boolean shouldHaveSymbol = symbols.isSelected();
        boolean shouldHaveLetter = letters.isSelected();
        //we declare our booleans

        for(char c : password.toCharArray()) {
            //we check that the password corresponds to a sufficient level of security according to the selected options
            if (shouldHaveDigit && !hasDigit) hasDigit = Character.isDigit(c);
            if (shouldHaveSymbol && !hasSymbol) hasSymbol = !Character.isAlphabetic(c);
            if(shouldHaveLetter && !hasLower) hasLower = Character.isLowerCase(c);
            if(shouldHaveLetter && !hasUpper) hasUpper = Character.isUpperCase(c);
        }
        return (!shouldHaveDigit || hasDigit) && (!shouldHaveSymbol || hasSymbol) && (!shouldHaveLetter || (hasUpper && hasLower));
    }

    private String generatePassword(boolean lettersSelected, boolean numbersSelected, boolean symbolsSelected, int sizeValue) {
        //explicit but we generate password
        String chars="";
        if(lettersSelected) chars += alphabet + alphabet.toUpperCase();
        if(numbersSelected) chars += number;
        if(symbolsSelected) chars += symbol;
        StringBuilder finalPassword = new StringBuilder();
        for(int i = 0; i<sizeValue; i++)  {
            int index = ThreadLocalRandom.current().nextInt(0,chars.length());
            finalPassword.append(chars.charAt(index));
           }
        return finalPassword.toString();

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> new PasswordGeneratorUI().setVisible(true));
    }

    private void initComponents() {
    /*ui code
        using a gui builder
     */
        JPanel jPanel1 = new JPanel();
        generate = new JButton();
        copy = new JButton();
        JLabel jLabel1 = new JLabel();
        letters = new JCheckBox();
        numbers = new JCheckBox();
        symbols = new JCheckBox();
        passwordField = new JTextField();
        size = new JSlider();

        size.setMaximum(20);
        size.setMinimum(8);
        size.setValue(8);

        size.setBorder(BorderFactory.createTitledBorder(null, "Password Size : " + size.getValue(), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(240, 240, 240))); // NOI18N
        generate.setText("Generate");
        copy.setText("Copy");
        jLabel1.setText("Options : ");
        letters.setText("Letters");
        numbers.setText("Numbers");
        symbols.setText("Symbols");

        jPanel1.setBackground(new Color(51, 51, 51));
        generate.setBackground(new Color(95, 117, 184));
        copy.setBackground(new Color(95, 117, 184));
        letters.setBackground(new Color(51, 51, 51));
        numbers.setBackground(new Color(51, 51, 51));
        symbols.setBackground(new Color(51, 51, 51));
        size.setBackground(new Color(51, 51, 51));

        generate.setForeground(new Color(240, 240, 240));
        copy.setForeground(new Color(240, 240, 240));
        jLabel1.setForeground(new Color(240, 240, 240));
        letters.setForeground(new Color(240, 240, 240));
        numbers.setForeground(new Color(240, 240, 240));
        symbols.setForeground(new Color(240, 240, 240));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(jLabel1).addComponent(generate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(passwordField).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(letters).addGap(18, 18, 18).addComponent(numbers).addGap(18, 18, 18).addComponent(symbols)).addComponent(copy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(0, 0, Short.MAX_VALUE)).addComponent(size, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(letters).addComponent(numbers).addComponent(symbols)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(size, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(generate).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(copy).addGap(12, 12, 12)));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack();
    }
}
