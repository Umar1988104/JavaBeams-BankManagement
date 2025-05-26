JavaBeams is a realistic and advanced Bank Management System developed using Java Swing GUI. It simulates core banking functions with a modern, responsive interface and complete backend logic using file-based storage (no external database). The system demonstrates the practical use of Java GUI programming, object-oriented concepts, and file handling.

The entire interface is created using Java Swing components such as JFrame, JPanel, JButton, JLabel, JTextField, JPasswordField, and JTable. Each window (Home, Create Account, Deposit, Withdraw, Settings, etc.) is designed with a custom layout, hover effects, transitions, and background images for a real banking experience.

Key Features (Implemented Using Java Swing GUI):
Welcome Page: A stylish entry screen with JFrame, JLabel, and background image. Loads first before main app.

Main Menu Dashboard: Left-side navigation panel created using JPanel with modern JButton designs for navigation.

Create Account: Form using JTextField, JComboBox, and validation logic. Saves data using file writing (FileWriter, BufferedWriter).

Deposit/Withdraw: Simple input interface with balance update logic. Data is read and updated using BufferedReader and FileWriter.

Transaction History: Displayed using JTable, loaded dynamically from transaction log files.

Search Feature: Search bar and logic to find customers by name or ID using linear search in file data.

Settings Page: Allows changing password and displaying logged-in user info. Uses JPasswordField and secure validation logic.

Hover Effects and UI Transitions: Implemented with MouseListener and setBackground() to enhance interactivity.

Confirmation Dialogs: JOptionPane used for logout and critical actions to ensure user confirmation.

Technologies & Tools:
Language: Java

GUI Toolkit: Java Swing

File Handling: File, FileReader, FileWriter, BufferedReader, BufferedWriter

IDE Used: NetBeans / IntelliJ IDEA / Eclipse

Layout Management: Custom layout using setBounds() and null layout for pixel-perfect placement

Project Highlights:
Modern, user-friendly UI with images and color themes.

Blue and white theme for main pages; green and white welcome page.

All features built with pure Java Swing GUI – no drag-and-drop editors.

Designed for educational use to demonstrate realistic banking operations using Java code.
