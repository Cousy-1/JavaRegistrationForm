import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JFrame {
    JTextField nameField, mobileField;
    JTextArea addressArea, displayArea;
    JRadioButton maleBtn, femaleBtn;
    JComboBox<String> dayBox, monthBox, yearBox;
    JCheckBox termsCheck;
    private ButtonGroup genderGroup;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(600, 300);
        setLayout(new GridLayout(1, 2));

        // Left side - Form
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));

        // Fields
        nameField = new JTextField();
        mobileField = new JTextField();

        // Gender
        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleBtn);
        genderGroup.add(femaleBtn);

        // DOB combo boxes
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i - 1] = String.valueOf(i);
        dayBox = new JComboBox<>(days);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthBox = new JComboBox<>(months);

        String[] years = new String[100];
        for (int i = 0; i < 100; i++) years[i] = String.valueOf(1925 + i);
        yearBox = new JComboBox<>(years);

        // Address
        addressArea = new JTextArea(3, 20);
        JScrollPane addressScrollPane = new JScrollPane(addressArea);

        // Terms
        termsCheck = new JCheckBox("Accept Terms And Conditions");

        // Buttons
        JButton submitBtn = new JButton("Submit");
        JButton resetBtn = new JButton("Reset");

        // Form layout
        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Mobile"));
        formPanel.add(mobileField);

        formPanel.add(new JLabel("Gender"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleBtn);
        genderPanel.add(femaleBtn);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("DOB"));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.add(dayBox);
        dobPanel.add(monthBox);
        dobPanel.add(yearBox);
        formPanel.add(dobPanel);

        formPanel.add(new JLabel("Address"));
        formPanel.add(addressScrollPane);

        formPanel.add(termsCheck);
        formPanel.add(new JLabel()); // empty filler

        formPanel.add(submitBtn);
        formPanel.add(resetBtn);

        // Right side - Display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(displayArea);

        add(formPanel);
        add(displayScrollPane);

        // Event listeners
        submitBtn.addActionListener(e -> handleSubmit());
        resetBtn.addActionListener(e -> resetForm());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void handleSubmit() {
        if (!termsCheck.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions.");
            return;
        }

        String name = nameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String address = addressArea.getText().trim();
        String gender = maleBtn.isSelected() ? "Male" : femaleBtn.isSelected() ? "Female" : "";

        String dob = dayBox.getSelectedItem() + "-" +
                monthBox.getSelectedItem() + "-" +
                yearBox.getSelectedItem();

        // Validate inputs
        if (name.isEmpty() || mobile.isEmpty() || gender.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        if (!mobile.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Mobile number should contain only digits.");
            return;
        }

        DBHelper.insertUser(name, mobile, gender, dob, address);
        displayArea.setText(DBHelper.fetchUsers());
    }

    private void resetForm() {
        nameField.setText("");
        mobileField.setText("");
        genderGroup.clearSelection();
        dayBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        addressArea.setText("");
        termsCheck.setSelected(false);
        displayArea.setText("");
    }

    public static void main(String[] args) {
        DBHelper.initDB();
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}
