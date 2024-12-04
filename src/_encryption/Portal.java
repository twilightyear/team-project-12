package _encryption;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Portal extends JFrame {
    private HashMap<String, String> userDatabase = new HashMap<>();
    private String loggedInUser = null;

    public Portal() {
        showLoginScreen();
    }

    private void showLoginScreen() {
        setTitle("로그인");
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("아이디:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("로그인");
        JButton signupButton = new JButton("회원가입");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(signupButton);

        add(mainPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String inputPassword = new String(passwordField.getPassword());

            if (userDatabase.containsKey(username)) {
                String encryptedPassword = userDatabase.get(username);
                String decryptedPassword = decryptPassword(encryptedPassword);
                if (decryptedPassword.equals(inputPassword)) {
                    loggedInUser = username;
                    showPortalPageScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "올바르지 않은 비밀번호입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "올바르지 않은 아이디입니다..", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        signupButton.addActionListener(e -> showSignupScreen());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setVisible(true);
    }

    private void showSignupScreen() {
        setTitle("회원가입");
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("아이디:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField();
        JButton signupButton = new JButton("회원가입");
        JButton backButton = new JButton("뒤로");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(signupButton);
        mainPanel.add(backButton);

        add(mainPanel, BorderLayout.CENTER);

        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "모든 양식을 채워주시기 바랍니다.", "애러", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDatabase.containsKey(username)) {
                JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.", "애러", JOptionPane.ERROR_MESSAGE);
            } else {
                String encryptedPassword = encryptPassword(password);
                userDatabase.put(username, encryptedPassword);
                JOptionPane.showMessageDialog(null, "회원가입을 성공했습니다.");
                showLoginScreen();
            }
        });

        backButton.addActionListener(e -> showLoginScreen());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setVisible(true);
    }

    private void showPortalPageScreen() {
        setTitle("Portal Page");
        getContentPane().removeAll();
        setLayout(new BorderLayout());

 
        JPanel portalPanel = new JPanel();
        portalPanel.setBackground(Color.BLACK);
        portalPanel.setLayout(null);

        JLabel userLabel = new JLabel("현재 계정 : " + loggedInUser);
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(10, 10, 200, 20);

        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(10, 50, 100, 30);
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            showLoginScreen();
        });

        portalPanel.add(userLabel);
        portalPanel.add(logoutButton);

        add(portalPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(600, 400);
        setVisible(true);
    }

    private String encryptPassword(String password) {
        String encrypted = password;
        encrypted = scytale.encryptScytale(encrypted);
        encrypted = monoalphabetic.encryptMonoalphabetic(encrypted);
        encrypted = character_substitution.encryptCharacterSubstitution(encrypted);
        encrypted = caesar.encryptCaesar(encrypted);
        return encrypted;
    }

    private String decryptPassword(String encryptedPassword) {
        String decrypted = encryptedPassword;
        decrypted = scytale.decryptScytale(decrypted);
        decrypted = monoalphabetic.decryptMonoalphabetic(decrypted);
        decrypted = character_substitution.decryptCharacterSubstitution(decrypted);
        decrypted = caesar.decryptCaesar(decrypted);
        return decrypted;
    }

    public static void main(String[] args) {
        new Portal();
    }
}