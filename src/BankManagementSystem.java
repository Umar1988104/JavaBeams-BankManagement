import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import com.formdev.flatlaf.FlatLightLaf;


public class BankManagementSystem extends JFrame {
    // Paths for data storage
    private static final String USERS_FILE = "users.dat";
    private static final String ACCOUNTS_DIR = "accounts";

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Login panel components
    private JTextField loginUsername = new JTextField(20);
    private JPasswordField loginPassword = new JPasswordField(20);
    private JButton loginBtn = new JButton("Login");
    private JButton registerBtn = new JButton("Register");

    // Registration components
    private JTextField regUsername = new JTextField(20);
    private JPasswordField regPassword = new JPasswordField(20);
    private JButton regSubmitBtn = new JButton("Register");
    private JButton regBackBtn = new JButton("Back");

    // Dashboard components
    private JLabel welcomeLabel = new JLabel();
    private JButton createAccountBtn = new JButton("Create Account");
    private JButton depositBtn = new JButton("Deposit");
    private JButton withdrawBtn = new JButton("Withdraw");
    private JButton transactionHistoryBtn = new JButton("Transaction History");
    private JButton searchBtn = new JButton("Search Account");
    private JButton helpBtn = new JButton("Help");
    private JButton aboutBtn = new JButton("About");
    private JButton settingsBtn = new JButton("Settings");
    private JButton logoutBtn = new JButton("Logout");

    private String currentUser;

    public BankManagementSystem() {
        setTitle("Advanced Bank Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Make sure accounts directory exists
        File accountsDir = new File(ACCOUNTS_DIR);
        if (!accountsDir.exists()) {
            accountsDir.mkdir();
        }

        // Setup all panels
        setupWelcomePanel(); // Add this at the top before other panels
        setupLoginPanel();
        setupRegisterPanel();
        setupDashboardPanel();

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");


        setVisible(true);
    }

    private void setupWelcomePanel() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(200, 220, 255));

