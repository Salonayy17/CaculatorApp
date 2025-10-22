import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class StudentRegistrationForm extends JFrame {
    // Form components
    private JTextField tfFirstName, tfLastName, tfEmail, tfPhone, tfCity, tfState, tfPincode;
    private JTextArea taAddress;
    private JComboBox<String> cbGender, cbCourse, cbDept, cbYear;
    private JFormattedTextField tfDOB;
    private JButton btnSubmit, btnClear;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/saloni?serverTimezone=UTC";
    private static final String DB_USER = "root"; // your MySQL username
    private static final String DB_PASS = "aadi"; // your MySQL password

    public StudentRegistrationForm() {
        setTitle("Student Registration Form");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        // Row 1 - First Name
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("First Name:"), gbc);
        tfFirstName = new JTextField(20);
        gbc.gridx = 1; panel.add(tfFirstName, gbc);

        // Row 2 - Last Name
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Last Name:"), gbc);
        tfLastName = new JTextField(20);
        gbc.gridx = 1; panel.add(tfLastName, gbc);

        // Row 3 - Gender
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Gender:"), gbc);
        cbGender = new JComboBox<>(new String[]{"Select", "Male", "Female", "Other"});
        gbc.gridx = 1; panel.add(cbGender, gbc);

        // Row 4 - Date of Birth
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Date of Birth (yyyy-mm-dd):"), gbc);
        tfDOB = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        tfDOB.setColumns(20);
        gbc.gridx = 1; panel.add(tfDOB, gbc);

        // Row 5 - Email
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Email:"), gbc);
        tfEmail = new JTextField(20);
        gbc.gridx = 1; panel.add(tfEmail, gbc);

        // Row 6 - Phone
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Phone:"), gbc);
        tfPhone = new JTextField(20);
        gbc.gridx = 1; panel.add(tfPhone, gbc);

        // Row 7 - Address
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Address:"), gbc);
        taAddress = new JTextArea(3, 20);
        panel.add(new JScrollPane(taAddress), gbc);
        gbc.gridx = 1; panel.add(new JScrollPane(taAddress), gbc);

        // Row 8 - City
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("City:"), gbc);
        tfCity = new JTextField(20);
        gbc.gridx = 1; panel.add(tfCity, gbc);

        // Row 9 - State
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("State:"), gbc);
        tfState = new JTextField(20);
        gbc.gridx = 1; panel.add(tfState, gbc);

        // Row 10 - Pincode
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Pincode:"), gbc);
        tfPincode = new JTextField(20);
        gbc.gridx = 1; panel.add(tfPincode, gbc);

        // Row 11 - Course
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Course:"), gbc);
        cbCourse = new JComboBox<>(new String[]{"Select", "B.Tech", "B.Sc", "B.Com", "BCA", "MBA", "MCA"});
        gbc.gridx = 1; panel.add(cbCourse, gbc);

        // Row 12 - Department
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Department:"), gbc);
        cbDept = new JComboBox<>(new String[]{"Select", "Computer Science", "Electronics", "Mechanical", "Civil", "Management", "Commerce"});
        gbc.gridx = 1; panel.add(cbDept, gbc);

        // Row 13 - Year of Study
        y++;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Year of Study:"), gbc);
        cbYear = new JComboBox<>(new String[]{"Select", "1st Year", "2nd Year", "3rd Year", "4th Year"});
        gbc.gridx = 1; panel.add(cbYear, gbc);

        // Buttons
        y++;
        gbc.gridx = 0; gbc.gridy = y;
        btnSubmit = new JButton("Submit");
        btnClear = new JButton("Clear");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSubmit);
        btnPanel.add(btnClear);
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        add(panel);

        // Action listeners
        btnSubmit.addActionListener(e -> saveToDatabase());
        btnClear.addActionListener(e -> clearForm());
    }

    private void saveToDatabase() {
        String first = tfFirstName.getText().trim();
        String last = tfLastName.getText().trim();
        String gender = (String) cbGender.getSelectedItem();
        String dob = tfDOB.getText().trim();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim();
        String addr = taAddress.getText().trim();
        String city = tfCity.getText().trim();
        String state = tfState.getText().trim();
        String pin = tfPincode.getText().trim();
        String course = (String) cbCourse.getSelectedItem();
        String dept = (String) cbDept.getSelectedItem();
        String year = (String) cbYear.getSelectedItem();

        if (first.isEmpty() || last.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO students (first_name, last_name, gender, dob, email, phone, address, city, state, pincode, course, department, year_of_study) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, first);
            ps.setString(2, last);
            ps.setString(3, gender);
            ps.setString(4, dob);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, addr);
            ps.setString(8, city);
            ps.setString(9, state);
            ps.setString(10, pin);
            ps.setString(11, course);
            ps.setString(12, dept);
            ps.setString(13, year);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Registered Successfully!");
            clearForm();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
        }
    }

    private void clearForm() {
        tfFirstName.setText("");
        tfLastName.setText("");
        cbGender.setSelectedIndex(0);
        tfDOB.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
        taAddress.setText("");
        tfCity.setText("");
        tfState.setText("");
        tfPincode.setText("");
        cbCourse.setSelectedIndex(0);
        cbDept.setSelectedIndex(0);
        cbYear.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentRegistrationForm().setVisible(true);
        });
    }
}