        JLabel titleLabel = new JLabel(" JAVA BEAMS BANK ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 51, 102));
        welcomePanel.add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(new Color(0, 120, 215));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 220, 255));
        buttonPanel.add(startButton);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        mainPanel.add(welcomePanel, "welcome");
    }

    private void setupLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(230, 240, 255));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Bank Management System - Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        loginPanel.add(title, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1;
        loginPanel.add(new JLabel("Username:"), c);
        c.gridx = 1;
        loginPanel.add(loginUsername, c);

        c.gridx = 0; c.gridy = 2;
        loginPanel.add(new JLabel("Password:"), c);
        c.gridx = 1;
        loginPanel.add(loginPassword, c);

        c.gridx = 0; c.gridy = 3;
        loginPanel.add(loginBtn, c);
        c.gridx = 1;
        loginPanel.add(registerBtn, c);

        loginBtn.addActionListener(e -> loginUser());
        registerBtn.addActionListener(e -> {
            regUsername.setText("");
            regPassword.setText("");
            cardLayout.show(mainPanel, "register");
        });

        mainPanel.add(loginPanel, "login");
    }

    private void setupRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(240, 230, 240));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Register New User");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        registerPanel.add(title, c);

        c.gridwidth = 1;
        c.gridx = 0; c.gridy = 1;
        registerPanel.add(new JLabel("Username:"), c);
        c.gridx = 1;
        registerPanel.add(regUsername, c);

        c.gridx = 0; c.gridy = 2;
        registerPanel.add(new JLabel("Password:"), c);
        c.gridx = 1;
        registerPanel.add(regPassword, c);

        c.gridx = 0; c.gridy = 3;
        registerPanel.add(regSubmitBtn, c);
        c.gridx = 1;
        registerPanel.add(regBackBtn, c);

        regSubmitBtn.addActionListener(e -> registerUser());
        regBackBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        mainPanel.add(registerPanel, "register");
    }

     private void setupDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(Color.WHITE);

        // ===== Top Bar =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 102, 204));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.add(logoutBtn, BorderLayout.EAST);

        logoutBtn.addActionListener(e -> logout());

        // ===== Center Panel (Logo + Bank Name) =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(60, 20, 60, 20));

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon("logo.jpg"); // make sure this path is correct
            Image logoImg = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImg));
        } catch (Exception e) {
            logoLabel.setText("[Logo]");
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("JAVA BEAMS BANK");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(titleLabel);

        // ===== Left Panel (Main Buttons) =====
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        leftPanel.setBackground(new Color(230, 240, 255));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JButton[] leftButtons = { createAccountBtn, depositBtn, withdrawBtn, transactionHistoryBtn };
        String[] leftLabels = { "Create Account", "Deposit", "Withdraw", "Transactions" };

        for (int i = 0; i < leftButtons.length; i++) {
            JButton btn = leftButtons[i];
            btn.setText(leftLabels[i]);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(0, 123, 255));
            btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
            leftPanel.add(btn);
        }

        // ===== Right Panel (Other Buttons) =====
        JPanel rightPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        rightPanel.setBackground(new Color(230, 240, 255));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JButton[] rightButtons = { searchBtn, helpBtn, aboutBtn, settingsBtn };
        String[] rightLabels = { "Search", "Help", "About", "Settings" };

        for (int i = 0; i < rightButtons.length; i++) {
            JButton btn = rightButtons[i];
            btn.setText(rightLabels[i]);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(0, 123, 255));
            btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
            rightPanel.add(btn);
        }

        // Add actions
        createAccountBtn.addActionListener(e -> showCreateAccountDialog());
        depositBtn.addActionListener(e -> showDepositDialog());
        withdrawBtn.addActionListener(e -> showWithdrawDialog());
        transactionHistoryBtn.addActionListener(e -> showTransactionHistory());
        searchBtn.addActionListener(e -> showSearchDialog());
        helpBtn.addActionListener(e -> showHelpDialog());
        aboutBtn.addActionListener(e -> showAboutDialog());
        settingsBtn.addActionListener(e -> showSettingsDialog());

        // Layout center row with left/center/right
        JPanel centerRow = new JPanel(new BorderLayout());
        centerRow.setBackground(Color.WHITE);
        centerRow.add(leftPanel, BorderLayout.WEST);
        centerRow.add(centerPanel, BorderLayout.CENTER);
        centerRow.add(rightPanel, BorderLayout.EAST);

        dashboard.add(topPanel, BorderLayout.NORTH);
        dashboard.add(centerRow, BorderLayout.CENTER);

        mainPanel.add(dashboard, "dashboard");
    }



    // ======= User Login/Registration Logic =======

    private void loginUser() {
        String username = loginUsername.getText().trim();
        String password = new String(loginPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, String> users = readUsers();
        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            welcomeLabel.setText("Welcome, " + currentUser);
            loginUsername.setText("");
            loginPassword.setText("");
            cardLayout.show(mainPanel, "dashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerUser() {
        String username = regUsername.getText().trim();
        String password = new String(regPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, String> users = readUsers();
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        users.put(username, password);
        writeUsers(users);
        JOptionPane.showMessageDialog(this, "Registration successful. You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(mainPanel, "login");
    }

    private Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (Map<String, String>) ois.readObject();
        } catch (Exception e) {
            // file might not exist yet
        }
        return users;
    }

    private void writeUsers(Map<String, String> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving users.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        currentUser = null;
        cardLayout.show(mainPanel, "login");
    }

    // ======= Account Management =======

    private void showCreateAccountDialog() {
        JDialog dialog = new JDialog(this, "Create New Account", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(200, 230, 201));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblName = new JLabel("Account Holder Name:");
        JTextField txtName = new JTextField(20);
        JLabel lblId = new JLabel("Account ID (unique number):");
        JTextField txtId = new JTextField(20);
        JLabel lblBalance = new JLabel("Initial Deposit Amount:");
        JTextField txtBalance = new JTextField(20);

        JButton btnCreate = new JButton("Create");
        JButton btnCancel = new JButton("Cancel");

        c.gridx = 0; c.gridy = 0;
        dialog.add(lblName, c);
        c.gridx = 1;
        dialog.add(txtName, c);

        c.gridx = 0; c.gridy = 1;
        dialog.add(lblId, c);
        c.gridx = 1;
        dialog.add(txtId, c);

        c.gridx = 0; c.gridy = 2;
        dialog.add(lblBalance, c);
        c.gridx = 1;
        dialog.add(txtBalance, c);

        c.gridx = 0; c.gridy = 3;
        dialog.add(btnCreate, c);
        c.gridx = 1;
        dialog.add(btnCancel, c);

        btnCreate.addActionListener(e -> {
            String name = txtName.getText().trim();
            String id = txtId.getText().trim();
            String balanceStr = txtBalance.getText().trim();

            if (name.isEmpty() || id.isEmpty() || balanceStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double balance;
            try {
                balance = Double.parseDouble(balanceStr);
                if (balance < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File accountFile = new File(ACCOUNTS_DIR, id + ".dat");
            if (accountFile.exists()) {
                JOptionPane.showMessageDialog(dialog, "Account ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account account = new Account(name, id, balance);
            saveAccount(account);

            JOptionPane.showMessageDialog(dialog, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void showDepositDialog() {
        JDialog dialog = new JDialog(this, "Deposit Amount", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(197, 202, 233));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("Account ID:");
        JTextField txtId = new JTextField(20);
        JLabel lblAmount = new JLabel("Deposit Amount:");
        JTextField txtAmount = new JTextField(20);

        JButton btnDeposit = new JButton("Deposit");
        JButton btnCancel = new JButton("Cancel");

        c.gridx = 0; c.gridy = 0;
        dialog.add(lblId, c);
        c.gridx = 1;
        dialog.add(txtId, c);

        c.gridx = 0; c.gridy = 1;
        dialog.add(lblAmount, c);
        c.gridx = 1;
        dialog.add(txtAmount, c);

        c.gridx = 0; c.gridy = 2;
        dialog.add(btnDeposit, c);
        c.gridx = 1;
        dialog.add(btnCancel, c);

        btnDeposit.addActionListener(e -> {
            String id = txtId.getText().trim();
            String amountStr = txtAmount.getText().trim();

            if (id.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account account = loadAccount(id);
            if (account == null) {
                JOptionPane.showMessageDialog(dialog, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            account.deposit(amount);
            saveAccount(account);

            JOptionPane.showMessageDialog(dialog, "Deposit successful. New Balance: " + account.balance, "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void showWithdrawDialog() {
        JDialog dialog = new JDialog(this, "Withdraw Amount", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(255, 224, 178));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("Account ID:");
        JTextField txtId = new JTextField(20);
        JLabel lblAmount = new JLabel("Withdraw Amount:");
        JTextField txtAmount = new JTextField(20);

        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnCancel = new JButton("Cancel");

        c.gridx = 0; c.gridy = 0;
        dialog.add(lblId, c);
        c.gridx = 1;
        dialog.add(txtId, c);

        c.gridx = 0; c.gridy = 1;
        dialog.add(lblAmount, c);
        c.gridx = 1;
        dialog.add(txtAmount, c);

        c.gridx = 0; c.gridy = 2;
        dialog.add(btnWithdraw, c);
        c.gridx = 1;
        dialog.add(btnCancel, c);

        btnWithdraw.addActionListener(e -> {
            String id = txtId.getText().trim();
            String amountStr = txtAmount.getText().trim();

            if (id.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account account = loadAccount(id);
            if (account == null) {
                JOptionPane.showMessageDialog(dialog, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (account.balance < amount) {
                JOptionPane.showMessageDialog(dialog, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            account.withdraw(amount);
            saveAccount(account);

            JOptionPane.showMessageDialog(dialog, "Withdrawal successful. New Balance: " + account.balance, "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void showTransactionHistory() {
        JDialog dialog = new JDialog(this, "Transaction History", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel lblId = new JLabel("Account ID:");
        JTextField txtId = new JTextField(15);
        JButton btnLoad = new JButton("Load History");
        topPanel.add(lblId);
        topPanel.add(txtId);
        topPanel.add(btnLoad);

        dialog.add(topPanel, BorderLayout.NORTH);

        btnLoad.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Enter Account ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File histFile = new File(ACCOUNTS_DIR, id + "_history.txt");
            if (!histFile.exists()) {
                JOptionPane.showMessageDialog(dialog, "No transaction history found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                textArea.setText("");
                return;
            }

            try {
                java.util.List<String> lines = java.nio.file.Files.readAllLines(histFile.toPath());
                textArea.setText(String.join("\n", lines));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "Error reading history.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void showSearchDialog() {
        JDialog dialog = new JDialog(this, "Search Account", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel lblName = new JLabel("Enter Account Holder Name:");
        JTextField txtName = new JTextField(20);
        JButton btnSearch = new JButton("Search");
        searchPanel.add(lblName);
        searchPanel.add(txtName);
        searchPanel.add(btnSearch);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        dialog.add(searchPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> {
            String searchName = txtName.getText().trim().toLowerCase();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Enter name to search.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File folder = new File(ACCOUNTS_DIR);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".dat"));
            StringBuilder results = new StringBuilder();

            boolean found = false;
            if (files != null) {
                for (File f : files) {
                    Account acc = loadAccount(f.getName().replace(".dat", ""));
                    if (acc != null && acc.name.toLowerCase().contains(searchName)) {
                        found = true;
                        results.append("Name: ").append(acc.name)
                                .append(", ID: ").append(acc.id)
                                .append(", Balance: ").append(acc.balance)
                                .append("\n");
                    }
                }
            }

            if (!found) {
                results.append("No accounts found for: ").append(searchName);
            }

            resultArea.setText(results.toString());
        });

        dialog.setVisible(true);
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "For help, contact javabeamssupoort@bank.com\n" +
                        "You can create accounts, deposit, withdraw, and check transaction history.",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Advanced Bank Management System\n" +
                        "Version 1.0\n" +
                        "Developed by JAVABEAMS\n" +
                        "Made by-\n" +
                        "MOHAMMAD UMAR SIKANDRI\n" +
                        "JAYANTI MISHRA\n" +
                        "AAHLADITA BARIK\n" +
                        "ZAHID AKHTAR KHAN\n" +
                        "2025",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSettingsDialog() {
        JOptionPane.showMessageDialog(this,
                "Settings are currently not available.\nFuture updates coming soon!\n Thank you for your patience",
                "Settings", JOptionPane.INFORMATION_MESSAGE);
    }

    // ======= File Operations =======

    private void saveAccount(Account account) {
        File file = new File(ACCOUNTS_DIR, account.id + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(account);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving account data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Account loadAccount(String id) {
        File file = new File(ACCOUNTS_DIR, id + ".dat");
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Account) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    // ======= Account Class =======
    private static class Account implements Serializable {
        String name;
        String id;
        double balance;

        public Account(String name, String id, double balance) {
            this.name = name;
            this.id = id;
            this.balance = balance;
            writeTransaction("Account created with balance: " + balance);
        }

        public void deposit(double amount) {
            balance += amount;
            writeTransaction("Deposited: " + amount + ", New Balance: " + balance);
        }

        public void withdraw(double amount) {
            balance -= amount;
            writeTransaction("Withdrawn: " + amount + ", New Balance: " + balance);
        }

        private void writeTransaction(String record) {
            String filename = ACCOUNTS_DIR + "/" + id + "_history.txt";
            try (FileWriter fw = new FileWriter(filename, true)) {
                fw.write(new Date() + ": " + record + "\n");
            } catch (IOException e) {
                // ignore errors here
            }
        }
    }

    // ======= Main Method =======
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        SwingUtilities.invokeLater(() -> new BankManagementSystem());
    }


}
